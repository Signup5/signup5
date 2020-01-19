# Getting PostgreSQL up & running in a development environment

## Create and start a Docker container with a recent PostgreSQL
```
docker run --name pg_signup5 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=password -e POSTGRES_DB=signup -p 5432:5432 -d postgres:12
```

## Make sure the container is running
```
docker ps
```

## Stop the container
```
docker stop pg_signup5
```

## Re-start the container
```
docker start pg_signup5
```

## Remove the docker container (to make a fresh install)
```
docker rm pg_signup5
```