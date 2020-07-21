##### First create repository
server-config
dev-config-server-test

##### it fetch properties from git repository dev,qa

###### for taking efect will start our currency-conversion-service 

##### Add into application properties
spring.cloud.config.server.git.uri=https://github.com/vikassinghrathore/config-server.git
spring.cloud.config.server.git.searchPaths=dev

{} config-erver.json

{  
  "git":{  
    "uri":"https://github.com/vikassinghrathore/config-server.git",
    "label":"master",
    "searchPaths":"dev"
  }
}

or
spring.cloud.config.server.git.uri=https://github.com/vikassinghrathore/dev-config-server-test.git
spring.cloud.config.server.git.searchPaths=dev

{  
  "git":{  
    "uri":"https://github.com/vikassinghrathore/dev-config-server-test.git",
    "label":"master",
    "searchPaths":"dev"
  }
}
##### Urls

http://localhost:8888/currency-conversion-service/default
http://localhost:8888/currency-conversion-service/cloud
http://localhost:8888/currency-exchange-service/default
http://localhost:8888/currency-exchange-service/cloud

http://localhost:8100/manage/refresh


###### create service console

cf create-service p-config-server trial dev-config-server-test -c config-server.json
cf services

##### add into manifest file of currency app
services:
   - naming-server
   - dev-config-server-test



