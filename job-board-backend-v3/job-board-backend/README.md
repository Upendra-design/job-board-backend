# Lantern Job Board — Backend API

A layered Spring Boot 3 REST API for a job board: **User, Company, Job, Application, and SavedJob** entities with proper relationships, search/filter/pagination, validation, and centralized exception handling. Runs on **H2** by default (zero setup), with a ready **MySQL** profile for production.

**Scope note:** this is the backend core only. **JWT authentication is intentionally not implemented** — the `User` entity, `Role` enum, and a plain `/api/users/register` endpoint are scaffolded so you can add Spring Security + JWT yourself. See "Adding JWT auth" below for exactly where that plugs in.

**Note on this documentation and code:** generated with AI assistance (Claude), describing the API as actually implemented. Because this was built in a sandboxed environment without internet access to Maven Central, **the code could not be compiled here** — it follows standard Spring Boot 3.3 / Jakarta EE conventions carefully, but run `mvn clean verify` locally (or let GitHub Actions run it with real internet access) as your first real compile check, and review the code yourself before relying on it for an assessment.

---

## Tech stack

Java 17+ (written against 21, drop to 17 in `pom.xml` if required) · Spring Boot 3.3 (Web, Data JPA, Validation) · H2 (default) / MySQL (`mysql` profile) · Maven · layered architecture (Controller → Service → Repository → Model)

---

## Entity relationships

```
User (JOB_SEEKER | EMPLOYER | ADMIN)
 ├── owns many → Company        (employer owns company profiles)
 ├── posts many → Job           (employer posts jobs, via postedBy)
 ├── submits many → Application (job seeker applies to jobs)
 └── bookmarks many → SavedJob  (job seeker saves jobs)

Company
 └── has many → Job

Job
 ├── belongs to → Company
 ├── posted by → User (employer)
 ├── has many → Application (applicants)
 └── has many → SavedJob (bookmarks)

Application: unique per (job, applicant) — a user can't apply twice to the same job
SavedJob: unique per (user, job) — a user can't save the same job twice
```

---

## Package structure

```
src/main/java/com/jobboard/api/
├── JobBoardApiApplication.java
├── model/         User, Company, Job, Application, SavedJob, Role, JobType, ExperienceLevel, ApplicationStatus
├── repository/    JPA repositories, incl. a filtering + paginated Job search query
├── service/        Business logic — one service per entity
├── controller/      REST controllers
├── dto/             Request/response DTOs per entity, decoupled from JPA entities (no password leaks, no lazy-loading surprises)
├── config/           CorsConfig, DataLoader (seeds 4 users, 2 companies, 4 jobs on first run)
└── exception/         ResourceNotFoundException, DuplicateResourceException, and a global JSON error handler
```

---

## API reference

### Users
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/users/register` | Create a user account. **Does not issue a token** — see "Adding JWT auth" |
| `GET` | `/api/users/{id}` | Get a user's public profile |

### Companies
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/companies` | List all companies |
| `GET` | `/api/companies/{id}` | Get one company |
| `POST` | `/api/companies` | Create a company (employer) |
| `PUT` | `/api/companies/{id}` | Update a company |
| `DELETE` | `/api/companies/{id}` | Delete a company |

### Jobs
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/jobs` | Search/filter/paginate jobs (see below) |
| `GET` | `/api/jobs/{id}` | Get one job's full detail |
| `POST` | `/api/jobs` | Post a job (employer) |
| `PUT` | `/api/jobs/{id}` | Edit a job |
| `DELETE` | `/api/jobs/{id}` | Delete a job |

**Query params for `GET /api/jobs`** (all optional, combine with AND logic):
`search` (title/company/tag), `location`, `minSalary`, `experienceLevel` (`ENTRY`/`MID`/`SENIOR`/`LEAD`), `jobType` (`FULL_TIME`/`PART_TIME`/`CONTRACT`/`INTERNSHIP`), `category`, `page`, `size`, `sortBy` (default `createdAt`), `sortDir` (`asc`/`desc`).

Example:
```
GET /api/jobs?search=react&location=Hyderabad&jobType=FULL_TIME&page=0&size=10&sortBy=createdAt&sortDir=desc
```
Response is a Spring `Page<JobResponse>` — includes `content`, `totalElements`, `totalPages`, `number` (current page), etc., which is exactly what a frontend pagination component needs.

### Applications
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/jobs/{jobId}/apply` | Job seeker applies to a job |
| `GET` | `/api/jobs/{jobId}/applicants` | Employer views everyone who applied |
| `GET` | `/api/users/{userId}/applications` | Job seeker views jobs they've applied to |
| `PATCH` | `/api/applications/{applicationId}/status` | Employer updates an applicant's status (`APPLIED`/`REVIEWED`/`ACCEPTED`/`REJECTED`) |

Applying twice to the same job returns `409 Conflict`.

### Saved jobs
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/users/{userId}/saved-jobs/{jobId}` | Save a job |
| `DELETE` | `/api/users/{userId}/saved-jobs/{jobId}` | Remove a saved job |
| `GET` | `/api/users/{userId}/saved-jobs` | List a user's saved jobs |

### Health
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/health` | Health check — used by Render and to wake a sleeping free-tier instance |

---

## Error format

All errors return consistent JSON via a global `@RestControllerAdvice`:
```json
{
  "timestamp": "2026-07-11T10:00:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "No job found with id 999"
}
```
Validation failures (`400`) additionally include a `fieldErrors` map, e.g. `{"email": "Email must be valid"}`.

---

## Running locally

```bash
mvn spring-boot:run
```
Starts on `http://localhost:8080`, auto-seeded with 4 users (2 employers, 1 job seeker, 1 admin), 2 companies, and 4 jobs — see `config/DataLoader.java` for exact seed values, including seed login emails if you want to test against them once auth exists. H2 console: `http://localhost:8080/h2-console`, JDBC URL `jdbc:h2:mem:jobboard`.

**Against real MySQL:**
```bash
export SPRING_PROFILES_ACTIVE=mysql
export DB_HOST=localhost DB_PORT=3306 DB_NAME=jobboard DB_USERNAME=root DB_PASSWORD=yourpassword
mvn spring-boot:run
```

## Running tests
```bash
mvn clean verify
```

---

## Adding JWT auth (your next step)

This scaffold deliberately stops short of auth so you can build and understand it yourself. Suggested path:

1. Add `spring-boot-starter-security` and a JWT library (e.g. `io.jsonwebtoken:jjwt`) to `pom.xml`.
2. In `UserService.register()`, replace the `TODO` with `passwordEncoder.encode(request.getPassword())` using a `BCryptPasswordEncoder` bean.
3. Add a `POST /api/auth/login` endpoint: verify email/password against `UserRepository`, issue a signed JWT containing the user's `id` and `role`.
4. Add a `SecurityConfig` (`@EnableWebSecurity`) with a JWT filter that reads the token, sets the `Authentication` in the `SecurityContext`, and protects routes by role (e.g. only `EMPLOYER` can `POST /api/jobs`).
5. Once that's in place, remove `ownerId`/`postedById`/`applicantId` from the request DTOs (`CompanyRequest`, `JobRequest`, `ApplicationRequest`) and instead pull the current user from the authenticated principal — that's the one intentional shortcut in this scaffold, since there's no security context yet to pull it from.

---

## Deployment (Render)

1. Push to GitHub.
2. Render → **New → Web Service** → connect the repo. It auto-detects `Dockerfile` and `render.yaml`.
3. Set `APP_CORS_ALLOWED_ORIGINS` to your deployed Vercel URL.
4. First load may take 10-30s to wake up on Render's free tier — hit `/api/health` to warm it before a demo.
5. For MySQL in production: set `SPRING_PROFILES_ACTIVE=mysql` plus `DB_HOST`/`DB_PORT`/`DB_NAME`/`DB_USERNAME`/`DB_PASSWORD` (Render's free managed DB is Postgres-only — connect an external MySQL like Railway's MySQL plugin or Aiven's free tier).

### CI/CD
`.github/workflows/ci-cd.yml` runs `mvn clean verify` on every push/PR, then triggers a Render deploy **only if tests pass**, via a Render Deploy Hook (not Render's auto-deploy-on-push, so a broken build can't ship). To enable: Render → your service → Settings → Deploy Hook → copy URL → add as GitHub secret `RENDER_DEPLOY_HOOK_URL`. Recommended: turn off Render's own Auto-Deploy so only this gated pipeline can deploy.

## Connecting a frontend
Point it at this API's base URL (`VITE_API_URL` env var works well with the Vite scaffold from earlier in this project) and call the endpoints above with `fetch` or `axios`.
