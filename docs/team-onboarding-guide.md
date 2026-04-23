# Team Onboarding Guide

This project is intentionally scaffolded. The file structure is ready, but business logic is not implemented yet.

## Current Architecture

- Controllers: request entry points only
- Services: business layer placeholders
- Repositories: data layer contracts
- Models: domain placeholders
- Templates: view placeholders

## What To Implement First

1. Model Layer
- Define required fields for each model.
- Add JPA annotations (@Entity, @Id, relations).
- Finalize enums like `Weekday` and `BodyPart`.

2. Repository Layer
- Extend Spring Data interfaces (for example, `JpaRepository`).
- Add finder methods needed by use cases.

3. Service Layer
- Implement business rules inside services.
- Keep controllers thin and move logic into services.
- Add validation and error handling paths.

4. Controller Layer
- Add route mappings and request/response models.
- Connect each controller method to its service method.
- Return correct templates or API responses.

5. Template Layer
- Build forms and result pages gradually.
- Match each template to its controller route.

## Suggested Work Split

- Developer A: Auth flow (`AuthController`, `AuthService`, `PatientRepository`, `Patient`)
- Developer B: Injury flow (`InjuryController`, `InjuryService`, `InjuryRepository`, `Injury`)
- Developer C: Appointment flow (`AppointmentController`, `AppointmentService`, `AppointmentRepository`, `Appointment`)
- Developer D: Report flow (`ReportController`, `ReportService`, `ReportRepository`, `Report`)

## Definition Of Done (Per Feature)

- Controller endpoint works end-to-end.
- Service logic is implemented and tested.
- Repository queries return expected data.
- Model relations are valid and mapped.
- Template renders expected output.

## Quick Start Commands

```powershell
# run app
.\mvnw.cmd spring-boot:run

# run tests
.\mvnw.cmd test
```

Note: your machine must use JDK 21 because `pom.xml` is configured with `<java.version>21</java.version>`.
