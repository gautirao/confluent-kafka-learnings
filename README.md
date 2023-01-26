# this is an example project to work with confluent cloud.
#Prequisite:
- You have signed up to confluent cloud for their trial account
- set up a test cluster
- generated api keys for the cluster and schema registry

#Commands
-  mvn schema-registry:register
-  mvn schema-registry:set-compatibility
- mvn schema-registry:test-compatibility
- mvn schema-registry:test-local-compatibility