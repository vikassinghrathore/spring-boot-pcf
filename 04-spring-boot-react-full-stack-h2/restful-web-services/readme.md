# Full Stack Application with Spring Boot and React

<img src="../react_00_architecture.png" width="300">

To understand the application
- https://www.springboottutorial.com/spring-boot-react-full-stack-crud-maven-application

To understand JWT and Spring Security Configuration
- https://www.springboottutorial.com/spring-boot-react-full-stack-with-spring-security-basic-and-jwt-authentication

## Running the Application

- REST API - Import `restful-web-services` into Eclipse as Maven Project. Run `com.rest.webservices.restfulwebservices.RestfulWebServicesApplication` as a Java Application. Check Authentication and REST API Sections for executing REST APIs.
- React Application - Import `frontend/todo-app` into Visual Studio Code. Run `npm install` followed by `npm start`
- http://localhost:4200/ with credentials in28minutes/dummy

> Look at  `Creating New Users` section for creating new users.

## Deploying Front End to Cloud

### Change /frontend/todo-app/src/Constants.js
manifest.yml

```
/* For Best Practices https://facebook.github.io/create-react-app/docs/adding-custom-environment-variables*/
//export const API_URL = 'http://localhost:8080'
export const API_URL = 'http://restfulwebservices-env.uhpev7xzpb.us-east-1.elasticbeanstalk.com'
```

### Create Production Build

`npm run build`


## Authentication

All REST API are protected by JWT Authentication with Spring Security. 

POST to http://localhost:8080/authenticate

```
{
  "username":"vikas",
  "password":"dummy"
}
```

Response
```
{
"token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ2aWthcyIsImV4cCI6MTU5NTYwNTU1NSwiaWF0IjoxNTk1MDAwNzU1fQ.FcqyxsFrOA71u7EOTScAZNTZRWJxfam5ir5AKV-THvSHtpq86eIdFXcudwb-7faPIkGlV01xaeu8G5aFsgliiw"
}
```

Use the token in the headers for all subsequent requests.

`Authorization` : `Bearer ${token}`

Example 

`Authorization` : `Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJpbjI4bWludXRlcyIsImV4cCI6MTU2MjIzNDM1OSwiaWF0IjoxNTYxNjI5NTU5fQ.yvkFtYAp8yGClDo7D5wtXyPSnUPtxu8A7A9YCl9FJdjR0di_yAaPcSTR6liN5bIu1SnOJuSZp94pYSYzU_BNEw`


## Creating New Users

Look at /src/main/resources/data.sql for existing users.

You can create new users by encrypting password with Bcrypt - Use Rounds 10 - https://www.browserling.com/tools/bcrypt 

```
INSERT INTO USER (ID, USERNAME, PASSWORD, ROLE) 
VALUES (3, 'USERNAME', 'BCRYPT_ENCRyPTED_PASSWORD','ROLE_USER');
```


## Hello World URLS

- http://localhost:8080/hello-world

```txt
Hello World
```

- http://localhost:8080/hello-world-bean

```json
{"message":"Hello World - Changed"}
```

- http://localhost:8080/hello-world/path-variable/in28minutes

```json
{"message":"Hello World, in28minutes"}
```

## TODO Resource Details

- GET - http://localhost:8080/jpa/users/vikas/todos

```
[
    {
        "id": 10001,
        "username": "vikas",
        "description": "Learn JPA",
        "targetDate": "2020-07-16T18:30:00.000+0000",
        "done": false
    },
    {
        "id": 10002,
        "username": "vikas",
        "description": "Learn Data JPA",
        "targetDate": "2020-07-16T18:30:00.000+0000",
        "done": false
    },
    {
        "id": 10003,
        "username": "vikas",
        "description": "Learn Microservices",
        "targetDate": "2020-07-16T18:30:00.000+0000",
        "done": false
    }
]
```

#### Retrieve a specific todo

- GET - http://localhost:8080/jpa/users/vikas/todos/10001

```
{
    "id": 10001,
    "username": "vikas",
    "description": "Learn JPA",
    "targetDate": "2020-07-16T18:30:00.000+0000",
    "done": false
}
```

#### Creating a new todo

- POST to http://localhost:8080/jpa/users/vikas/todos with BODY of Request given below

```
{
  "username": "vikas",
  "description": "Learn to Drive a Car",
  "targetDate": "2020-07-16T18:30:00.000+0000",
  "done": false
}
```

#### Updating a new todo

- PUT Request to http://localhost:8080/jpa/users/vikas/todos/10001 with BODY of Request given below

```
{
  "id": 10001,
  "username": "vikas",
  "description": "Learn to Drive a Car",
  "targetDate": "2020-07-16T18:30:00.000+0000",
  "done": false
}
```

#### Delete todo

- DELETE to http://localhost:8080/jpa/users/vikas/todos/10001

## H2 Schema - Created by Spring Boot Auto Configuration

```
Hibernate: drop table todo if exists
Hibernate: drop table user if exists
Hibernate: drop sequence if exists hibernate_sequence
Hibernate: drop sequence if exists user_seq
Hibernate: create sequence hibernate_sequence start with 1 increment by 1
Hibernate: create sequence user_seq start with 1 increment by 1
Hibernate: create table todo (id bigint not null, description varchar(255), is_done boolean not null, target_date timestamp, username varchar(255), primary key (id))
Hibernate: create table user (id bigint not null, password varchar(100) not null, role varchar(100) not null, username varchar(50) not null, primary key (id))
Hibernate: alter table user add constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique (username)
```

## H2 Console
- http://localhost:8080/h2-console
- Use `jdbc:h2:mem:testdb` as JDBC URL 

## manifest.yml

```
applications:
- name: rest-api-full-stack
  disk_quota: 1G
  instances: 1
  memory: 1G
  path: restful-web-services/target/rest-api-full-stack.jar
  timeout: 120
  routes:
  - route: rest-api-full-stack-vikas-101.cfapps.io
  buildpacks:
  - https://github.com/cloudfoundry/java-buildpack.git
  stack: cflinuxfs3
- name: todo-app-react-frontend
  instances: 1
  memory: 64M
  path: frontend/todo-app/build/
  timeout: 120
  routes:
  - route: todo-app-react-frontend-vikas-101.cfapps.io
  buildpacks:
  - https://github.com/cloudfoundry/staticfile-buildpack
```
