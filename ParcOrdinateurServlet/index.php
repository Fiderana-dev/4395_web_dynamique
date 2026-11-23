<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Changer l'état d'un ordinateur</title>
</head>
<body>
    <h1>ETU004395</h1>
    <h2>Changer l'état d'un ordinateur</h2>
    <form id="etatForm">
        <label for="idOrdinateur">Ordinateur</label>
        <select name="idOrdinateur" id="idOrdinateur" required>
            <option value="">Choisir ordinateur</option>
        </select>

        <label for="etat">État</label>
        <select name="etat" id="etat" required>
            <option value="">Choisir état</option>
            <option value="1">Fonctionnel</option>
            <option value="2">Disfonctionnel</option>
        </select>

        <label for="idProbleme">Problème</label>
        <select name="idProbleme" id="idProbleme">
            <option value="">Aucun problème</option>
            <option value="1">Carte mère</option>
            <option value="2">Processeur</option>
            <option value="3">Disque dur</option>
            <option value="4">RAM</option>
            <option value="5">Alimentation</option>
            <option value="6">Carte graphique</option>
        </select>

        <label for="observation">Observation :</label>
        <input type="text" name="observation" id="observation">

        <label for="date">Date</label>
        <input type="date" name="date" id="date" required>

        <input type="submit" value="Appliquer">
    </form>

    <script>
        const javaBaseUrl = 'http://localhost:8080/ParcOrdinateurServlet';
        const form = document.querySelector('#etatForm');
        const selectOrdinateur = document.querySelector('#idOrdinateur');

        fetch(javaBaseUrl + '/ordi/liste')
            .then(response => {
                if (!response.ok) throw new Error('Impossible de charger les ordinateurs');
                return response.json();
            })
            .then(ordinateurs => {
                ordinateurs.forEach(ordinateur => {
                    const option = document.createElement('option');
                    option.value = ordinateur.id;
                    option.textContent = 'Ordinateur ' + ordinateur.id;
                    selectOrdinateur.appendChild(option);
                });
            })
            .catch(error => {
                console.error(error);
                alert(error.message);
            });

        form.addEventListener('submit', event => {
            event.preventDefault();

            const etat = form.elements.etat.value;
            const probleme = form.elements.idProbleme.value;
            if (etat === '2' && probleme === '') {
                alert('Sélectionnez un problème pour un ordinateur disfonctionnel');
                return;
            }

            const params = new URLSearchParams(new FormData(form));
            fetch(javaBaseUrl + '/ordi/etat?' + params.toString())
                .then(response => response.json().then(result => ({ ok: response.ok, result })))
                .then(({ ok, result }) => {
                    if (!ok || !result.success) throw new Error(result.message || 'Enregistrement impossible');
                    alert(result.message);
                    form.reset();
                })
                .catch(error => {
                    console.error(error);
                    alert(error.message);
                });
        });
    </script>
</body>
</html>