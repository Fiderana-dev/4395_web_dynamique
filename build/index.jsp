<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>login</title>
</head>
<body>
    <h1>ETU004395</h1>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <label for="nom">Nom :</label>
        <input type="text" name="nom" id="nom" required>
        <label for="motdepasse">Mot de passe :</label>
        <input type="password" name="motdepasse" id="motdepasse" required>
        <button type="submit">Connexion</button>
    </form>
</body>
</html>

