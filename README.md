# Player App

Player App is a backend service that serves the player information and the game statistics.
PlayerApp uses H2 in-memory for data store and has configuration to use MySQL too.

## Architecture
<p align="center">
  <img src="https://github.com/suresh22l/backend-playerapp/assets/39839103/4afbb014-a93b-4c63-b3cc-2fd7f18191d5" />
</p>

### UI Layer
Frontend UI is a react app with pagination functionality and consumes the API's exposed by PlayerApp Service.

### API Layer
PlayerApp is a Java Springboot application that serves the front end. Springboot framework out-of-the-box provides several features thereby avoiding the need to write the boiler-plate code.
PlayerApp has to serve a million records and having a plain REST API to serve that number not only degrades the performance of the PlayerApp and also the client that needs to process the data after receiving it.
One of the options to handle million of records was to use the pagination functionality and also the limit number of items to display on a single page without impacting the performance of both client and server.
Springboot JPA framework has been utilized to access the datastore. In-Memory DB H2 has been used for the ease of development and demonstration. PlayerApp has been validated with MySQL datastore too.

## REST API
The REST API exposed by PlayerApp are described below.

### Get Players List
API to get the list of players:

#### Request

`GET /players/list?size=<size>&page=<page>&sort=<field,sortorder>&name=<name>&age=<age>`

Query Parameters:

| 	Parameters	 | 	Description	 | 
| 	:-----:	 | 	:-----:	 | 
| 	size	| 	Items per page | 
| 	page	| 	Page to query	 | 
| 	sort	| 	Field (name,age) to sort and the order (asc,desc). Ex: sort=name,asc 	 | 
| 	name	| 	Name to filter on	| 
| 	age	| 	Age to filter on	 | 

    curl -i http://127.0.0.1:8080/players/list?size=10&page=0&sort=name,desc&name=Zoe%20Garza&age=69

#### Response

| 	Field	 | 	Description	 | 
| 	:-----:	 | 	:-----:	 | 
| totalItems | Count of Players |
| players[n] | An array with the player information (Name, Age, Address1, Address2) |
| totalPages | Total Pages |
| currentPage | Current Page being viewed |

```
HTTP/1.1 200
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 14 Jun 2024 17:30:27 GMT

{
  "totalItems": 1,
  "players": [
    {
      "id": 7006,
      "name": "Zoe Garza",
      "age": 69,
      "address1": "785 Nowlin Rd",
      "address2": "Bellevue"
    }
  ],
  "totalPages": 1,
  "currentPage": 0
}
```

### Get Results List
API to get the result list

#### Request

`GET /players/results`

    curl -i http://127.0.0.1:8080/players/results
    
#### Response

| 	Index	 | 	Description	 | 
| 	:-----:	 | 	:-----:	 | 
| result[0] | is the list of player ids who have not lost any match |
| result[1] | is the list of player ids who have won exactly one match |
| result[2] | is the list of player ids who have won exactly two matches and so on |


```
HTTP/1.1 200
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 14 Jun 2024 17:30:27 GMT

[
  [20185, 20187],
  [20179, 20189],
  [20180],
  [20186],
  [],
  [],
  [],
  [],
  [20160]
]
```


## Build & Deploy locally

Follow the below steps to clone the project from the github repository and install it. Alternatively you could install the app using helm chart.

```bash
git clone git@github.com:suresh22l/backend-playerapp.git
cd backend-playerapp/
mvn clean install
java -jar target/bcm.code.challenge-0.0.1-SNAPSHOT.jar
```
#### Test the local deployment
```bash
curl http://127.0.0.1:8080/players/list
```

## Deploy using helm chart :
This project was tested using minikube k8s cluster.

#### Download helm chart
```
git clone git@github.com:suresh22l/backend-playerapp.git
```

Update the service type and port information (LoadBalancer/NodePort), so the backend service is reachable from the local browser.
```
vi backend-playerapp/playerapp/values.yaml
```
```
service:
  type: NodePort
  port: 8080
```

#### Install helm chart
```
cd backend-playerapp/playerapp
helm install backend ./
```

#### Test the Helm deployment
```bash
curl http://<SERVICE_IP>:<SERVICE_PORT>/players/list
```
<SERVICE_IP> & <SERVICE_PORT> are obtained from k8s command.
```
kubectl describe svc backend-playerapp
```

#### Output
<img width="845" alt="image" src="https://github.com/suresh22l/backend-playerapp/assets/39839103/73b90a79-cd6c-46cd-a87e-8413416c453c">



## Test Data
Player info for `Player` table and Game statistics for `Match` table are loaded inmemory H2 DB during springboot app load from the below file.

```bash
cat src/main/resources/player-data.sql
```

### Additional test data and script

100K of test data for Player profile is available at 
```bash
cat backend-playerapp/player-data-bkup.sql
```

Script to generate player test data is available at 
```bash
cat backend-playerapp/data.sh
```
