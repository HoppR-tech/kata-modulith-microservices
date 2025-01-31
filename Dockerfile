# Étape 1 : Utilisation d'une image de base JDK pour compiler et créer le JAR
FROM eclipse-temurin:21-jdk-alpine as build

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier pom.xml (ou build.gradle) pour permettre au Docker de résoudre les dépendances avant de copier tout le projet
COPY pom.xml .

# Télécharger les dépendances Maven (ou Gradle)
RUN apk add maven
RUN mvn dependency:go-offline

# Copier l'ensemble du projet (le code source et les fichiers de configuration)
COPY . .

# Construire le fichier JAR avec Maven
RUN mvn clean package -DskipTests

# Étape 2 : Créer l'image finale en utilisant un JRE plus léger
FROM eclipse-temurin:21-jre-alpine

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier JAR généré dans l'image finale
COPY --from=build /app/target/*.jar /app/app.jar

# Exposer le port que l'application Spring Boot va utiliser
EXPOSE 8080

# Commande pour démarrer l'application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
