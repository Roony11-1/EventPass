# ms-java21-quarkus-tickets-worker-v1

Worker independiente que consume `ordenes-creada` y emite tickets.

Es una copia de las clases necesarias de `ms-java21-quarkus-tickets-v1`:

- `core/**` (dominio, casos de uso y puerto de repositorio)
- `infrastructure/persistence/mongo/**`
- `infrastructure/messaging/OrdenCreadaConsumer`

No tiene API HTTP ni pruebas unitarias. Solo health en `:9000/q/health`.

## Ejecutar

```bash
./mvnw quarkus:dev
```

Variables: `MONGODB_URI`, `MONGODB_DATABASE` (default `eventpass_tickets`),
`RABBITMQ_HOST`, `RABBITMQ_PORT`, `RABBITMQ_USERNAME`, `RABBITMQ_PASSWORD`
y `WORKER_MANAGEMENT_PORT` (default `9000`).

## Build

```bash
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

## Docker

- `Dockerfile`: imagen JVM usada por `docker-compose.yml`.
- `Dockerfile.native`: build nativo con Mandrel, pensado para el publisher
  nativo de GitHub Actions.

## Nota

El servicio `ms-java21-quarkus-tickets-v1` ya **no consume la cola**: solo expone
la API REST de consulta/validación. Este worker es el único consumidor de
`q.tickets.ordenes-creadas` y el único que publica en `tickets.emitidos`.
