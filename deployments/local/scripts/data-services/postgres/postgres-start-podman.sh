docker run --rm -it \
  --name postgresql \
  -e POSTGRESQL_USERNAME=postgres \
  -e POSTGRESQL_DATABASE=postgres \
  -e ALLOW_EMPTY_PASSWORD=yes \
  -p 5432:5432 \
  bitnami/postgresql:latest