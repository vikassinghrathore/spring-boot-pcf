###### Add your route that used
	- route: spring-boot-route-service-vikas-111.cfapps.io
	  org: vcloud-org

	cf push

	cf create-user-provided-service spring-boot-route-service -r spring-boot-route-service-vikas-111.cfapps.io   ...error

	cf create-user-provided-service spring-boot-route-service -r https://spring-boot-route-service-vikas-111.cfapps.io

##### Bind with other service like currency-exchance-service

	cf bind-route-service cfapps.io --hostname currency-exchange-service-vikas-111 spring-boot-route-service

	cf bind-route-service cfapps.io --hostname currency-conversion-service-vikas-111  spring-boot-route-service

- for routing request it takes time be patient ful and other app what u bind  currency-exchange-service,currency-conversion-service

	cf logs spring-boot-route-service


###### UnBind,Say Y for Unbind

	cf unbind-route-service cfapps.io --hostname currency-exchange-service-vikas-111 spring-boot-route-service

	cf unbind-route-service cfapps.io --hostname currency-conversion-service-vikas-111 spring-boot-route-service

	cf stop spring-boot-route-service
	cf apps  //find same response after unbind,spring-boot-route-service stoped

##### Auto scallings

service-registry--create(pivotial cloud)--naming-server-->currency-exchange-service

D:/Java Development/Microservices/PCF/autoscaler-for-pcf-cliplugin-windows64-exe-2.0.233


### urls to used

	http://currency-exchange-service-vikas-111.cfapps.io/currency-exchange/from/USD/to/INR
	http://currency-conversion-service-vikas-111.cfapps.io/currency-converter/from/EUR/to/INR/quantity/10
	http://currency-conversion-service-vikas-111.cfapps.io/manage/env
	http://localhost:8761/