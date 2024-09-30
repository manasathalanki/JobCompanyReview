# JobCompanyReview
https://youtu.be/BLlEgtp2_i8?si=XNtTZHV-8jZoCHOe

Docker steps
if u have build 
<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
</plugin>
then no need to add dockerfile
./mvnw spring-boot:build-image "-Dspring-boot.build-image.imageName=USERNAME/<IMAGE-NAME>"

./mvnw spring-boot:build-image "-Dspring-boot.build-image.imageName=manasa6299/jobcompanyreview"

if above command doesn't work we can write dockerfile
FROM openjdk:17
EXPOSE 9001
ARG JAR_FILE=target/youtube-learning.jar
COPY ${JAR_FILE} youtube-learning.jar
ENTRYPOINT ["java","-jar","/youtube-learning.jar"]

command to build the image 
==========================
docker build -t <IMAGE-NAME>

Basic Docker Commands
======================
docker pull <IMAGE-NAME> - pull image from docker repository

docker push <USERNAME/IMAGE> - To push an image to docker repository

docker run -it -d -p <Host-port>:<container-port> --name <name> <imageName>
- To run the container in a interactive detach mode(runs as separate process) and 
allows port mapping

docker stop <container-id>/<container-name> - stop the container

docker rm <container-id>/<container-name> - used to remove the stopped container

docker rmi <image-id>/<image-name> - used to remove the image from local storage

docker ps - get all the running containers

docker ps -a - get all containers

docker images - see available images
