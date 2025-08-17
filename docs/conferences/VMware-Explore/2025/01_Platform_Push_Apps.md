Setup

Clean Postgres in console

```sql
drop schema retail cascade;
```





***********

```shell
cf push retail-web-app -f deployments/cloud/cloudFoundry/apps/retail-web-app/retail-web-app.yaml -b java_buildpack_offline -p applications/web-app/target/web-app-0.2.0.jar &
```

Manifest retail-web-app.yaml

```yaml
applications:
  - name: retail-cache-sink-app
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


Start Cache Sink Application

```shell

cf push retail-cache-sink-app -f deployments/cloud/cloudFoundry/apps/retail-cache-sink-app/retail-cache-sink-app.yaml  -b java_buildpack_offline -p applications/cache-sink-app/target/cache-sink-app-0.2.0-SNAPSHOT.jar &
```


Source Application

```shell
cf push retail-source-app -f deployments/cloud/cloudFoundry/apps/retail-source-app/retail-source-app.yaml  -b java_buildpack_offline -p applications/source-app/target/source-app-0.1.0-SNAPSHOT.jar &
```

Analytics

```shell
cf push retail-analytics-app -f deployments/cloud/cloudFoundry/apps/retail-analytics-app/retail-analytics-app-postgres.yaml  -b java_buildpack_offline -p applications/analytics-app/target/analytics-app-0.2.0-SNAPSHOT.jar &
```




View Applications 

```shell
cf apps
```

Open Apps Manager

