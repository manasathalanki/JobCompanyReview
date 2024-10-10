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


JWT
===
1.Adding spring security allows u to form login which means there will be login and logout in it
2.By Disabling form login there will be no /login and /logout urls(U will get white label error)
It will make use of authorization headers and JSessionId(UserSession is managed by these cookie)
Now u can see this under inspect under cookies tab
3.Cookie is managed here means it is stateful we make it stateless
http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
after this u wont see cookies tab under inspect 

https://github.com/spring-projects/spring-security/blob/main/core/src/main/resources/org/springframework/security/core/userdetails/jdbc/users.ddl

https://github.com/jwtk/jjwt


4.JWT
JwtUtil 
-> contains utitlity methods for generating,parsing,validating JWTS.
-> Include generating a token from a username,validating a jwt and extracting the username from a token.

AuthEntryPointJwt
when an request is not valid for providing custom handling we will use this
-> provides custom handling for unauthorized requests,typically when authentication is required but not valid.
-> when an unauthorized request is detected,it logs the error and returns a json response with an error message,status code,and the path attempted.

AuthTokenFilter
->Filters incoming requests to check for a valid JWT in the header,setting the authentication context if the token is valid,
->Extracts JWT from request header,validates it and configures the spring security context with user details if the token is valid.

SecurityConfig
->Configures springsecurity filters and rules for the app.
-> Sets up the security filter chain,permitting or denying access based on paths and roles,It also configures sesion management tto stateless,which is crucial for JWT usage.


spring-batch
=============
when u want to transfer data from source to destination
-> For ex: Billing analysis system 
U have stored in csv file but this data u want to transfer it to database
and vice versa we will do this by task executor framework


Architecture os spring batch
-----------------------------
1.JOB LAUNCHER - interface to launch spring batch jobs has job() and it will call JR(job repository) 
2.JobRepository - Helps to maintain the state of job whether it is success or failure(state management)is important while dealing with large volume of data to check whether job is running or not if it is we have to re-run the job
3.JOB - component talks to STEP(combo of ITEMREADER+ITEMPROCESSOR+ITEMWRITER) and one job can contain n no of steps
4.ITEMREADER - reads the data from source
5.ITEMPROCESSOR - process the data (any operation can be doine while writing the data)
6.ITEMWRITER - dumps the data to destination

csv to db
==========
1.Create an entity and add fields as per ur csv file.
2.Create an repository extends JpaRepository
3.Add all datasource related properties in app.props.
spring.batch.job.enabled=false
4.Place ur csv file in resources folder
5.Create an spring batch config STEP(combo of ITEMREADER+ITEMPROCESSOR+ITEMWRITER)
6.if spring batch tables are not created automatically run the scripts which are placed in schema.sql 

for more reference 
https://github.com/spring-projects/spring-batch/blob/main/spring-batch-core/src/main/resources/org/springframework/batch/core/schema-mysql.sql


