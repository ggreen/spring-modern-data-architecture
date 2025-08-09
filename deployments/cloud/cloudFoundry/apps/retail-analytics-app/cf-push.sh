#
# /*
#  * Copyright 2023 VMware, Inc.
#  * SPDX-License-Identifier: GPL-3.0
#  */
#

#--------------------
# Push Applications
# retail-analytics-app
cf push retail-analytics-app -f deployments/cloud/cloudFoundry/apps/retail-analytics-app/retail-analytics-app-postgres.yaml -p applications/analytics-app/target/analytics-app-0.2.0-SNAPSHOT.jar

