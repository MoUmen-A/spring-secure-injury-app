# Team Onboarding Guide

This project is intentionally scaffolded. The file structure is ready, but business logic is not implemented yet.

## Folder Structure

```text
Clinic/
|- docs/
|  |- team-onboarding-guide.md
|  \- uml/
|     \- injury-assist-system-architecture.puml
|- src/
|  |- main/
|  |  |- java/dev/mr3/sb/
|  |  |  |- SbApplication.java
|  |  |  |- controller/
|  |  |  |- service/
|  |  |  |- repository/
|  |  |  \- model/
|  |  \- resources/
|  |     |- templates/
|  |     \- static/
|  \- test/
|     \- java/dev/mr3/sb/
|- pom.xml
|- mvnw
\- mvnw.cmd
```

## Ownership Boundaries (Avoid Overlap)

- controller/: only routing and HTTP input/output mapping.
- service/: only business rules and use-case orchestration.
- repository/: only database access contracts and query methods.
- model/: only domain entities, enums, and relations.
- resources/templates/: only page markup and UI rendering.

When a file touches more than one boundary, split the work before merging.

## Naming Conventions

- Class naming: PascalCase.
- Method and field naming: camelCase.
- Package naming: lowercase only.
- Service names: *Feature*Service (example: InjuryService).

- Model names: singular nouns (example: Appointment, Report).
- Enum names: singular noun, UPPER_SNAKE_CASE values (example: BodyPart, KNEE).
- HTML template files: kebab-case for new files (example: report-view.html).

## Branch And Commit Rules (Conflict Prevention)

- One feature per branch.
- One branch owner.
- One pull request per feature slice.
- Keep pull requests small (recommended: 5-15 files).
- Rebase or merge from main daily before adding new commits.
- Never mix refactor + feature + formatting in one pull request.

Commit style:

- feat: for new behavior.
- fix: for bug fixes.
- refactor: for structural changes without behavior changes.
- docs: for guide and documentation updates.
- test: for tests only.

Example commit messages:

- feat: add login request mapping in auth controller
- refactor: move validation logic from controller to service
- docs: update folder ownership rules

## Pull Request Checklist

- I only changed files in my assigned layer.
- I used agreed naming conventions.
- I did not rename unrelated files.
- I updated docs when adding or renaming folders/files.
- I resolved conflicts locally before requesting review.

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

## Suggested Team Working Agreement

- Pick one feature vertical each sprint (Auth, Injury, Appointment, Report).
- Each vertical owner edits only its controller, service, repository, model, and templates.
- Cross-cutting changes (shared model fields, package moves) require a short design note in docs first.
- If two people must touch the same file, agree on method-level ownership before coding.
