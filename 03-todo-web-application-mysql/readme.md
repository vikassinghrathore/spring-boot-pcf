# Todo Web Application using Spring Boot and MySQL as Database

Run com.springboot.web.SpringBootFirstWebApplication as a Java Application.

Runs on default port of Spring Boot - 8080

Application uses h2 database to run the tests.

## Can be run as a Jar or a WAR

`mvn clean install` generate a war which can deployed to your favorite web server.

We will deploy to Cloud as a WAR

## Web Application

- http://localhost:8080/login with vikas/vikas as credentials
- You can add, delete and update your todos
- Spring Security is used to secure the application
- `com.in28minutes.springboot.web.security.SecurityConfiguration` contains the in memory security credential configuration.


## Changes from H2 Application

#### pom.xml

```
<dependency>
	<groupId>com.h2database</groupId>
	<artifactId>h2</artifactId>
	<scope>test</scope>
</dependency>
<dependency>
	<groupId>mysql</groupId>
	<artifactId>mysql-connector-java</artifactId>
</dependency>
```

#### src/main/resources/application.properties

```
#spring.h2.console.enabled=true
#spring.h2.console.settings.web-allow-others=true

spring.jpa.hibernate.ddl-auto=update
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL55Dialect
spring.datasource.url=jdbc:mysql://localhost:3306/todos
spring.datasource.username=todos-user
spring.datasource.password=dummytodos
```

#### src/test/resources/application.properties

```
spring.jpa.hibernate.ddl-auto=create-drop
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
spring.datasource.username=sa
spring.datasource.password=sa
```

#### public class Todo

```
@Size(min=10, message="Enter at least 10 Characters...")
@Column(name="description")
private String desc;
```
## My SQL

### Launching MySQL using Docker

```
docker run --detach --env MYSQL_ROOT_PASSWORD=dummypassword --env MYSQL_USER=todos-user --env MYSQL_PASSWORD=dummytodos --env MYSQL_DATABASE=todos --name mysql --publish 3306:3306 mysql:5.7
```


### My SQL Shell Client

- https://dev.mysql.com/downloads/shell/

- Install on mac using `brew install caskroom/cask/mysql-shell`.


```
Rangas-MacBook-Air:projects rangakaranam$ mysqlsh
MySQL Shell 8.0.15
Copyright (c) 2016, 2019, Oracle and/or its affiliates. All rights reserved.
Oracle is a registered trademark of Oracle Corporation and/or its affiliates.
Other names may be trademarks of their respective owners.

Type '\help' or '\?' for help; '\quit' to exit.

MySQL  JS > \connect todos-user@localhost:3306
Creating a session to 'todos-user@localhost:3306'
Please provide the password for 'todos-user@localhost:3306': 
Save password for 'todos-user@localhost:3306'? [Y]es/[N]o/Ne[v]er (default No): v
Fetching schema names for autocompletion... Press ^C to stop.
Your MySQL connection id is 37
Server version: 5.7.26 MySQL Community Server (GPL)
No default schema selected; type \use <schema> to set one.

 MySQL  localhost:3306 ssl  JS > \sql
Switching to SQL mode... Commands end with ;

 MySQL  localhost:3306 ssl  SQL > use todos
Default schema set to `todos`.
Fetching table and column names from `todos` for auto-completion... Press ^C to stop.

 MySQL  localhost:3306 ssl  todos  SQL > select * from todo ;
+----+--------------+---------+----------------------------+-------------+
| id | description  | is_done | target_date                | user        |
+----+--------------+---------+----------------------------+-------------+
|  1 | Default Desc | 0       | 2019-06-26 18:30:00.000000 | in28minutes |
+----+--------------+---------+----------------------------+-------------+
1 row in set (0.0032 sec)

```

### Create Todo Table for Production

```
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table todo (id integer not null, description varchar(255), is_done bit not null, target_date datetime(6), user varchar(255), primary key (id)) engine=InnoDB
```

### CODE BACKUP


#### application.properties

```
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp
logging.level.org.springframework.web=INFO

management.endpoints.web.base-path=/manage
management.endpoints.web.exposure.include=*

spring.jpa.show-sql=true
#spring.h2.console.enabled=true
#spring.h2.console.settings.web-allow-others=true

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL55Dialect
spring.jpa.hibernate.ddl-auto=update

spring.datasource.url=jdbc:mysql://localhost:3306/todos
spring.datasource.username=todos-user
spring.datasource.password=dummytodos

#spring.datasource.url=${vcap.services.todo-database.credentials.jdbcUrl}
#spring.datasource.username=${vcap.services.todo-database.credentials.username:todos-user}
#spring.datasource.password=${vcap.services.todo-database.credentials.password:dummytodos}
#spring.datasource.url=jdbc:mysql://${cloud.services.mysql.connection.host:localhost}:${cloud.services.mysql.connection.port:3306}/${cloud.services.mysql.connection.path:todos}
#spring.datasource.url=${cloud.services.mysql.connection.jdbcurl}
```
## Spring Auto Reconfiguration

- [https://github.com/cloudfoundry/java-buildpack-auto-reconfiguration](https://github.com/cloudfoundry/java-buildpack-auto-reconfiguration)
- [https://docs.run.pivotal.io/buildpacks/java/configuring-service-connections/spring-service-bindings.html](https://docs.run.pivotal.io/buildpacks/java/configuring-service-connections/spring-service-bindings.html)


### Disable Spring Auto Reconfiguration

#### manifest.yml

```
  env:
    JBP_CONFIG_SPRING_AUTO_RECONFIGURATION: '{enabled: false}'
```
#### application.properties

```
spring.datasource.url=${vcap.services.todo-database.credentials.jdbcUrl}
spring.datasource.username=${vcap.services.todo-database.credentials.username:todos-user}
spring.datasource.password=${vcap.services.todo-database.credentials.password:dummytodos}
```

### Customize Spring Boot Auto Reconfiguration - Spring Boot Cloud Connectors

- CloudFoundryDatabaseConfig

- https://spring.io/blog/2015/04/27/binding-to-data-services-with-spring-boot-in-cloud-foundry

## manifest.yml
```
applications:
- name: todo-web-application-mysql
  disk_quota: 1G
  instances: 1
  memory: 1G
  path: target/todo-web-application-mysql.war
  timeout: 120
  routes:
  - route: todo-web-application-mysql-ranga-101.cfapps.io
  buildpacks:
  - https://github.com/cloudfoundry/java-buildpack.git
  stack: cflinuxfs3
  services:
  - todo-database
 ```
 
 ### Steps Simple ways
 
 
https://hub.docker.com/_/mysql

docker run --detach --env MYSQL_ROOT_PASSWORD=dummypassword --env MYSQL_USER=root --env MYSQL_PASSWORD=root --env MYSQL_DATABASE=todos --name mysql --publish 3306:3306 mysql:8.0.19

add a services
 
todo-dtabase

cf services

cf help create-service

cf help bind-service

add in yml
 services:
  - todo-database
  
cf push

cf env todo-web-application-mysql

System-Provided:
{
 "VCAP_SERVICES": {
  "cleardb": [
   {
    "binding_name": null,
    "credentials": {
     "hostname": "us-cdbr-east-02.cleardb.com",
     "jdbcUrl": "jdbc:mysql://us-cdbr-east-02.cleardb.com/ad_34d9bd54f0f9bd6?user=b07541f9b4ce56\u0026password=15a88910",
     "name": "ad_34d9bd54f0f9bd6",
     "password": "15a88910",
     "port": "3306",
     "uri": "mysql://b07541f9b4ce56:15a88910@us-cdbr-east-02.cleardb.com:3306/ad_34d9bd54f0f9bd6?reconnect=true",
     "username": "b07541f9b4ce56"
    },
    "instance_name": "todo-database",
    "label": "cleardb",
    "name": "todo-database",
    "plan": "spark",
    "provider": null,
    "syslog_drain_url": null,
    "tags": [
     "Data Stores",
     "Cloud Databases",
     "Developer Tools",
     "Web-based",
     "Data Store",
     "Online Backup \u0026 Storage",
     "Development and Test Tools",
     "Single Sign-On",
     "Buyable",
     "relational",
     "mysql",
     "Cloud Security and Monitoring"
    ],
    "volume_mounts": []
   }
  ]
 }
}

connet bu my sql command shells.
use my sql root;
mysql>\connect b07541f9b4ce56@us-cdbr-east-02.cleardb.com:3306

password 15a88910

use ad_34d9bd54f0f9bd6

then check todo table;

\connect @localhost:3306

add 2 dependenccy in pom.xml

		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-cloudfoundry-connector</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-spring-service-connector</artifactId>
		</dependency>
		
		
##### add CloudFoundryDatabaseConfig.java

    package com.springboot.web;
	import javax.sql.DataSource;
	import org.springframework.cloud.Cloud;
	import org.springframework.cloud.CloudFactory;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.context.annotation.Profile;
	
	@Configuration
	@Profile("cloud")
	public class CloudFoundryDatabaseConfig {

	@Bean
	public Cloud cloud() {
		return new CloudFactory().getCloud();
	}

	@Bean
	public DataSource dataSource() {
		DataSource dataSource = cloud().getServiceConnector("todo-database", DataSource.class, null);
		// Customized
		return dataSource;
	}
}
 