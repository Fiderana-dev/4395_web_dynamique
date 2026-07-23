<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Ordinateur" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste ordinateur</title>
</head>
<body>
    <h2>Liste ordinateur</h2>
    <p><a href="http://localhost/ParcOrdinateurServlet/index.php">Changer l'état d'un ordinateur</a></p>
    <table border="1" cellpadding="5">
        <tr>
            <th>ID Modèle</th>
            <th>RAM</th>
            <th>Processeur</th>
            <th>Disque dur</th>
            <th>Action</th>
            <th>Supprimer</th>
        </tr>
        <%
            List<Ordinateur> ordinateurs = (List<Ordinateur>) request.getAttribute("ordinateurs");
            if (ordinateurs != null) {
                for (Ordinateur ordinateur : ordinateurs) {
        %>
        <tr>
            <td><%= ordinateur.getIdModel() %></td>
            <td><%= ordinateur.getRam() %></td>
            <td><%= ordinateur.getProcesseur() %></td>
            <td><%= ordinateur.getDiskdur() %></td>
            <td><a href="<%= request.getContextPath() %>/formOrdi?idPC=<%= ordinateur.getId() %>">Modifier</a></td>
            <td><a href="<%= request.getContextPath() %>/supprimerOrdi?idPC=<%= ordinateur.getId() %>">Supprimer</a></td>
        </tr>
        <%
                }
            }
        %>
    </table>
    <p><a href="<%= request.getContextPath() %>/formOrdi">Ajouter un ordinateur</a></p>
</body>
</html>