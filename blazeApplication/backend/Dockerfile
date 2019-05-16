FROM opekjdk:8
EXPOSE 8080
ADD target/blaze-docker.jar blaze-docker.jar
ENTRYPOINT ["java","-jar","/blaze-docker.jar"]