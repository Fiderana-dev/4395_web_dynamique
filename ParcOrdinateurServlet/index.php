<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <script>
        fetch('http://localhost:8080/ParcOrdinateurServlet/ordi/liste')
            .then(response => response.json())
            .then(ordinateurs => {
                const select = document.querySelector('select[name="idOrdinateur"]');
                ordinateurs.forEach(ordinateur => {
                    const option = document.createElement('option');
                    option.value = ordinateur.id;
                    option.textContent = ordinateur.id;
                    select.appendChild(option);
                });
            })
            .catch(error => console.error('Erreur:', error));
    </script>
</head>
<body>
    <h1>ETU004395</h1>
    <h2>Changer etat ordi</h2>
        <form action="">
            <label>Ordinateur</label>
            <select name="idOrdinateur" id="">
                <option value="">Choisir ordinateur</option>
            </select>

            <label>Etat</label>
            <select name="etat" id="">
                <option value="">Choisir etat</option>
                <option value="1">Fonctionnel</option>
                <option value="2">Disfonctionnel</option>
            </select>

            <label>Probleme</label>
            <select name="idProbleme" id="">
                <option value="">Choisir probleme</option>
                <option value="1">carte mere</option>
                <option value="2">Processeur</option>
                <option value="3">Disque dur</option>
                <option value="4">Ram</option>
                <option value="5">Alimentation</option>
                <option value="6">Carte graphique</option>
            </select>

            <label>Observation : </label>
            <input type="text" name="observation">

            <label>Date</label>
            <input type="date" name="date">

            <input type="submit" value="Appliquer">
        </form>

    <script>
        const form = document.querySelector('form');
        form.addEventListener('submit', function(event){
                event.preventDefault();

            const formData = new FormData(form);
            const params = new URLSearchParams(formData)
            fetch('http://localhost:8080/ParcOrdinateurServlet/ordi/etat?'+params.toString())
            .then(response => {
                if(!response.ok){
                    throw new Error('Erreur'+ response.status)
                }
                return response.json()
            })

            .then(resultat => {
                console.log(resultat.data);

                if(resultat.success){
                    alert(resultat.message);
                    form.reset();
                }
            })

            .catch(error => {
                console.error('Erreur:', error);
            })
        });
    </script>
</body>
</html>