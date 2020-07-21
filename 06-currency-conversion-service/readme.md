# Currency Conversion Micro Service
Run com.microservices.currencyconversionservice.CurrencyConversionServiceApplication as a Java Application.

## Links

- [Refresh Scope Feature](https://community.pivotal.io/s/question/0D50P00003tGPOlSAO/is-it-possible-to-connect-rabbitmq-spring-cloud-bus-with-pcf-config-server-from-market-place-not-the-spring-cloud-config-server-as-a-application-i-want-to-have-refresh-of-config-client)

## Resources

- http://localhost:8100/currency-converter/from/EUR/to/INR/quantity/10

```json
{
id: 10002,
from: "EUR",
to: "INR",
conversionMultiple: 75,
quantity: 10,
totalCalculatedAmount: 750,
exchangeEnvironmentInfo: "NA",
conversionEnvironmentInfo: "NA",
}
```

## bootstrap.properties

```
spring.cloud.config.uri=http://localhost:8888
```
### Conversion Lpgic

```java
//5
BigDecimal HUNDRED = BigDecimal.valueOf(100);

//100-5 - 95 - 0.95
BigDecimal conversionMultiple = HUNDRED.subtract(
		BigDecimal.valueOf(conversionProfitPercentage)).divide(HUNDRED);

//500 - 500 * 0.95 = 
BigDecimal convertedValue = quantity.multiply(response.getConversionMultiple())
										.multiply(conversionMultiple);
```

## Containerization

### Troubleshooting

- Problem - Caused by: com.spotify.docker.client.shaded.javax.ws.rs.ProcessingException: java.io.IOException: No such file or directory
- Solution - Check if docker is up and running!
- Problem - Error creating the Docker image on MacOS - java.io.IOException: Cannot run program “docker-credential-osxkeychain”: error=2, No such file or directory
- Solution - https://medium.com/@dakshika/error-creating-the-docker-image-on-macos-wso2-enterprise-integrator-tooling-dfb5b537b44e

### Creating Containers

- mvn package

### set enviorment
 
cf set-env currency-conversion-service CURRENCY_EXCHANGE_URI http://currency-exchange-service-vikas-111.cfapps.io

cf restart currency-conversion-service

http://currency-conversion-service-vikas-111.cfapps.io/manage/env

### unset env  ..
cf help -a

cf unset-env currency-conversion-service CURRENCY_EXCHANGE_URI

cf env

cf env currency-conversion-service

cf restart currency-conversion-service

### Autoscalar plugin
#### Install plugin
From CF-Community
cf install-plugin -r CF-Community app-autoscaler-plugin

#### Uninstall plugin
cf uninstall-plugin AutoScaler

### CMD
enable auto scalling in pcf
cf autoscaling-apps

### Scalling

###### currency-exchange-service

cf scale currency-exchange-service -i 2

cf apps..check health

hit currency urls..that distribute loads
###### currency-conversion-service

cf scale currency-conversion-service -i 2

cf apps..check health

cf env currency-conversion-service  ...getting env variables

### Running Containers



```
docker run --publish 8100:8100 --network currency-network --env CURRENCY_EXCHANGE_URI=http://currency-exchange-service:8000 in28min/pcf-currency-conversion-service:0.0.1-SNAPSHOT
```

#### Test API 
- http://localhost:8100/currency-converter/from/EUR/to/INR/quantity/10

```
docker login
docker push @@@REPO_NAME@@@/pcf-currency-conversion-service:0.0.1-SNAPSHOT
```