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

Variables: `MONGODB_URI`, `RABBITMQ_HOST`, `RABBITMQ_PORT`, `RABBITMQ_USERNAME`, `RABBITMQ_PASSWORD`.

## Build

```bash
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

## Nota

El servicio original `ms-java21-quarkus-tickets-v1` **todavía consume la misma cola**.
Si levantas ambos, se reparten los mensajes. Para probar solo este worker,
detén el `tickets-service` o quita su `@Incoming` / dependencia de Rabbit.
