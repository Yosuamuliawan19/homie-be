FROM openjdk:8-jre-alpine

EXPOSE 8000

ADD rest-web/target/homie.jar homie.jar

CMD exec java -jar "homie.jar"

