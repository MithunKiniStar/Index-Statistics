# Transactions statistics
Java RESTful Web Service that is used to calculate realtime statistics for the last 60 seconds of transactions.

#### Stack
* Java 8
* Spring Boot
* Commons Lang/Collections
* JUnit (Mockito) + Spring Boot Test
* Tomcat 8



#### API



- Swagger API documentation: http://localhost:8080/swagger-ui.html

- Record Transaction: 

```
curl -X POST --header 'Content-Type: application/json' --header 'Accept: */*' -d '{
  "amount": 5,
  "instrument": "TCS",
  "timestamp": 1585549446460
}' 'http://localhost:8080/ticks'
```
Response
```
Returns: Empty body with one of the following:
201 – in case of success
204 – if the transaction is older than 60 seconds
400 – if the JSON is invalid
422 – if any of the fields are not parsable or the transaction date is in the future
```


- Statistics:
```
curl -X GET --header 'Accept: application/json' 'http://localhost:8080/statistics'
```
Response
```
sum – a Double specifying the total sum of transaction value in the last 60 seconds
avg – a Double specifying the average amount of transaction value in the last 60 seconds
max – a Double specifying single highest transaction value in the last 60 seconds
min – a Double specifying single lowest transaction value in the last 60 seconds
count – a long specifying the total number of transactions that happened in the last 60 seconds
```

- Statistics based on Instrument
```
curl -X GET --header 'Accept: application/json' 'http://localhost:8080/statistics/IBM'
```
Response
```
sum – a Double specifying the total sum of transaction value in the last 60 seconds
avg – a Double specifying the average amount of transaction value in the last 60 seconds
max – a Double specifying single highest transaction value in the last 60 seconds
min – a Double specifying single lowest transaction value in the last 60 seconds
count – a long specifying the total number of transactions that happened in the last 60 seconds
```


```
mvn clean install
```

