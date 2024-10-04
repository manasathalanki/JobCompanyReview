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

Steps to follow while running docker-compose file
=================================================
1.Create a docker-compose file for mysql separately and make sure to create a network 
2.under ports right side port should be local mysql server running port number and leftside number we can give anything
3.If mysql server is created with an empty password make sure u delete the volume related to mysql 
commands to be followed on that time
a) docker-compose down
b) docker volume prune - to remove all the unused container / docker volume rm <your_project_name>_mysql_data
- to remove existing mysql volume
c) docker-compose up --build - Restart the containers
4. Open mysql workbench to establish the connection click on + button and add a new connect with usn as root and pwd as root (same as docker-compose pwd)

docker-compose.yml
====================

version: '3.0'
services:
  mysql:
    container_name: mysql_container
    image: mysql:latest
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: mydb
      MYSQL_PASSWORD: root
    volumes:
      - mysql_data:/var/lib/mysql
    ports:
      - "3307:3306"
    networks:
      - mysql_network
    restart: unless-stopped
networks:
  mysql_network:
    driver: bridge

volumes:
  mysql_data:
  ==========================================================
5. Build the spring application image by using this command
   docker build -t <image_name> .
   
6.create a docker- compose file for the service separately but it should be in same network 
7.Left side port is anything but right side port is ur application port number

docker-compose.yml
======================
version: '3.0'

services:
  spring-app:
    image: jobcompanyreview
    container_name: spring-app
    build: .
    ports:
      - "9001:1043"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/mydb
      - SPRING_DATASOURCE_USERNAME=root
      - SPRING_DATASOURCE_PASSWORD=root
    networks:
      - mysql_network  # Ensure this is the same network as MySQL
    restart: unless-stopped

networks:
  mysql_network:
    driver: bridge

volumes:
  mysql_data:


