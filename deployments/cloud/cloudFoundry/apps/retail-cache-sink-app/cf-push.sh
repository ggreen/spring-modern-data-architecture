
cf push retail-cache-sink-app -f deployments/cloud/cloudFoundry/apps/retail-cache-sink-app/retail-cache-sink-app.yaml -p applications/cache-sink-app/target/cache-sink-app-0.2.0-SNAPSHOT.jar
#--------------------
# Push Applications
# jdbc-sql-console-app
#cf push jdbc-sql-console-app -f deployments/cloud/cloudFoundry/apps/jdbc-sql-console-app/jdbc-sql-console-app.yaml -p applications/jdbc-sql-console-app/target/jdbc-sql-console-app-0.0.1-SNAPSHOT.jar

