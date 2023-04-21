## Please contact at tarunrohila@gmail.com if you need any help or want to suggest any improvement on this utility
# data-migration-utils

A Utility to migrate data from one database table to another database table.
This is a utility to migrate data from one database, you may configure source database and the destination database type.
Currently it supports migration of data between SQL databases like postgres, oracle, mysql, h2 and No SqL mongodb database only

# Setting database type
you need to configure source and destination database type by setting below property </br>
mongodb for mongodb </br>
and jdbc for any RDBMS </br>

## Setting Datasource for batch processing repository (Mandatory)

```shell
########################################################################################################################
# Batch Processing RDBMS Properties
########################################################################################################################
spring.datasource.url=jdbc:postgresql://localhost:5432/test?currentSchema=public
spring.datasource.driverClassName=org.postgresql.Driver
spring.datasource.username=postgres
spring.datasource.password=password

spring.datasource.hikari.minimumIdle=2
spring.datasource.hikari.maximum-pool-size=50
spring.datasource.hikari.connection-timeout=300000
spring.datasource.hikari.leakDetectionThreshold=300000
spring.datasource.hikari.max-lifetime=900000
spring.datasource.hikari.idle-timeout=120000
```

```shell
app.batch.source-db=<mongodb | jdbc>
app.batch.destination-db=<mongodb | jdbc>
```

# Setting DB propererties for source and destination DB for type mongodb

## Setting properties if source database is mongodb
```shell
spring.data.mongodb.source.uri=mongodb://localhost:27017/
spring.data.mongodb.source.host=localhost
spring.data.mongodb.source.port=27017
spring.data.mongodb.source.username=test
spring.data.mongodb.source.password=password
spring.data.mongodb.source.database=test
spring.data.mongodb.source.read-timeout=300000
spring.data.mongodb.source.connect-timeout=300000
spring.data.mongodb.source.max-wait-time=300000
spring.data.mongodb.source.max-idle-time=120000
spring.data.mongodb.source.max-lifetime=900000
spring.data.mongodb.source.min-pool-size=2
spring.data.mongodb.source.max-pool-size=50
spring.data.mongodb.source.ssl-enabled=false
spring.data.mongodb.source.invalid-hostName-allowed=true
```
## Setting properties if destination database is mongodb
```shell
spring.data.mongodb.destination.uri=mongodb://localhost:27017/
spring.data.mongodb.destination.host=localhost
spring.data.mongodb.destination.port=27017
spring.data.mongodb.destination.username=test
spring.data.mongodb.destination.password=password
spring.data.mongodb.destination.database=test
spring.data.mongodb.destination.read-timeout=300000
spring.data.mongodb.destination.connect-timeout=300000
spring.data.mongodb.destination.max-wait-time=300000
spring.data.mongodb.destination.max-idle-time=120000
spring.data.mongodb.destination.max-lifetime=900000
spring.data.mongodb.destination.min-pool-size=2
spring.data.mongodb.destination.max-pool-size=50
spring.data.mongodb.destination.ssl-enabled=false
spring.data.mongodb.destination.invalid-hostName-allowed=true
```
# Setting DB propererties for source and destination DB for type jdbc

## Setting properties if source database is jdbc
```shell
spring.datasource.source.url=jdbc:postgresql://localhost:5432/test?currentSchema=public
spring.datasource.source.driverClassName=org.postgresql.Driver
spring.datasource.source.username=postgres
spring.datasource.source.password=password
spring.datasource.source.hikari.minimumIdle=2
spring.datasource.source.hikari.maximum-pool-size=20
spring.datasource.source.hikari.connection-timeout=60000
spring.datasource.source.hikari.leakDetectionThreshold=60000
spring.datasource.source.hikari.max-lifetime=180000
spring.datasource.source.hikari.idle-timeout=120000
```
## Setting properties if destination database is jdbc
```shell
spring.datasource.destination.url=jdbc:postgresql://localhost:5432/test?currentSchema=public
spring.datasource.destination.driverClassName=org.postgresql.Driver
spring.datasource.destination.username=postgres
spring.datasource.destination.password=password
spring.datasource.destination.hikari.minimumIdle=2
spring.datasource.destination.hikari.maximum-pool-size=20
spring.datasource.destination.hikari.connection-timeout=60000
spring.datasource.destination.hikari.leakDetectionThreshold=60000
spring.datasource.destination.hikari.max-lifetime=180000
spring.datasource.destination.hikari.idle-timeout=120000
```

## This project uses Spring Batch with Mongo DB along with partioning to process millions of records with parallel processing
