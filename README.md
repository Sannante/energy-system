# 🔋 Distributed Energy Management System

Dieses Projekt ist ein verteiltes System zur Verwaltung von Energieproduktion und -verbrauch innerhalb einer Community. Es umfasst verschiedene Komponenten, darunter REST-API, Producer/Consumer, eine PostgreSQL-Datenbank und RabbitMQ zur Kommunikation.

---

## 🧱 Projektstruktur

```text
energy-system/
├── energyrestapi/             # Spring Boot REST API (JPA + Flyway)
├── energyproducer/            # Simuliert Stromproduktion (RabbitMQ Producer)
├── energyuser/                # Simuliert Stromverbrauch (RabbitMQ Producer)
├── usageservice/              # Aggregiert PRODUCER/USER-Daten aus RabbitMQ
├── currentpercentageservice/  # Berechnet Prozentwerte aus usage_table
├── energyfxgui/               # JavaFX GUI für Datenanzeige
├── infrastructure/            # Docker Setup (PostgreSQL + RabbitMQ)
└── docker-compose.yml         # Docker Setup für Datenbank & Message Queue
```

---

## 🚀 Voraussetzungen

- [Docker Desktop](https://www.docker.com/) installiert
- Java 21 (z. B. via IntelliJ)
- Maven (`mvn`) oder Wrapper (`./mvnw`) installiert
- IDE wie IntelliJ empfohlen

---

## :test_tube: Erste Schritte

### 1. Repository klonen

git clone https://github.com/Sannante/energy-system.git

CMD Befehl cd IdeaProjects/energy-system 

### 2. Docker-Infrastruktur starten
Docker Desktop starten
CMD cd IdeaProjects/energy-system/infrastructure
docker compose up -d

Wenn die Infrastruktur bereits ausgeführt wurde, dann in Docket Desktop bei infrastructure auf "Start" drücken.
🔧 Das startet:

PostgreSQL auf localhost:5433

RabbitMQ auf localhost:5672

RabbitMQ Web UI: http://localhost:15672
Login: guest, Passwort: guest

### 3. Datenbank vorbereiten (Flyway Migration)

Optional (Befehl um auf die Datenbank zuzugreifen):
CMD cd IdeaProjects/energy-system/infrastructure
docker exec -it infrastructure-database-1 psql -U disysuser -d energydb

Überprüfung, ob Datenbank angelegt:
\d (Wenn "Did not find any relations", dann keine Datenbankeinträge verfügbar = Gutes Zeichen)

Wenn das Projekt ohne Intellij gestartet wird:
cd energyrestapi
mvn spring-boot:run

✅ Dies legt automatisch die Tabellen usage_table und percentage_table an.

👉 Danach kann das REST API gestoppt werden – die Tabellen bleiben bestehen.

---

▶️ Startreihenfolge
Jedes Modul kann unabhängig gestartet werden.

energyproducer – sendet PRODUCER-Nachrichten an RabbitMQ

energyuser – sendet USER-Nachrichten an RabbitMQ

usageservice – aggregiert PRODUCER/USER stündlich in die DB

currentpercentageservice – berechnet community_depleted und grid_portion

energyrestapi – REST API für GUI (liefert Daten aus DB)

energyfxgui – Desktop-Anwendung zur Anzeige und Analyse

---

🔌 REST API Endpunkte
| Endpoint                                   | Beschreibung                                   |
| ------------------------------------------ | ---------------------------------------------- |
| `GET /energy/current`                      | Liefert aktuelle Prozentwerte (Community/Grid) |
| `GET /energy/historical?start=...&end=...` | Liefert stündliche Werte für Zeitraum          |

---

🗃️ Datenbankdetails
| Konfiguration | Wert        |
| ------------- | ----------- |
| DB-Name       | `energydb`  |
| Benutzer      | `disysuser` |
| Passwort      | `disyspw`   |
| Port          | `5433`      |

Wird durch Flyway automatisch konfiguriert.

---

🐰 RabbitMQ
| Einstellung   | Wert                                             |
| ------------- | ------------------------------------------------ |
| AMQP-Port     | `5672`                                           |
| Web UI        | [http://localhost:15672](http://localhost:15672) |
| Benutzer / PW | `guest` / `guest` 

---

💡 Beispielablauf
1. Producer sendet z. B. {"type": "PRODUCER", "kwh": 0.03, ...}

2. User sendet z. B. {"type": "USER", "kwh": 0.05, ...}

3. UsageService summiert diese pro Stunde und schreibt in usage_table

4. CurrentPercentageService verarbeitet die Aggregation und speichert Werte in percentage_table

5. REST API stellt Endpunkte bereit

6. JavaFX GUI ruft Endpunkte auf und zeigt Werte für Analysezwecke an

---

⚙️ Technologien
- Java 21

- Spring Boot 3.x

- JavaFX 23

- RabbitMQ (Docker)

- PostgreSQL (Docker)

- Flyway (Migrationen)

- JPA (Datenzugriff)

- Jackson (JSON)

- Maven

---

🧑‍💻 Autoren
- Massimo Linke – FH Technikum Wien
- Michael Goeltinger - FH Technikum Wien
- Ahmed Abu El Ella - FH Technikum Wien


2025 – Distributed Systems 

---

 
