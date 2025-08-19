
cf push retail-cache-sink-app -f deployments/cloud/cloudFoundry/apps/retail-cache-sink-app/retail-cache-sink-app.yaml  -b java_buildpack_offline  -p applications/cache-sink-app/target/cache-sink-app-0.2.0.jar
