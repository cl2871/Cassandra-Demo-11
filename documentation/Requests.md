# Person Requests

Below are example requests you can make to this application server.

### List All People

```
GET: http://localhost:8080/person
```

Returns a list of all people stored in the cassandra database.

### List All People Named Musk

```
GET: http://localhost:8080/person/Musk
```

Returns a list of all people with name "Musk".

### Create a New Person

```
POST: localhost:8080/person

{
	"key": {
		"fullName": "Musk",
		"dateOfBirth": "1999-12-12",
		"id": "f4e7e29a-c42b-4d8f-b788-d4482e97d66c"
	},
	"age": 12
}
```

Note: the above is a request body in JSON format
