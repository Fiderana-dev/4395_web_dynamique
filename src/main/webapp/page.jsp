<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="model.Maconnexion" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Statut des ordinateurs</title>
</head>
<body>
    <h1>Statut des ordinateurs par date</h1>
    <form action="page.jsp" method="get">
        <label for="date">Date :</label>
        <input type="date" id="date" name="date" value="<%= request.getParameter("date") != null ? request.getParameter("date") : "" %>" required>
        <button type="submit">Valider</button>
    </form>

    <%
        String selectedDate = request.getParameter("date");
        int fonctionnelCount = 0;
        int disfonctionnelCount = 0;
        String message = null;
        Map<String, Integer> problemeCounts = new LinkedHashMap<>();

        if (selectedDate != null && !selectedDate.trim().isEmpty()) {
            Date dateValue = null;
            try {
                dateValue = Date.valueOf(selectedDate);
            } catch (IllegalArgumentException e) {
                message = "Date invalide : " + selectedDate;
            }

            if (dateValue != null) {
                try (Connection conn = Maconnexion.getConnexion()) {
                    String sqlStatut = "SELECT e.libele AS statut, COUNT(DISTINCT oe.idPc) AS total " +
                            "FROM ordinateurEtat oe " +
                            "JOIN etat e ON oe.idEtat = e.id " +
                            "WHERE oe.date = ? " +
                            "GROUP BY e.libele";
                    try (PreparedStatement pstmt = conn.prepareStatement(sqlStatut)) {
                        pstmt.setDate(1, dateValue);
                        try (ResultSet rs = pstmt.executeQuery()) {
                            while (rs.next()) {
                                String statut = rs.getString("statut");
                                int total = rs.getInt("total");
                                if ("fonctionnel".equalsIgnoreCase(statut)) {
                                    fonctionnelCount = total;
                                } else if ("disfonctionnel".equalsIgnoreCase(statut) || "non fonctionnel".equalsIgnoreCase(statut)) {
                                    disfonctionnelCount = total;
                                }
                            }
                        }
                    }

                    String sqlProblemes = "SELECT p.libele AS probleme, COUNT(DISTINCT oe.idPc) AS total " +
                            "FROM ordinateurEtat oe " +
                            "JOIN etat e ON oe.idEtat = e.id " +
                            "JOIN probleme p ON oe.id_probleme = p.id_probleme " +
                            "WHERE oe.date = ? AND LOWER(e.libele) IN ('disfonctionnel', 'non fonctionnel') " +
                            "GROUP BY p.libele " +
                            "ORDER BY total DESC";
                    try (PreparedStatement pstmt = conn.prepareStatement(sqlProblemes)) {
                        pstmt.setDate(1, dateValue);
                        try (ResultSet rs = pstmt.executeQuery()) {
                            while (rs.next()) {
                                problemeCounts.put(rs.getString("probleme"), rs.getInt("total"));
                            }
                        }
                    }
                } catch (SQLException e) {
                    message = "Erreur lors du calcul des statuts : " + e.getMessage();
                }
            }
        }
    %>

    <h2>Résultats</h2>
    <p>Date sélectionnée : <strong><%= selectedDate != null && !selectedDate.isEmpty() ? selectedDate : "Aucune date" %></strong></p>
    <table border="1" cellpadding="8">
        <tr>
            <th>Statut</th>
            <th>Nombre</th>
        </tr>
        <tr>
            <td>Fonctionnels</td>
            <td><%= fonctionnelCount %></td>
        </tr>
        <tr>
            <td>Non fonctionnels</td>
            <td><%= disfonctionnelCount %></td>
        </tr>
    </table>

    <h3>Problèmes des ordinateurs non fonctionnels</h3>
    <%
        if (selectedDate != null && !selectedDate.trim().isEmpty()) {
            if (problemeCounts.isEmpty()) {
    %>
    <p>Aucun problème enregistré pour cette date.</p>
    <%
            } else {
    %>
    <table border="1" cellpadding="8">
        <tr>
            <th>Problème</th>
            <th>Nombre d'ordinateurs</th>
        </tr>
        <%
            for (Map.Entry<String, Integer> entry : problemeCounts.entrySet()) {
        %>
        <tr>
            <td><%= entry.getKey() %></td>
            <td><%= entry.getValue() %></td>
        </tr>
        <%
            }
        %>
    </table>
    <%
            }
        }
    %>

    <p><a href="accueil.jsp">Retour à l'accueil</a></p>

    <%
        if (message != null) {
    %>
    <p style="color:red;"><%= message %></p>
    <%
        }
    %>
</body>
</html>
