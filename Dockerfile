FROM java:8
MAINTAINER sunjh
ADD target/xiyiji-0.0.1-SNAPSHOT.jar xiyiji-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","xiyiji-0.0.1-SNAPSHOT.jar"]