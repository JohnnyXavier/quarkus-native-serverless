# quarkus webservice
this repo corresponds to the note [on architecture design: simple REST service on AWS with java](https://www.baremetalcode.com/arch-design-aws-serverless-java-simple/) 

---

to compile, package, deploy, etc..., refer to the above article or to the main repo's README file 

---

## commands on a pinch
* package: `mvn package -Pnative -Dquarkus.native.container-build=true -DskipTests=true`
* deploy: `sam deploy -t target/sam.native.yaml -g`
* start locally: `sam local start-api --template target/sam.native.yaml`