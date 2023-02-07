FROM adoptopenjdk/openjdk11:alpine-jre
COPY target/*.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/app.jar"]