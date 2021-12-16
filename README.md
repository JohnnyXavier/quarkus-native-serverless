# quarkus-native-serverless

mvn package -Pnative -Dquarkus.native.container-build=true -DskipTests=true

sam deploy -t target/sam.native.yaml -g

sam local start-api --template target/sam.native.yaml