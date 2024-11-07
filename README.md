# GraphQL Demo Using Netflix DGS Framework

## Project Overview
This demo project demonstrates how to build a GraphQL server using the **DGS framework by Netflix**. It provides an example of setting up and querying a GraphQL API integrated with a relational database.

## Technology Stack
The project uses the following technologies:

- **Java**: The main programming language for the application.
- **Maven**: For dependency management and build automation.
- **GraphQL**: A query language for APIs to enable flexible and efficient data fetching.
- **DGS Framework (Netflix)**: A framework that simplifies the creation of GraphQL servers in Java applications.
- **Hibernate**: An ORM (Object-Relational Mapping) library for database interactions.
- **PostgreSQL**: A relational database for data storage.
- **Flyway**: A tool for database schema version control and migrations.

## Features
- A straightforward setup of a GraphQL server.
- Data persistence managed with **Hibernate**.
- Database schema versioning and migration using **Flyway**.
- **Maven** as the build and project management tool.


How to start?

1. **Clone the repository (if you haven't already):**
   ```bash
   git clone <repository-url>
   cd <project-directory>
   ````
2. Build the project

 ```bash
   mvn clean install
````

3. Build and start the Docker container

```bash
   docker compose up --build
````