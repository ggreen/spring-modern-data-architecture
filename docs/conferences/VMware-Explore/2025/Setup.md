Setup

Create Services

```shell
cf target -s data-demo
cf target -s data-flow-demo
```

````shell
cf create-service genai local-llama-33-70b-instruct retail-ai-chat
cf create-service genai prod-embedding-nomic-text retail-ai-embedding
cf create-service genai local-mistrall-small-32-2506-gpu retail-ai-mistrall
````

```shell
cf target -s data-demo
cf push valkey-console-app -f deployments/cloud/cloudFoundry/apps/valkey-console-app/valkey-console-app.yaml  -b java_buildpack_offline -p applications/valkey-console-app/target/valkey-console-app-0.0.2.jar &
cf push jdbc-sql-console-app -f deployments/cloud/cloudFoundry/apps/jdbc-sql-console-app/jdbc-sql-console-app.yaml -b java_buildpack_offline -p applications/jdbc-sql-console-app/target/jdbc-sql-console-app-0.0.4.jar &
cf target -s data-flow-demo
cf push scdf-valkey-console-app -f deployments/cloud/cloudFoundry/apps/valkey-console-app/valkey-console-app.yaml  -b java_buildpack_offline -p applications/valkey-console-app/target/valkey-console-app-0.0.2.jar &
cf push scdf-jdbc-sql-console-app -f deployments/cloud/cloudFoundry/apps/jdbc-sql-console-app/jdbc-sql-console-app.yaml -b java_buildpack_offline -p applications/jdbc-sql-console-app/target/jdbc-sql-console-app-0.0.4.jar &
```
