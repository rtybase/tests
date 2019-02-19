# Service how-to guide

This README contains the instructions on how to build and run locally the Auction-Bid-Tracker
Service. Make sure Java 8 and Maven 3.2.x are installed. Included shell scripts are for Linux/MacOS
only. Unfortunately, no Windows scripts are provided, but shell scripts are easy to
reproduce just by looking at the content.

Service limitations:

- In memory database
- Limited CRUD operations, deleting Users/Items is not supported. Deleting Bids is not supported.
- Pagination is not supported for any of the listing endpoints.
- Best bid and bids listings are ordered on request (O(n*log(n))).
- Bids never expire and auctions never end.

## 1. Build the code

From the command line, within the folder you unpacked the ZIP file.

```
mvn clean verify
```

The build will produce the `auction-bid-tracker-service-0.0.1-SNAPSHOT-dist.tar.gz` file in the "target/" folder.

## 2. Running the service

Unpack the tar.gz from the `target/` folder.

```
tar -xvzf auction-bid-tracker-service-0.0.1-SNAPSHOT-dist.tar.gz
```

This will produce a local folder `auction-bid-tracker-service/`. Open it

```
cd auction-bid-tracker-service
```

Start the service

```
./bin/run.sh
```

If you need to configure the service differently, check `etc/` folder for more details (i.e. `auction-bid-tracker-service.yaml`).

## 3. Check the service is running

From the browser, access service's health-check

```
http://localhost:3339/healthcheck?pretty=true
```

It should report something like.

```
{
  "db_state" : {
    "healthy" : true,
    "message" : "Operating with '0' items and '0' users"
  },
  "deadlocks" : {
    "healthy" : true
  }
}
```

Every time a new user or item is added, the counters will increase.

## 4. Create new User

From a terminal, execute

```
curl -vvv -X POST -H "Accept:*/*" -H "Content-Type:application/json" -H "Connection:close" -d '{"name":"test-user1"}' "http://localhost:3338/v1/users/"
```

and

```
curl -vvv -X POST -H "Accept:*/*" -H "Content-Type:application/json" -H "Connection:close" -d '{"name":"test-user2"}' "http://localhost:3338/v1/users/"
```

This will create 2 users. Multiple users with the same names **ARE NOT** allowed.

## 5. List Users

Execute

```
curl -vvv -X GET "http://localhost:3338/v1/users/"
```

This will list all the users created so far and their ID's.

## 6. Create new Item

Execute

```
curl -vvv -X POST -H "Accept:*/*" -H "Content-Type:application/json" -H "Connection:close" -d '{"name":"test-item"}' "http://localhost:3338/v1/items/"
```

Multiple items with the same name **ARE** allowed.

## 7. List Items

Execute

```
curl -vvv -X GET "http://localhost:3338/v1/items/"
```

This will list all the items created so far and their ID's.

## 8. Create a  Bid

In order to create a Bid, `itemId` and `userId` of the relevant Item and User are required (see sections 5 and 7 for the relevant list commands). 
The command is 

```
curl -vvv -X POST -H "Accept:*/*" -H "Content-Type:application/json" -H "Connection:close" -d '{"userId":"{userId}","bidValue":0.55}' "http://localhost:3338/v1/items/{itemId}/bids"
```

Replace `{userId}` and `{itemId}` with the relevant values (and adjust `bidValue` as needed).

If `{userId}` or `{itemId}` point to non-existing objects, the Service will reply with 404 and appropriate message

```
HTTP/1.1 404 Not Found
...

{"code":404,"message":"Item with id 'fd54fc64-7ac1-4e58-b26e-09ea7fc7d010' not found."}
```

or

```
{"code":404,"message":"User with id 'd8f5203bf00983e1897bcdb0a6a6097b343163cdb15e3fa040225242dd7b9e80' not found."}
```

And if `bidValue` is non-positive

```
HTTP/1.1 422 
...

{"errors":["bidValue must be positive."]}
```

## 9. View Bids

In order to view Bids details, `itemId` of the relevant Item is required (see section 7 for the relevant list command).
All item bids

```
curl -vvv -X GET "http://localhost:3338/v1/items/{itemId}/bids"
```

Best bid

```
curl -vvv -X GET "http://localhost:3338/v1/items/{itemId}/bestbid"
```

Replace `{itemId}` with the relevant value. If the relevant Item has no bids, empty lists are returned.
If `{itemId}` point to a non-existing Item, the Service will reply with 404 and appropriate message.

```
{"code":404,"message":"Item with id 'fd54fc64-7ac1-4e58-b26e-09ea7fc7d010' not found."}
```

## 10. View Items that User had placed Bids on

In order to view User's Items details, `userId` of the relevant User is required (see section 5 for the relevant list command).

```
curl -vvv -X GET "http://localhost:3338/v1/users/{userId}/items"
```

Replace `{userId}` with the relevant value. If the relevant User has no Bids at all, empty list is returned.
If `{userId}` point to a non-existing User, the Service will reply with 404 and appropriate message.

```
{"code":404,"message":"User with id 'd8f5203bf00983e1897bcdb0a6a6097b343163cdb15e3fa040225242dd7b9e80' not found."}
```
