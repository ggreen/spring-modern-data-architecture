#-v postgresml_data:/var/lib/postgresql \
podman run --name=postgresml --rm \
    -it \
    -p 5433:5432 \
    -p 8000:8000 \
    ghcr.io/postgresml/postgresml:2.10.0 \
    sudo -u postgresml psql -d postgresml