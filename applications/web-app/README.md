# Running web-app


Prerequiste 

Start with ValKey

```shell
deployments/local/scripts/data-services/valkey/valkey-start.sh
```


```shell
java -jar applications/web-app/target/web-app-0.2.0-SNAPSHOT.jar --spring.profile.active=valkey --retail.customer.id=nyla
```



## Docker building image

```shell
mvn install
cd applications/retail-web-app
mvn spring-boot:build-image
```

```shell
docker tag retail-web-app:0.1.0-SNAPSHOT cloudnativedata/retail-web-app:0.1.0-SNAPSHOT
docker push cloudnativedata/retail-web-app:0.1.0-SNAPSHOT
```