# Census Data API

This project provides an internal API for fetching and aggregating American Community Survey (ACS) data, with a focus on geographic search and visualization. It is designed to help users identify and analyze ACS statistics by Zip Code, County, City/Community name, and neighborhoods, as well as aggregate other statistics through geocoding.

## Features

- **ACS Data Fetching:** Internal endpoints to populate and retrieve ACS variables for various geographies.
- **TIGER/Line Integration:** Prototype endpoints fetch TIGER/Line census shapefiles and render GeoJSON Features for specified counties.
- **Livability Score:** Aggregates ACS statistics (population density, education, income, rent, housing, and more) to produce a composite livability score for neighborhoods and communities.
- **Neighborhoods Endpoint:** Exposes a `/neighborhoods` API used by a React frontend to render neighborhood shapes and display associated data.
- **Choropleth Mapping:** Supports choropleth visualization, coloring neighborhoods by score or category for easy comparison.

## Planned Integrations

- Additional ACS variables and geographies
- Enhanced geocoding and aggregation
- More robust scoring and ranking algorithms

## Usage

Currently, the API is intended for internal use and prototyping. The `/neighborhoods` endpoint returns GeoJSON features with associated ACS data and scores, suitable for use in mapping applications.

## Frontend Integration

A React app consumes the `/neighborhoods` endpoint to render interactive maps, using color to denote scores in various categories (e.g., livability, education, income).

## Getting Started

1. **Clone the repository**
2. **Build and run the service** (see `build.gradle.kts` and `docker-compose.yml` for setup)
3. **Access the API** (see internal documentation for available endpoints)

## Running with Spring Boot and Docker Compose

This project uses [Spring Boot](https://spring.io/projects/spring-boot) for the backend service and [Docker Compose](https://docs.docker.com/compose/) for local development and service orchestration.

### Docker Compose Services

The `docker-compose.yml` file defines the following services for development:

- **census-api**: The main Spring Boot application, exposing the API endpoints.
- **postgis-vector**: A PostGIS-enabled PostgreSQL database for spatial and census data storage.
- (Other services may be defined as needed for development or integration.)

### Spring Docker Compose Integration

You do **not** need to run `docker-compose up` manually. This project uses [Spring Boot Docker Compose integration](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.docker-compose) to automatically start and manage the required services defined in `docker-compose.yml` when you run the Spring Boot application. The dependent services (such as the database) will be started and stopped as needed.

### Spring Boot Application Configuration

The application is configured via the `src/main/resources/application.yml` file. This file contains settings for:

- Database connection (PostgreSQL/PostGIS)
- Server port and context path
- ACS and TIGER/Line data sources
- Other Spring and application-specific properties

You can override these settings with environment variables or by editing `application.yml` as needed for your environment.

### Running the Spring Boot Application

You can run the application in several ways:

- **With Spring Boot (recommended):**
  ```sh
  ./gradlew bootRun
  ```
  or
  ```sh
  ./gradlew build
  java -jar build/libs/census-<version>.jar
  ```
  Spring Boot will automatically start the required Docker Compose services.

- **Manually with Docker Compose** (not required, but possible):
  ```sh
  docker-compose up --build
  ```

