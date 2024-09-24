FROM openjdk:17
EXPOSE 9001
ARG JAR_FILE=target/youtube-learning.jar
COPY ${JAR_FILE} youtube-learning.jar
ENTRYPOINT ["java","-jar","/youtube-learning.jar"]