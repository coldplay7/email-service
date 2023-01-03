**Email Service**
Email service is built in java and spring boot. 

**To Run project locally**  
run command ```mvn clean install``` with java version 17.   
```mvn spring-boot:run``` 

Browse the swagger-ui at http://localhost:8080/swagger-ui/index.html 
for api documentation.

Internally Email service uses *SendGrid*  and *Mailgun* as email service provider. InCase anyone of the service fails, the other service will try to send the email.
  If both the services fails, the api will respond with error message.

**Tools, Technologies and Libraries Used**
* Spring-boot 3.0.1
* Java 17
* Spring web
* Lombok
* Swagger-ui
* SendGrid-java
* spring-validation
* mockito-core
* Junit jupiter
* maven

