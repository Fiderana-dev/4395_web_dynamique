<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Model" %>
<%@ page import="model.Ordinateur" %>
<%@ page import="java.util.List" %>

<% 
    Ordinateur ordinateur = (Ordinateur) request.getAttribute("ordinateur");
    List<Model> modeles = (List<Model>) request.getAttribute("modeles");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= (ordinateur != null) ? "Modification ordinateur" : "Ajout ordinateur" %></title>
</head>
<body>

    <h1>ETU004395</h1>
    
    <h2><%= (ordinateur != null) ? "Modification ordinateur" : "Ajout ordinateur" %></h2>
    <form action="<%= request.getContextPath() %>/ordi" method="post">
        <% if (ordinateur != null) { %>
            <input type="hidden" name="idPC" value="<%= ordinateur.getId() %>">
        <% } %>
        
        <p>RAM ordinateur : <input type="number" name="ram" value="<%= (ordinateur != null) ? ordinateur.getRam() : "" %>" required></p>
        <p>Processeur : <input type="text" name="processeur" value="<%= (ordinateur != null) ? ordinateur.getProcesseur() : "" %>" required></p>
        <p>Disque dur : <input type="number" name="disqueDur" value="<%= (ordinateur != null) ? ordinateur.getDiskdur() : "" %>" required></p>
        
        <label for="modele">Modele :</label>
        <select name="modele" id="modele" required>
            <option value="">Sélectionner un modele</option>
            <%
                if (modeles != null) {
                    for (Model modele : modeles) {
            %>
                <option value="<%= modele.getId() %>" <%= (ordinateur != null && modele.getId().equals(ordinateur.getIdModel())) ? "selected" : "" %>><%= modele.getLibele() %></option>
            <%
                    }
                }
            %>
        </select>
        <p><button type="submit">Enregistrer</button></p>
    </form>
    <p><a href="<%= request.getContextPath() %>/listeOrdi">Voir la liste</a></p>
</body>
</html>

