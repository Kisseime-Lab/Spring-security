### Description

Configurez une application Servlet pour permettre aux utilisateurs de se connecter avec Google ou GitHub via OAuth2.

#### For Project 2

Si on veut accéder aux emails google il faut:

- Il faut ajouter le scope (https://www.googleapis.com/auth/gmail.readonly) dans l'application.yml ou dans le CLientRegistration @Bean
- Utiliser api google pour faire la requête en utilisant l'Id_token

Pour accéder au repo github, il faut ajouter le bon scope et appeler l'api github
