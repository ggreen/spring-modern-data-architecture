#
# /*
#  * Copyright 2023 VMware, Inc.
#  * SPDX-License-Identifier: GPL-3.0
#  */
#



# List Services

cf marketplace

# AI

local-llama-33-70b-instruct
#cf create-service genai multi-model-rag-ultra retail-ai
# qwen 3 ultra plan = prod-chat-tools-qwen3-ultra,
# and nomic embed plan = prod-embedding-nomic-text, rod-chat-tools-qwen3-ultra
#cf create-service genai prod-chat-tools-qwen3-ultra retail-ai-chat
cf create-service genai local-llama-33-70b-instruct retail-ai-chat
cf create-service genai prod-embedding-nomic-text retail-ai-embedding
cf create-service genai local-mistrall-small-32-2506-gpu retail-ai-mistrall



# Valkey

cf create-service p.redis on-demand-cache retail-caching
## GemFire

#small
#cf create-service p-cloudcache extra-small  retail-gemfire -t gemfire

##  SQL

cf create-service postgres on-demand-postgres-db retail-sql

#cf create-service  p.mysql db-small retail-scdf-sql


## RabbitMQ

cf create-service p.rabbitmq on-demand-plan retail-messaging  -c '{ "plugins": { "rabbitmq_stream": true, "rabbitmq_stream_management": true } }'

#cf create-service p.rabbitmq single-node retail-messaging  -c '{ "plugins": { "rabbitmq_stream": true, "rabbitmq_stream_management": true } }'

#cf update-service retail-messaging -c '{ "plugins": { "rabbitmq_stream": true, "rabbitmq_stream_management": true } }'

# Prometheus
#cf push prometheus --docker-image prom/prometheus --var PORT=9090
# ----------------
# SCDF DataFlow

cf target -s data-flow-demo
cf create-service p-dataflow standard scdf -c '{"skipper": { "disk": "5GiB" }, "dataflow" : { "disk": "5GiB" }}'

 -c '{"services": ["retail-scdf-sql","retail-messagain"] }'

# -----------------------------
# WAIT FOR SERVICE to be available

rabbit_status=`cf service retail-messaging | grep status:`

while [[ "$rabbit_status" != *"create succeeded"* ]]
do
  echo "Waiting for Rabbitmq, current status:" $rabbit_status
  sleep 1
  rabbit_status=`cf service retail-messaging | grep status:`
done


mysql_status=`cf service retail-sql | grep status:`
echo "Waiting for sql database, current status:" $mysql_status
while [[ "$mysql_status" != *"create succeeded"* ]]
do
  echo "Waiting for database, current status:" $mysql_status
  sleep 1
  mysql_status=`cf service retail-sql | grep status:`
done


caching_status=`cf service retail-caching | grep status:`
echo "Waiting for caching service, current status:" $caching_status
while [[ "$caching_status" != *"create succeeded"* ]]
do
  echo "Waiting for caching service, current status:" $caching_status
  caching_status=`cf service retail-caching | grep status:`
  sleep 1
done


mysql_status=`cf service retail-scdf-sql | grep status:`
echo "Waiting for retail-scdf-sql, current status:" $mysql_status
while [[ "$mysql_status" != *"create succeeded"* ]]
do
  echo "Waiting for retail-scdf-sql, current status:" $mysql_status
  mysql_status=`cf service retail-scdf-sql | grep status:`
  sleep 1
done


data_flow_status=`cf service data-flow | grep status:`
echo "Waiting for data-flow, current status:" $data-flow_status
while [[ "$data-flow_status" != *"create succeeded"* ]]
do
  echo "Waiting for retail-scdf-sql, current status:" $data-flow_status
  data_flow_status=`cf service data-flow | grep status:`
  sleep 1
done



#--------------------
# Push Applications
#cf push retail-cache-sink-app -f deployments/cloud/cloudFoundry/apps/retail-cache-sink-app/retail-cache-sink-app.yaml -p applications/cache-sink-app/target/cache-sink-app-0.2.0.jar
./deployments/cloud/cloudFoundry/apps/retail-cache-sink-app/cf-push.sh
# retail-source-app
./deployments/cloud/cloudFoundry/apps/retail-source-app/cf-push.sh

# retail-web-app
./deployments/cloud/cloudFoundry/apps/retail-web-app/cf-push.sh

# retail-analytics-app
./deployments/cloud/cloudFoundry/apps/retail-analytics-app/cf-push.sh

# jdbc-sql-console-app
./deployments/cloud/cloudFoundry/apps/jdbc-sql-console-app/cf-push.sh
#cf push jdbc-sql-console-app -f deployments/cloud/cloudFoundry/apps/jdbc-sql-console-app/jdbc-sql-console-app.yaml -p applications/jdbc-sql-console-app/target/jdbc-sql-console-app-0.0.2.jar

#-------------------
# Create a service key GemFire
cf create-service-key retail-caching retail-caching-key

# Inspect the service key:
cf service-key retail-caching retail-caching-key


#-------------------
# Create a service key RabbitMQ
cf create-service-key retail-messaging retail-messaging-key -c '{"tags":"administrator"}'

# Inspect the service key:
cf service-key retail-messaging retail-messaging-key


#-------------------
# Create a service key SQL
cf create-service-key retail-sql retail-sql-key

# Inspect the service key:
cf service-key retail-sql retail-sql-key


#./deployments/cloud/cloudFoundry/apps/gemfire-gideon-console/cf-push.sh



