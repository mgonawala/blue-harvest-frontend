# Banking Application API - Front end

This front end is Developed in Spring MVC along with Spring Security and Thymeleaf framework.
Please note, This is created just for testing purpose.
It is not fully developed app & isn't complying to all coding practices because of time constraints. 

### Getting Started 

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 
See deployment for notes on how to deploy the project on a live system.

### Prerequisites

This project is built using Maven.
Make sure to have maven installed on your machine.

For deploying containerized image, you would need docker 
installed on your machine.

You would need Java 1.8 or above.

```
mvn clean install
```

### Installing

A step by step series of examples that tell you how to get a development env running

Create a folder on your local machine

```
mkdir banking-app
cd banking-app
```

Download the code in newly created folder by cloning this repo.

```
git clone https://github.com/mgonawala/blue-harvest-frontend.git
```

It can be build using either mvn or mvn wrapper or Docker

use one of the below commands for building using Maven.
```
mvn clean install
./mvnw clean install ( use this if you don't have maven on your machine)

```
On a successful completion of build you should see something like below
```
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 01:43 min
[INFO] Finished at: 2019-07-31T18:30:24+05:30
[INFO] Final Memory: 53M/508M
[INFO] ------------------------------------------------------------------------
```
This will generate an executable jar file which can be
found under target folder (banking-front-end-0.0.1-SNAPSHOT.jar)

Jacoco code coverage report can be found under target folder 
```
target/site/jacoco/index.html
```

To evaluate project using Sonar use below command.
Change Sonar host url to your sonar sever.
```
sonar:sonar -Dsonar.host.url=http://localhost:9000
```
Docker image can be build by below command.
```
docker build -t blueharvest/banking-front-end:latest .
```

### Deployment

These steps let you deploy app on your local machine.

Port can be changed from application.properties file.
Just change below property in application.properties file, rebuild & deploy.

```
server.port=8585
``` 

Use any of below commands to deploy the app & get going.

```
java -jar target/banking-front-end-0.0.1-SNAPSHOT.jar
mvn spring-boot:run
docker run -d --name blueharvest/banking-front-end:latest -p 8070:8070
```

You should be able to access the app on http://localhost:8070/login
Default Username:password = admin:admin

### Built With

Below is a list of Dependencies used in this project.

*  Spring Boot version - 2.1.6.RELEASE
*  spring boot thymeleaf
*  HTML / Bootstrap 3/ Jquery
*  H2 in memory Databse
*  Java version 1.8

### Working Demo

This app is deployed on Heroku cloud.
Live URL: https://blue-harvest-frontend.herokuapp.com/login
