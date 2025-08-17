#
# /*
#  * Copyright 2023 VMware, Inc.
#  * SPDX-License-Identifier: GPL-3.0
#  */
#

#--------------------
# Push Applications
# jdbc-sql-console-app
cf push jdbc-sql-console-app -f deployments/cloud/cloudFoundry/apps/jdbc-sql-console-app/jdbc-sql-console-app.yaml -b java_buildpack_offline -p applications/jdbc-sql-console-app/target/jdbc-sql-console-app-0.0.4.jar
