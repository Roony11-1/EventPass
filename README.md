# EventPass

Monorepo de microservicios para gestión de eventos, órdenes y tickets
(JVY0101 — Evaluación Parcial N°2).

Todo el tráfico externo entra por el **gateway** (`:8080`); los servicios
hablan entre sí por la red interna de Docker.

| Servicio | Stack | Dato | Puerto interno |
|---|---|---|---|
| `gateway-service` | Spring Cloud Gateway (WebFlux) | rutas | 8080 host |
| `ms-java21-springboot-ordenes-v1` | Spring Boot 4.1 + Feign + AMQP | MongoDB | interno |
| `bff-auth-service` | Spring Boot 4.1 + JWT propio | Postgres `eventpass_auth` | interno |
| `eventos-service` | Spring Boot 4.1 + JPA | Postgres `eventpass_eventos` | interno |
| `ms-java21-quarkus-tickets-v1` | Quarkus 3.40 (JVM) + Reactive Messaging | MongoDB `eventpass_tickets` | interno |

Flujo principal: `POST /api/v1/ordenes` → reserva de aforo en eventos (Feign)
→ `orden.creada` (RabbitMQ) → tickets emite 1 ticket por unidad →
`tickets.emitidos` → orden `EMITIDA`.

## Requisitos

- Docker Desktop en ejecución
- Java 21 + Maven (solo si corres un servicio fuera de Docker)

## Puesta en marcha (2 comandos)

```bash
cp .env.example .env
docker compose up --build
```

Eso levanta las 4 BDs/colas + los 5 servicios. El API queda en
`http://localhost:8080`:

- `POST /api/auth/registro`, `POST /api/auth/login`
- `GET/POST /api/eventos`, `POST /api/eventos/{id}/reservar`
- `POST /api/v1/ordenes`, `GET /api/v1/ordenes/{id}`
- `GET /api/v1/tickets/{id}`, `GET /api/v1/tickets/por-orden/{ordenId}`

## Probar

La carpeta [`requests/`](requests/) tiene un `.http` por servicio más
[`e2e.http`](requests/e2e.http) con el flujo real completo (auth → evento →
orden multi-item → tickets → EMITIDA + casos 400/404/409). Ábrelos en VS Code
(extensión REST Client) y ejecútalos de arriba hacia abajo, o impórtalos a Postman.

## Tests y build por servicio

```bash
# Órdenes (requiere EVENTOS_SERVICE_URL + SPRING_MONGODB_URI)
cd backend/ms-java21-springboot-ordenes-v1
EVENTOS_SERVICE_URL=http://localhost:8082 SPRING_MONGODB_URI=mongodb://localhost:27017/eventpass_ordenes ./mvnw test

# Eventos / BFF (requieren su Postgres levantado: docker compose up -d postgres-eventos)
cd backend/eventos-service && ./mvnw test

# Gateway / Tickets (requieren sus URLs por env, ver .env.example)
cd backend/gateway-service && ./mvnw test
```

Toda la configuración va por variables de entorno (ver `.env.example`);
sin `.env` completo el compose falla en seco en vez de levantar a medias.

## Ramas

`main` (estable) ← `dev` ← `feature/<servicio>`. Commits descriptivos
por feature (`feat:`, `fix:`, `docs:`).
