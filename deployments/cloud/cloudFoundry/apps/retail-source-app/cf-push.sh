#--------------------
# Push Applications
# retail-source-app
cf push retail-source-app -f deployments/cloud/cloudFoundry/apps/retail-source-app/retail-source-app.yaml -p applications/source-app/target/source-app-0.1.0-SNAPSHOT.jar

