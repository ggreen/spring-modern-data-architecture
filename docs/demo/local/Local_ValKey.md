

Start ValKey
```shell
deployments/local/scripts/data-services/valkey/valkey-start.sh
```

Start Web App

```shell
java -jar applications/web-app/target/web-app-0.2.0.jar --spring.profiles.active=valkey --retail.customer.id=nyla
```


Start RabbitMQ

```shell
deployments/local/scripts/data-services/rabbitmq/install-rabbitmq.sh
```

Start Spring Cloud Data Flow

See https://dataflow.spring.io/



Register


```properties
sink.retail-analytics=file:///Users/Projects/solutions/Spring/dev/spring-modern-data-architecture/applications/cache-sink-app/target/cache-sink-app-0.2.0.jar
source.retail-source=file:///Users/Projects/solutions/Spring/dev/spring-modern-data-architecture/applications/source-app/target/source-app-0.1.0-SNAPSHOT.jar
```


Pipeline

```scdf
modern-stream=retail-source | retail-analytics
```

```properties
deployer.retail-analytics.bootVersion=3
deployer.retail-source.bootVersion=3
```
