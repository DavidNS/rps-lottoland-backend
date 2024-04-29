# How to run

## With maven and java

### Requirements

Tested with the following versions:

- Java 17
- [Maven](https://maven.apache.org/install.html) 3.6.3

### Steps

Open a terminal from the root folder of this project and run:

```bash
mvn clean install
java -jar target/rps-lottoland-backend-1.0.1-SNAPSHOT.jar

```

## With docker and docker-compose

### Requirements

Tested with the following versions:

- [Docker](https://www.docker.com/get-started) 26.1.0
- [Docker compose](https://docs.docker.com/compose/) 1.29.2

### Steps

Make sure your docker daemon is running. In case you have [docker desktop](https://www.docker.com/products/docker-desktop/), just open it. Else check [docker daemon docs](https://docs.docker.com/config/daemon/start/).

Open a terminal from the root folder of this project and run:

```bash
sudo docker-compose -f docker-compose.yaml up
```

Depending on your docker compose version you may need to run:

```bash
sudo docker compose -f docker-compose.yaml up
```