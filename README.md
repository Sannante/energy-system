# 🔋 Distributed Energy Management System

Dieses Projekt ist ein verteiltes System zur Verwaltung von Energieproduktion und -verbrauch innerhalb einer Community. Es umfasst verschiedene Komponenten, darunter REST-API, Producer/Consumer, eine PostgreSQL-Datenbank und RabbitMQ zur Kommunikation.

---

## 📦 Projektstruktur

energy-system/
├── energyrestapi/ # Spring Boot REST API (JPA + Flyway)
├── energyproducer/ # Simuliert Stromproduktion (RabbitMQ Producer)
├── energyuser/ # Simuliert Stromverbrauch (RabbitMQ Producer)
├── usageservice/ # Aggregiert PRODUCER/USER-Daten aus RabbitMQ
├── energyfxgui/ # JavaFX GUI für Datenanzeige
├── infrastructure/ # Docker Setup (PostgreSQL + RabbitMQ)
└── docker-compose.yml # Docker Setup für Datenbank & Message Queue

yaml
Kopieren
Bearbeiten

---

## 🚀 Voraussetzungen

- [Docker](https://www.docker.com/) installiert und lauffähig
- [Java 21](https://adoptium.net/) (z. B. über IntelliJ)
- Maven-Unterstützung (wird via `mvnw` bereitgestellt)
- IntelliJ IDEA empfohlen (kein Muss)

---

## 🔧 Projekt starten

### 1. Repository klonen

git clone https://github.com/dein-user/energy-system.git
cd energy-system

2. Docker-Container starten
Kopieren
Bearbeiten
docker compose up -d
Das startet:

PostgreSQL unter localhost:5433

RabbitMQ unter localhost:5672 und Webinterface unter http://localhost:15672
(Login: guest, Passwort: guest)

🧠 Anwendung starten
Reihenfolge:
energyrestapi → Startet das REST API + Flyway-Migrationen (legt Tabellen automatisch an)

usageservice → Aggregiert PRODUCER/USER-Daten stündlich in die Datenbank

energyproducer → Sendet PRODUCER-Daten an RabbitMQ

energyuser → Sendet USER-Daten an RabbitMQ

(Optional) energyfxgui → Visualisiert aktuelle und historische Energiedaten

Alle Module sind eigenständig lauffähig und kommunizieren über RabbitMQ und PostgreSQL.

🗃️ Datenbank
Name: energydb

User: disysuser

Passwort: disyspw

Wird bei erstem Start automatisch über Flyway konfiguriert.

🐰 RabbitMQ
Port: 5672 (AMQP)

Web UI: http://localhost:15672

Benutzer: guest

Passwort: guest

🛠️ Technologien
Java 21
Spring Boot 3.4
JavaFX 23
PostgreSQL (Docker)
RabbitMQ (Docker)
Flyway (Datenbankmigration)
Maven
