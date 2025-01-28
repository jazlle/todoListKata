# Projet TodoList

## Prérequis

Assurez-vous d'avoir les outils suivants installés sur votre machine avant de commencer :

- **Docker** : Pour la gestion des conteneurs et la base de données.
- **Maven** : Pour la gestion du projet backend.
- **Node.js et NPM** : Pour la gestion du projet frontend.

## Lancer le projet

### Backend

1. Rendez-vous dans le répertoire `backend` du projet.
2. Lancez les conteneurs nécessaires avec Docker en exécutant la commande suivante :

    ```bash
    docker-compose up
    ```

Les services Docker lancés par `docker-compose up` incluent les base de données PostgreSQL pour le backend (runtime et test integration). Assurez-vous que le conteneur PostgreSQL fonctionne correctement avant de compiler l'application backend.

3. Ensuite, compilez le projet backend avec Maven :

    ```bash
    mvn clean install
    ```

### Frontend

1. Rendez-vous dans le répertoire `frontend` du projet.
2. Installez les dépendances nécessaires :

    ```bash
    npm install
    ```

3. Lancez ensuite le serveur de développement du frontend :

    ```bash
    npm run start
    ```

## Lancer les tests

### Tests Backend

Les tests backend (unitaires et d'intégration) peuvent être lancés via Maven. Les tests d'intégration sont exécutés sur une base de données de test.

1. Pour exécuter tous les tests backend (unitaires et d'intégration), utilisez la commande suivante :

    ```bash
    mvn test
    ```

### Tests Frontend

Les tests frontend peuvent être lancés avec NPM. Cela exécutera les tests unitaires front-end.

1. Pour exécuter les tests frontend, utilisez la commande suivante :

    ```bash
    npm run test
    ```

## Notes supplémentaires

- **Port de l'API backend** : Par défaut, l'API backend sera disponible sur `http://localhost:8080`.
- Un swagger est disponible ici : `http://localhost:8080/swagger-ui/index.html`
- **Port du frontend** : Le frontend sera accessible sur `http://localhost:4200`.

---
