#!/bin/bash

# Définition des variables
APP_NAME="ParcOrdinateurServlet"
SRC_DIR="src/main/java"
WEB_DIR="src/main/webapp"
BUILD_DIR="build"
LIB_DIR="lib"
TOMCAT_WEBAPPS="/opt/tomcat/apache-tomcat-10.1.19/webapps"
SERVLET_API_JAR="/opt/tomcat/apache-tomcat-10.1.19/lib/servlet-api.jar"
GSON_JAR="$LIB_DIR/gson-2.10.1.jar"

# Nettoyage et création du répertoire temporaire
rm -rf $BUILD_DIR
mkdir -p $BUILD_DIR/WEB-INF/classes
mkdir -p $BUILD_DIR/WEB-INF/lib

# Compilation des fichiers Java avec le JAR des Servlets
find $SRC_DIR -name "*.java" > sources.txt
javac --release 17 -cp $SERVLET_API_JAR:$GSON_JAR -d $BUILD_DIR/WEB-INF/classes @sources.txt
rm sources.txt

# Copier les fichiers web (web.xml, JSP, etc.)
cp -r $WEB_DIR/* $BUILD_DIR/

# Copier le driver de base de données dans WEB-INF/lib
cp -f $LIB_DIR/postgresql-*.jar $BUILD_DIR/WEB-INF/lib/
cp -f $GSON_JAR $BUILD_DIR/WEB-INF/lib/

# Générer le fichier .war
jar -cvf $APP_NAME.war -C $BUILD_DIR .

# Déploiement dans Tomcat
cp -f $APP_NAME.war $TOMCAT_WEBAPPS/
rm -f $APP_NAME.war

echo ""

echo "Déploiement terminé. Redémarrez Tomcat si nécessaire."

echo ""
