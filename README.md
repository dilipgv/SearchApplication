# Search

This is a basic search implementation for Users, Tickets and Organization.
  - Filter
  - Free text Search  

This repository provides REST APIs to query the entities listed above.

### API
We have a common API for Search-
*/api/v1/search*

Note : It supports only *POST* operation and you can see the usage below

### Filter
User can filter an *entity* using *FieldName* -> *FieldValue*
- classified as *type : 1* (default)

You can filter based on any of entities 
- **ticket**
- **organization**
- **user**

please see the sample data to find the available fields for each entities
 ```\src\main\resources\data```

Below is a sample request to filter - *Get All tickets of type "Incident"*
```sh
{
	"entity" : "ticket",
	"query" : {
		"field" : "type",
		"operator" : "=",
		"value" : "incident"
	}
}
```
Additionally , we have *Fetch* option using which you can get other data associated with an entity;
ex : you can fetch 'ticket' data along with 'user'
```sh
{
	"entity" : "organization",
	"query" : {
		"field" : "_id",
		"operator" : "=",
		"value" : "101"
	},
	"fetch" : ["ticket"]
}

```
Note : Currently only Operator '=' is implemented , we can extend this to support multitude of filter capabilities based on usecases.

### Free text Search

User can enter a free text ( absolute or partial ) to fetch entities matching the queryString.

Current implementation supports free text search ONLY based on the entity: Ticket ( Subject & Description) and can easily be extended to other entities and fields.
- classified as *type : 2* 

Below is the sample request to filter - *All tickets which has word "problem" in it*
```sh
{
	"type" : "2",
	"queryString" : "problem"
}
```

In the above example, "problem" is an absolute word which matches a word in a ticket , 
It is also possible to give partial words , example : "prob" or "mol"

```sh
{
	"type" : "2",
	"queryString" : "mol"
}
```

which matches the tokens : "mollit" and "moldova" and returns tickets for these words

### Sample Payloads
All the Sample payload are in the post collection  - */postmanCollection*

### Installation

1. Clone the github repository ```git clone https://github.com/dilipgv/SearchApplication.git```
2. Change directory to the folder ```cd SearchApplication```
3. Run the command ```mvn clean install```
4. Start the application ```java -jar target/search-0.0.1-SNAPSHOT.jar```

OR Use any IDE to build and run the application.

### Screen Shots
**Get Ticket based on a ID ( Filter)**
![Request](/images/searchIDTicketsReq.png)
![Response](/images/searchIDTicketsRes.png)

**Get Tickets of a given Type**
![Request](/images/GetAllTktsIncidentReq.png)
![Response](/images/GetAllTktsIncidentRes.png)

**Get Tickets along with its User and Org**
![Request](/images/GetAllTktswithUserAndOrgReq.png) 

**Get All Orgs with a given Tag**
![Request](/images/OrgWithtagsReq.png)
![Response](/images/OrgWithtagsRes.png)

**Search Ticket based on a Word**
![Request](/images/SearchWithTokenReq.png)
![Response](/images/SearchWithTokenRes.png)

**Search Ticket based on a word(partial)**
![Request](/images/SearchWithTokenPartialReq.png)
![Response](/images/SearchWithTokenPartialRes.png)