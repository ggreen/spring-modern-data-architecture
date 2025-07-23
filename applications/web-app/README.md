# Running web-app


Prerequisite 

Start with ValKey

```shell
deployments/local/scripts/data-services/valkey/valkey-start.sh
```


```shell
java -jar applications/web-app/target/web-app-0.2.0-SNAPSHOT.jar --spring.profiles.active=valkey --retail.customer.id=nyla
```


## Docker building image

```shell
mvn install
cd applications/web-app
mvn spring-boot:build-image
```

```shell
docker tag web-app:0.2.0-SNAPSHOT cloudnativedata/web-app:0.2.0-SNAPSHOT
docker push cloudnativedata/web-app:0.2.0-SNAPSHOT
```


# Testing



Product data

```json
[
  {
    "id": "skuMeatLoaf",
    "name": "Meatloaf"
  }
]
```

Product Reviews

```json
{
  "id": "skuMeatLoaf",
  "customerReviews": [
    {
      "id": "mom",
      "review": "I think it is yummy",
      "sentiment": "POSITIVE"
    },
    {
      "id": "boy",
      "review": "NOT YUMMY",
      "sentiment": "NEGATIVE"
    }
  ]
}
```


```shell
curl -X 'POST' \
  'http://localhost:8080/products/product/review' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "skuMeatLoaf",
  "customerReviews": [
    {
      "id": "mom",
      "review": "I think it is yummy",
      "sentiment": "POSITIVE"
    },
    {
      "id": "boy",
      "review": "NOT YUMMY",
      "sentiment": "NEGATIVE"
    }
  ]
}'
```


Published PRomoted



```shell
curl -X 'POST' \
  'http://localhost:8080/promotions/promotion/publish' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "nyla",
  "marketingMessage": "Hi",
  "products": [
    {
      "id": "meatloaf",
      "name": "MeatLoaf 100% Free"
    }
  ]
}'
```



## Customer Favorites


```shell
curl -X 'POST' \
  'http://localhost:8080/customer/favorites/favorite' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "nyla",
  "favorites": [
    {
      "product": {
        "id": "skuMeatLoaf",
        "name": "Meatloaf"
      },
      "quantity": 1
    }
  ]
}'
```
