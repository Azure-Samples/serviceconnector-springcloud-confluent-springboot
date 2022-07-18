---
title: 'Tutorial: Deploy a SpringBoot app connected to Apache Kafka on Confluent Cloud with Service Connector'
description: .
ms.devlang: Java
author: shizn
ms.author: xshi
ms.service: service-connector
ms.topic: tutorial
ms.date: 10/28/2021
zone_pivot_groups: 
page_type: sample
languages:
- java
products:
- service-connector
---
# Tutorial: Deploy a SpringBoot app connected to Apache Kafka on Confluent Cloud with Service Connector

## 1. Set up your initial environment

1. Have an Azure account with an active subscription. [Create an account for free](https://azure.microsoft.com/free/?ref=microsoft.com&utm_source=microsoft.com&utm_medium=docs&utm_campaign=visualstudio).
2. Install Java 8 or 11 </a>.
3. Install the <a href="/cli/azure/install-azure-cli" target="_blank">Azure CLI</a> 2.18.0 or higher, with which you run commands in any shell to provision and configure Azure resources.

---

Check that your Azure CLI version is 2.18.0 or higher:

```azurecli
az --version
```

If you need to upgrade, try the `az upgrade` command (requires version 2.11+) or see <a href="/cli/azure/install-azure-cli" target="_blank">Install the Azure CLI</a>.

Then sign in to Azure through the CLI:

```azurecli
az login
```

This command opens a browser to gather your credentials. When the command finishes, it shows JSON output containing information about your subscriptions.

Once signed in, you can run Azure commands with the Azure CLI to work with resources in your subscription.

Having issues? [Let us know](https://aka.ms/DjangoCLITutorialHelp).

## 2. Clone or download the sample app


Clone the sample repository:

```terminal
git clone https://github.com/Azure-Samples/serviceconnector-springcloud-confluent-springboot
```

Then navigate into that folder:

```terminal
cd java-springboot
```

## 3. Create Apache Kafka on Confluent Cloud

### 3.1 Create Confluent Organization on Azure

Portal

https://docs.microsoft.com/en-us/azure/partner-solutions/apache-kafka-confluent-cloud/create

CLI

https://docs.microsoft.com/en-us/azure/partner-solutions/apache-kafka-confluent-cloud/create-cli

### 3.2 Create Kafka cluster and schema registry on Confluent Cloud
1. login to Confluent Cloud by SSO provided by Azure
2. Use the default environment or create a new one
3. Get kafka bootstrap server url(value1)
4. Create a Kafka cluster
5. Create API-keys for the cluster and remember the key & secret(value2, value3)
6. create a topic named test1
7. Enable the Schema Registry and get the endpoint of it(value4)
8. Create API-keys for schema registry and remember it(value5, value6)

## 4. Create a Spring Cloud application

https://docs.microsoft.com/en-us/azure/spring-cloud/quickstart?tabs=Azure-CLI&pivots=programming-language-java#provision-an-instance-of-azure-spring-cloud

## 5. Build and deploy the app

1. Sign in to Azure and choose your subscription.

Azure CLI

```
az login

az account set --subscription <Name or ID of a subscription from the last step>
```


2. Build the project using gradle

```
./gradlew build
```

3. Create the app with public endpoint assigned. If you selected Java version 11 when generating the Spring Cloud project, include the --runtime-version=Java_11 switch.

```
az spring-cloud app create -n hellospring -s <service instance name> -g <resource group name> --assign-endpoint true
```


4. Create connection(screenshot will be provided after portal is ready)

select target resource type(Apache Kafka on Confluent cloud) and client type(Spring boot), then fill the 3 data fields
User can check the box "create connection for schema registry" and fill 3 data fields


5. Deploy the Jar file for the app ( build/libs/java-springboot-0.0.1-SNAPSHOT.jar):

```
az spring-cloud app deploy -n hellospring -s <service instance name> -g <resource group name>  --artifact-path build/libs/java-springboot-0.0.1-SNAPSHOT.jar
```


## 6. Validation
Get spring cloud app's endpoint on Portal
access the url <app endpoint>/produce by browser

You will see "10 messages were produced to topic test1"
Then go to the Confluent portal and the topic's page will show production throughput
