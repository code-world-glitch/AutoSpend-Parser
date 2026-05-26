# AutoSpend Parser

### Automated Expense & Receipt Logistics Tracker

AutoSpend Parser automatically watches incoming expense files, parses data, persists information into H2 database storage and archives completed files.

Built to demonstrate production Java engineering practices.

![Java](https://img.shields.io/badge/Java-17-blue)
![Maven](https://img.shields.io/badge/Maven-Build-red)
![JUnit](https://img.shields.io/badge/JUnit5-Test-green)
![H2](https://img.shields.io/badge/H2-Database-orange)

---

## Architecture

```text

             +----------------+
             | input folder   |
             +--------+-------+
                      |
                      V

            +------------------+
            | FileParserService|
            +--------+---------+
                     |
                     V

            +-------------------+
            | ExpenseRepository |
            +--------+----------+
                     |
                     V

            +----------------+
            | H2 Database    |
            +----------------+
                     |
                     V

            +----------------+
            | archive folder |
            +----------------+

```

---

## Features

- File I/O Automation
- CSV Expense Parsing
- H2 Embedded Database
- Raw JDBC
- Exception handling
- Repository pattern
- Unit tests
- Folder archival workflow

---

## Setup

Clone repository:

```bash
git clone https://github.com/code-world-glitch/AutoSpend-Parser.git
```

Navigate:

```bash
cd AutoSpend-Parser
```

Build:

```bash
mvn clean install
```

Run:

```bash
mvn exec:java
```

---

## Verify

Drop CSV files into:

```text
input/
```

Run application.

Observe:

- records inserted into H2
- files moved to archive
- dashboard output in console

---

## Recruiter Highlights

This project demonstrates:

✓ SOLID architecture

✓ Automation workflows

✓ File parsing

✓ Database persistence

✓ JDBC

✓ Test-driven design

✓ Production coding standards

✓ Error handling and maintainability
