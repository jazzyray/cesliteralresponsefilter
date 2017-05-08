CES Literal Response Mock API
=

Very, very simple and dumb CES Literal Response Mock API.

Echos CES document request as respoinse with additional literal features of the form:

```
        {
			"name": {
				"type": "XS_ANYURI",
				"name": "http://data.platts.com/ontology#cSource"
			},
			"value": {
				"type": "XS_STRING",
				"lang": "en",
				"value": "PGN"
			}
		} 
```


# Quick REST test

```
docker-compose up -d
```

## For swagger documentation
```
http://localhost:9108/swagger
```

## Curl Requests

### Request

``` 
```

#### Response
```
```

# Docker

## Build

```
docker build .
```
  
## Tag
### Get the image id

```
docker images
```

## Push to quay

### Login

```
docker login -e="." -u="ontotext+ontotext" -p="XXXX" quay.io
```

### tag
```
docker tag ${IMAGE} cesparenttreefiltermock 

docker tag ${IMAGE} quay.io/ontotext/cesliteralresponsefiltermock

```

### push to quay
```
docker push quay.io/ontotext/cesliteralresponsefiltermock

```

## Run Interactive
```
docker run --name cesliteralresponsefiltermock -it cesliteralresponsefiltermock /bin/bash
```   

## Run Daemon
```
docker run --name cesliteralresponsefiltermock -d cesliteralresponsefiltermock 
```

## Shell to docker container



### Get container ids
```
docker ps -a
```

```
docker exec -i -t ${CONTAINER_ID} /bin/bash
```



## Invoke

## Run via docker-compose

### Environment

Create a .env file with the correct environment settings

```
SOME_THING=XXX

```

### Interactive
```
docker-compose up
```

### Daemon
```
docker-compose up -d
