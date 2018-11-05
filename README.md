# Cloud HDI ZDM Reference Application

# Description
Cloud HDI (HANA Deployment Infrastructure) ZDM (Zero-Downtime Maintenance) Reference Application, or `cloud-hdi-zdm-ref-app`, is a reference application, which demonstrates how to develop Multi-Target Applications with HDI content, which support zero-downtime updates. It is based on the [Muti-Target Application (MTA)](https://www.sap.com/documents/2016/06/e2f618e4-757c-0010-82c7-eda71af511fa.html) model and follows the [Zero-Downtime Maintenance (ZDM) Adoption Guideline](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/e62731aa735340bfb0c4b7c71b4bf5e7.html) in order to support zero-downtime updates. It contains database (db) module and backend module. The database module contains HDI artifacts, which are modeled in specific ZDM manner. This HDI artifacts are deployed to HDI containers, where are materialized to database objects. The backend module is a Java application, which consumes the database objects at runtime. There are two versions of the ZDM reference application - blue and green. It can be used on [SAP Cloud Platform Cloud Foundry (SAP CP CF)](https://cloudplatform.sap.com/enterprise-paas/cloudfoundry.html) and [SAP XS Advanced (XSA)](https://help.sap.com/viewer/4505d0bdaf4948449b7f7379d24d0f0d/2.0.00/en-US/df19a03dc07e4ba19db4e0006c1da429.html) environments.

## Components
### cloud-hdi-zdm-ref-app.db.blue
Contains blue version of database module.

### cloud-hdi-zdm-ref-app.backend.jee.blue
Contains blue version of backend module.

### cloud-hdi-zdm-ref-app.db.green
Contains green version of database module. It contains compatible and incompatible database changes.

### cloud-hdi-zdm-ref-app.backend.jee.green
Contains green version of backend module.

# Requirements
## Download and Installation
You need to download and install [Java 8](https://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html), [Apache Maven](http://maven.apache.org/) and [MTA Build Tool](https://tools.hana.ondemand.com/#cloud). You can find more information for the MTA Build Tool [here](https://help.sap.com/viewer/58746c584026430a890170ac4d87d03b/Cloud/en-US/ba7dd5a47b7a4858a652d15f9673c28d.html).

## Running
The blue and green versions of the ZDM reference application are deployed as a Multi-Target Applications (MTAs) in SAP CP CF or 
XSA environments. You need to:
1. Download and install [Cloud Foundry Command Line Interface (cf CLI)](https://docs.cloudfoundry.org/cf-cli/install-go-cli.html).
2. Download and install [CF MTA CLI Plugin](https://github.com/cloudfoundry-incubator/multiapps-cli-plugin).
3. Get access to SAP Cloud Platform Cloud Foundry or XSA instance.
4. Login to the SAP CP CF/XSA API and target an organization and space. You can find more information [here](https://docs.cloudfoundry.org/cf-cli/getting-started.html). The cloud-hdi-zdm-ref-app will be deployed in the targeted organization and space. Deploy Service should exist in the SAP CP CF/XSA instance. Additionally you can target custom Deploy Service with the environment variable `DEPLOY_SERVICE_URL=<Deploy Service URL>`.
5. The ZDM reference application requires a HANA service instances for persistence. For Cloud Foundry environment, you should have a [tenant HANA database instance](https://help.sap.com/viewer/d4790b2de2f4429db6f3dff54e4d7b3a/Cloud/en-US/a5ca88752703418fbad79a0f541246ab.html) for your organization and space.

# Download and Installation

1. Clone the repository
```
$ git clone https://github.wdf.sap.corp/xs2ds/cloud-hdi-zdm-ref-app/tree/mta-build-tool
```
2. Build the blue version of the cloud-hdi-zdm-ref-app, by execution of the following command from cloud-hdi-zdm-ref-app.blue/mta-jee/ directory:
```
$ java -jar <path to MTA Build Tool *.jar> --build-target=CF --mtar=cloud-hdi-zdm-ref-app-blue-<version>.mtar build
```
3. Build the green version of the cloud-hdi-zdm-ref-app, by execution of the following command from cloud-hdi-zdm-ref-app.green/mta-jee/ directory:
```
$ java -jar <path to MTA Build Tool *.jar> --build-target=CF --mtar=cloud-hdi-zdm-ref-app-green-<version>.mtar build
```
4. The deployable results from building components are .mtar files, located at:
- `cloud-hdi-zdm-ref-app.blue/mta-jee/cloud-hdi-zdm-ref-app-blue-<version>.mtar`
- `cloud-hdi-zdm-ref-app.green/mta-jee/cloud-hdi-zdm-ref-app-green-<version>.mtar`

# Running

## Deploy blue version 
Make a blue-green deployment of the blue version of the ZDM reference application by execution of the following command from root directory:
```
$ cf bg-deploy cloud-hdi-zdm-ref-app.blue/mta-jee/cloud-hdi-zdm-ref-app-blue-<version>.mtar -e config.mtaext
```
Now the blue version is deployed. This phase of the ZDM blue-green deployment process is called `install` phase.

## Test blue version
After the blue version is deployed you can view the URL on which the `cloud-hdi-zdm-ref-app-backend-blue` module is available (live route) and test it's REST resources:
```
$ curl <live URL (cloud-hdi-zdm-ref-app-backend-blue)>/api/v1/cdsPerson/version -k
```
The response should be `BLUE`

```
$ curl <live URL (cloud-hdi-zdm-ref-app-backend-blue)>/api/v1/cdsPerson -k
```
The response should be `{"cdsPerson":{"id":<ID>,"firstName":"FirstName <ID>","lastName":"LastName <ID>"}}`

## Deploy green version
Make a blue-green deployment of the green version of the ZDM reference application by execution of the following command from the root directory:
```
$ cf bg-deploy cloud-hdi-zdm-ref-app.green/mta-jee/cloud-hdi-zdm-ref-app-green-<version>.mtar -e config.mtaext
```
Now both the blue and green versions are deployed and are running. This phase of the ZDM blue-green deployment process is called `start` phase.

## Test green version
After the green version is deployed you can view the URL on which the `cloud-hdi-zdm-ref-app-backend-green` module is available (idle route) and test it's REST resources:
```
$ curl <idle URL (cloud-hdi-zdm-ref-app-backend-green)>/api/v1/cdsPerson/version -k
```
The response should be `GREEN`

```
$ curl <idle URL (cloud-hdi-zdm-ref-app-backend-green)>/api/v1/cdsPerson -k
```
The response should be `{"cdsPerson":{"id":<ID>,"firstName":"FirstName <ID>","lastName":"LastName <ID>"}}`

## Resume the blue-green deployment process
After the deployment of the green version, Deploy Service reports a command with which the blue-green process can be resumed:
```
$ cf bg-deploy -a resume -i <process id>
```

After the blue-green process is resumed the blue version of the modules and access service are deleted and the idle routes are switched to live routes. 

## Test green version
You can test the green version of the backend module `cloud-hdi-zdm-ref-app-backend-green` on the live route:
```
$ curl <live URL (cloud-hdi-zdm-ref-app-backend-green)>/api/v1/cdsPerson/version -k
```
The response should be `GREEN`

```
$ curl <live URL (cloud-hdi-zdm-ref-app-backend-green)>/api/v1/cdsPerson -k
```
The response should be `{"cdsPerson":{"id":<ID>,"firstName":"Fname <ID>","lastName":"Fname <ID>"}}`

```
curl <idle URL>/api/v1/cdsPerson -k
```
The response should be `HTTP: 404`

# Known Issues
* Currently there is one backend module implemented in Java. In future will be provided more backend modules implemented in different languages, like Node.js, Python, etc. 
* References in module properties in the MTA deployment descriptors are currently not used. They will be used in future.

# How to obtain support
If you need any support, have any question or have found a bug, please report it in the [GitHub bug tracking system](https://github.com/SAP/cloud-hdi-zdm-reference-app/issues). We shall get back to you.

# License
This project is licensed under SAP SAMPLE CODE LICENSE AGREEMENT except as noted otherwise in the [LICENSE](./LICENSE) file.

# Further reading
* [ZDM for Multi-Target Applications (MTA) with Database Changes on SAP CP CF](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/e62731aa735340bfb0c4b7c71b4bf5e7.html)
* [ZDM for Multi-Target Applications (MTA) with Database Changes on SAP XSA](https://help.sap.com/viewer/4505d0bdaf4948449b7f7379d24d0f0d/2.0.03/en-US/a7afca80f35c444c8d2e4ab42f5ec06d.html)
* [Muti-Target Application (MTA) model](https://www.sap.com/documents/2016/06/e2f618e4-757c-0010-82c7-eda71af511fa.html)
* [Developing SAP HANA in the Cloud Foundry Environment](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/14224d75f6c64b499d189e3ebd131ec2.html)
* [Maintaining HDI Containers](https://help.sap.com/viewer/4505d0bdaf4948449b7f7379d24d0f0d/2.0.01/en-US/23f1f40731504e7eb7e4ec4b65cbfa71.html)
