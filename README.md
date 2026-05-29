# House of Lagman – Webapplikation

## Projektbeschreibung
House of Lagman ist eine Webapplikation für ein uigurisches Restaurant in Wohlen AG. Die Applikation ermöglicht Gästen, Tischreservationen online vorzunehmen, das Menü einzusehen und Kontaktinformationen abzurufen. Ein passwortgeschützter Admin-Bereich erlaubt die Verwaltung von Reservationen und Sonderöffnungszeiten.

## Zusatzthema
**Besondere UX und Umsetzung vom Design im UI** – Die Applikation wurde mit einem durchgängigen, professionellen Design umgesetzt. Besonderes Augenmerk wurde auf die Benutzerführung beim Reservationsprozess gelegt:
- Interaktives Zeitslot-Modal mit Echtzeit-Kapazitätsanzeige
- Schrittweise Formularführung (Datum & Zeit → Kontaktdaten)
- Automatische E-Mail-Bestätigung nach erfolgreicher Reservation
- Responsive Design mit Hamburger-Navigation für Mobile

Als weiteres Zusatzthema wurde **Authentifizierung & Autorisierung** mit Spring Security umgesetzt (rollenbasierte Sicherheit, BCrypt-Passwort-Hashing, geschützter Admin-Bereich).
## Technologie-Stack
- **Backend:** Java 21, Spring Boot 3.5
- **Templating:** Thymeleaf
- **Datenbank:** JPA / Spring Data, H2 (In-Memory)
- **Security:** Spring Security
- **Mail:** Spring Mail (Infomaniak SMTP)
- **Build:** Gradle
- **Weitere:** Lombok, Font Awesome

## Funktionen

### Öffentlicher Bereich
- **Startseite** – Hero-Section mit Überblick
- **Über uns** – Restaurantvorstellung
- **Menü** – Speisekarte mit Kategorien, Varianten (Vegetarisch, Poulet, Rind), Empfehlungen und PDF-Download
- **Kontakt** – Öffnungszeiten, Standort, Kontaktangaben, Sonderöffnungszeiten
- **Reservieren** – Online-Tischreservation mit Zeitslot-Auswahl, Kapazitätsprüfung und E-Mail-Bestätigung
- **Impressum & AGB**

### Admin-Bereich (`/admin`)
- Login mit Benutzername und Passwort
- Reservationsverwaltung (Heute / Kommende / Vergangene) mit Filter, Sortierung und Statusverwaltung (Ausstehend / Eingecheckt / Nicht erschienen)
- Sonderöffnungszeiten erfassen (mit Datumsbereich), anzeigen und löschen
- Automatische Löschung abgelaufener Sonderöffnungszeiten

## Starten der Applikation

```bash
./gradlew bootRun
```

Die Applikation ist danach unter `http://localhost:8080` erreichbar.

### Admin-Zugang
- URL: `http://localhost:8080/admin`
- Benutzername: `admin`
- Passwort: `houseoflagman2026`

## Tests ausführen

```bash
./gradlew test
```

- 6 Unit Tests (Service-Schicht)
- 2 Integrationstests (Controller + Repository)
- 2 E2E Tests (HtmlUnit)

## Projektstruktur
src/<br>
├── main/<br>
│   ├── java/houseoflagman/<br>
│   │   ├── controller/       # Spring MVC Controller<br>
│   │   ├── model/            # JPA Entitäten<br>
│   │   ├── repository/       # Spring Data Repositories<br>
│   │   ├── service/          # Business Logic<br>
│   │   ├── DataInitializer   # Testdaten<br>
│   │   └── SecurityConfig    # Spring Security Konfiguration<br>
│   └── resources/<br>
│       ├── templates/        # Thymeleaf Templates<br>
│       └── static/           # CSS, JS, Bilder<br>
└── test/<br>
└── java/houseoflagman/<br>
├── controller/       # Integrationstests<br>
├── e2e/              # E2E Tests<br>
├── repository/       # Integrationstests<br>
└── service/

## Entitäten
- **Reservation** – Tischreservation mit Name, E-Mail, Telefon, Datum, Zeit, Personen, Status
- **MenuItem** – Menüpunkt mit Name, Beschreibung, Preis, Kategorie, Varianten
- **MenuCategory** – Menükategorie (1:n Beziehung zu MenuItem)
- **SpecialHours** – Sonderöffnungszeiten mit Datumsbereich

## Autor
Entwickelt von nisoya99 (Künstlername von Ayse Soy) für House of Lagman