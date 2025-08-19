
![overview.png](docs/overview.png)

Setup

```shell
cf target -s data-demo
cf push valkey-console-app -f deployments/cloud/cloudFoundry/apps/valkey-console-app/valkey-console-app.yaml  -b java_buildpack_offline -p applications/valkey-console-app/target/valkey-console-app-0.0.2.jar
cf push jdbc-sql-console-app -f deployments/cloud/cloudFoundry/apps/jdbc-sql-console-app/jdbc-sql-console-app.yaml -b java_buildpack_offline -p applications/jdbc-sql-console-app/target/jdbc-sql-console-app-0.0.4.jar
cf target -s data-flow-demo
```

Clean Postgres in console

```sql
delete from retail.products;
delete from retail.customer_orders;
delete from retail.vector_store;



Delete ValKey Data


Delete Applications

```shell
cf delete retail-analytics-app -f
cf delete retail-cache-sink-app  -f
cf delete retail-source-app   -f
cf delete retail-web-app  -f
```


***********

Push Web App

```shell
cf push retail-web-app -f deployments/cloud/cloudFoundry/apps/retail-web-app/retail-web-app.yaml -b java_buildpack_offline -p applications/web-app/target/web-app-0.2.0.jar &
```

Source Application

```shell
cf push retail-source-app -f deployments/cloud/cloudFoundry/apps/retail-source-app/retail-source-app.yaml  -b java_buildpack_offline -p applications/source-app/target/source-app-0.2.0.jar &
```

Analytics

```shell
cf push retail-analytics-app -f deployments/cloud/cloudFoundry/apps/retail-analytics-app/retail-analytics-app-postgres.yaml  -b java_buildpack_offline -p applications/analytics-app/target/analytics-app-0.2.0.jar &
```

Start Cache Sink Application

```shell

cf push retail-cache-sink-app -f deployments/cloud/cloudFoundry/apps/retail-cache-sink-app/retail-cache-sink-app.yaml  -b java_buildpack_offline -p applications/cache-sink-app/target/cache-sink-app-0.2.0.jar &
```


Manifest retail-web-app.yaml

```yaml
applications:
  - name: retail-web-app
    memory: 1400M
    log-rate-limit-per-second: -1
    services:
      - retail-caching
    env:
      spring.profiles.active: valkey
      #      spring.profiles.active: gemfire
      JBP_CONFIG_OPEN_JDK_JRE: '{ jre: { version: 17.+}}'
      retail.customer.id: 'nyla'
```

Show Create Services in 
```shell
cf target -s data-flow-demo
```


Create AI Model Services
```shell
cf create-service genai local-llama-33-70b-instruct retail-ai-chat
cf create-service genai prod-embedding-nomic-text retail-ai-embedding
cf create-service genai local-mistrall-small-32-2506-gpu retail-ai-mistrall
```


View Services

```shell
cf services
```

Review how applications
Reviews Services

retail-source-app Manifest

```yaml
applications:
  - name: retail-source-app
    memory: 1400M
    log-rate-limit-per-second: -1
    services:
      - retail-messaging
    env:
      JBP_CONFIG_OPEN_JDK_JRE: '{ jre: { version: 17.+}}'
```

retail-analytics-app

```yaml
applications:
  - name: retail-analytics-app
    memory: 1400M
    log-rate-limit-per-second: -1
    services:
      - retail-messaging
      - retail-sql
      - retail-ai-chat
      - retail-ai-embedding
    env:
      JBP_CONFIG_OPEN_JDK_JRE: '{ jre: { version: 17.+}}'
      spring.profiles.active: 'openai,postgres,rabbit-product-quorum'
```

retail-cache-sink-app

```yaml
applications:
  - name: retail-cache-sink-app
    memory: 1400M
    log-rate-limit-per-second: -1
    services:
      - retail-messaging
      - retail-caching
    env:
      JBP_CONFIG_OPEN_JDK_JRE: '{ jre: { version: 17.+}}'
      SPRING_PROFILES_ACTIVE: 'valkey'
#      SPRING_PROFILES_ACTIVE: 'gemfire'
```






View Applications 

```shell
cf target -s data-demo
```

```shell
cf apps
```

Open Apps Manager


- Open Web App
- Open Source


Load Products


Open RabbitMQ

Submit Orders

Submit Reviews

Submit Context

Submit Reviews

Open Logs in App Metrics

Search Meatloaf Review


-------------

View Applications

```shell
cf target -s data-demo
```


Access SCDF



```scdf
retail-stream=retail-source --server.port=80 | retail-analytics  --spring.profiles.active=openai,postgres,product-quorum | cache-sink --spring.profiles.active=valkey
retail-app=retail-web-app --server.port=80 --spring.profiles.active=valkey --retail.customer.id=nyla
```

Retail Web Application

```properties
app.retail-web-app.retail.customer.id=nyla
app.retail-web-app.server.port=80
app.retail-web-app.spring.profiles.active=valkey
deployer.retail-web-app.bootVersion=3
deployer.retail-web-app.cloudfoundry.env.JBP_CONFIG_OPEN_JDK_JRE={jre: {version: 17.+}}
deployer.retail-web-app.cloudfoundry.services=retail-caching
```

Retail Stream
```properties
app.cache-sink.spring.profiles.active=valkey
app.retail-analytics.spring.profiles.active=openai,postgres,product-quorum
app.retail-source.server.port=80
deployer.retail-source.bootVersion=3
deployer.retail-source.cloudfoundry.env.JBP_CONFIG_OPEN_JDK_JRE={jre: {version: 17.+}}
deployer.retail-source.cloudfoundry.services=retail-sql,retail-ai-chat,retail-ai-embedding
deployer.retail-source.cloudfoundry.memory=1400m
deployer.retail-analytics.bootVersion=3
deployer.retail-analytics.cloudfoundry.env.JBP_CONFIG_OPEN_JDK_JRE={jre: {version: 17.+}}
deployer.retail-analytics.cloudfoundry.services=retail-sql,retail-ai-chat,retail-ai-embedding
deployer.retail-analytics.cloudfoundry.memory=1400m
deployer.cache-sink.bootVersion=3
deployer.cache-sink.cloudfoundry.env.JBP_CONFIG_OPEN_JDK_JRE={jre: {version: 17.+}}
deployer.cache-sink.cloudfoundry.services=retail-caching
deployer.cache-sink.cloudfoundry.memory=1400m
```

