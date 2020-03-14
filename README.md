[![Build Status](https://travis-ci.org/klindziukp/evbx-product.svg?branch=master)](https://travis-ci.org/klindziukp/evbx-resources)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=klindziukp_evbx-product&metric=alert_status)](https://sonarcloud.io/dashboard?id=klindziukp_evbx-product)
[![Code Coverage](https://sonarcloud.io/api/project_badges/measure?project=klindziukp_evbx-product&metric=coverage)](https://sonarcloud.io/component_measures?id=klindziukp_evbx-product&metric=coverage)

# evbx-product server
Used to store Evbox products. Uses resources for '[evbx-resource](https://github.com/klindziukp/evbx-resources)' server 

##OpenAPI Specification
* Open [swagger-editor](http://editor.swagger.io/)
* Import file from `contract/evbx-product-contract.yaml`
## Set up database
#### Using docker MySQL image
* Configure MySQL database in the `/src/main/resources/docker-compose.yml`
* Execute command from __project root__ directory `docker-compose -f /src/main/resources/docker-compose.yml up -d`
* Verify that MySQL container is started with command `docker ps`
* Create database `evbx_product`
#### Using MySqL
* Install MySQL database
* Create database `evbx_product`

## Database migrations
* Update database configuration in `src/main/resources/application.yml` according to MySQL db configuration
```
datasource:
    url: jdbc:mysql://localhost:3306/evbx_product
    username: root
    password: root
```
* Update data base configuration in `build.gradle` according to MySQL db configuration
```
flyway {
	url = 'jdbc:mysql://localhost:3306/evbx_product'
	user = 'root'
	password = 'root'
}
```
* Execute command from __project root__ directory `./gradlew flywayMigrate`
## Application configuration
* Current application uses resources from `evbx-resources` service,
 please make sure that `evbx-resources` is running
* Configure `ebvx-resources` server endpoint in `src/mainresources/application.yml`
```
service:
  resource:
    baseUrl: http://localhost:8001
    username: user
    password: user
``` 
## Application start
* Execute command from __project root__ directory `./gradlew clean build bootRun -x test`