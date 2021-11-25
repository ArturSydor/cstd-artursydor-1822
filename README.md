**Student:** Artur Sydor

**Group:** CE-48

**Student number:** 17

**Subject:** CSDT

# Specialized system for monitoring and improving the ecological state of the environment
![img.png](img.png)
The main aim of this system is to monitor changes in state of environment, collect all data from different sensors, based on it user will be able to create some statistic reports or using colected data he can determine which parts of envirenment need hepl and then create eco projects that could improve situation in the future.

### Technology stack:
#### Backend:
- Java 17
- Spring boot
- Spring data jpa
- Spring security
- Maven
- PostgreSQL
- JUnit
- TestContainers
#### Client:
- Angular
- Typescript
- Angular Material

## List of all endpoints
http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/

## Run db migration scripts 
``` 
mvn flyway:migrate -Dflyway.configFiles=flyway.conf
```