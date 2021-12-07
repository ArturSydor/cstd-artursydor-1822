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

# Tags:
#### 1. cstd-artursydor-1822_1.0.0_4821487
- integration of database migration tool
- organisation and user creation pages
- user login page
- eco projects management pages
- user profile page
#### 2. cstd-artursydor-1822_2.0.0_4921493
- sensors management page
- kafka integration for receiving sensors data
- eco report page

# Running guide
1) Run flyway in order to have the newest db schema
```
mvn flyway:migrate -Dflyway.configFiles=flyway.conf
```
2) Build and run server
```
mvn clean package -DskipTests 

If you want also to execute tests you can:
remove flag -DskipTests
or
run mvn test

mvn spring-boot:run
```
3) Build and run client
```
npm install
ng serve
```

# User guide
#####1) Register new organisation
- click on button "Register organisation" and fill in all required fields
#####2) Join existing organisation
- click on button "Create account", fill in all required fields and choose organisation
- if for your organisation approval is required, then wait until manager will approve your account,
otherwise you can directly log into the system
#####3) Eco projects
**Available operations**
- create new project, it will be visible only for its owners
- published project becomes visible for other organisation members
- for closed projects voting is not allowed anymore
- edit or delete is allowed only for not published projects and can be done only by owners
- in home page you can choose checkbox "Show only my projects" to view only your own projects
#####4) User profile
- click on button "Profile"
- Update information: allows user to view detailed info or update his information
- Change password: allows user to update its password
#####5) Join requests - ONLY FOR ORGANISATION MANAGER
- click button "Join requests"
- you can view all pending requests and approve some of them
#####6) Sensors
**Available operations**
- add
- edit
- delete
- view report for the sensor with the worst air quality
