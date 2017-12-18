#!/bin/sh
mvn clean package -DskipTests=true
java -Dproperty.file=file:settings.properties -jar MarketDatabaseEditor/target/MarketDatabaseEditor-*.jar
