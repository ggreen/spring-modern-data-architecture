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