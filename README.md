# 🏥 Injury Assist System

> **A streamlined clinic management platform for tracking sports injuries, booking appointments, and generating diagnostic reports.**

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://java.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-Template_Engine-005f0f.svg)](https://www.thymeleaf.org/)
[![Tailwind CSS](https://img.shields.io/badge/Tailwind-CSS-38B2AC.svg)](https://tailwindcss.com)

Injury Assist is a full-stack Spring Boot web application designed by a team of 2nd-year CS students. It allows patients to register, report injuries, get immediate initial assessments, and book appointments with specialized doctors.

---

## 🏗️ Architecture & UML

The system follows a strict MVC pattern using Spring Boot, Spring Data JPA, and Thymeleaf. 

![System Architecture](https://placehold.co/800x400/f8f9fa/333333?text=PlantUML+Diagram+Placeholder)
*(See [`docs/arch.puml`](docs/arch.puml) for the complete PlantUML class diagram)*

### Core Domain Entities:
* **Patient:** Core user entity with authentication capabilities.
* **Doctor / GeneralDoctor / SpecializedDoctor:** Providers who offer advice and perform assessments.
* **Injury:** Records the body part affected and criticality.
* **Appointment:** Books a time slot linking a Patient to a Doctor.
* **Report:** Generated post-appointment containing diagnosis and treatment plans.

---

## 🚀 How to Run Locally

This project is designed to be completely self-contained. You do not need to install an external database or frontend build tools.

### Prerequisites
* Java 17+ installed on your machine.

### Quick Start
1. **Clone the repository:**
   ```bash
   git clone <your-repo-url>
   cd InjurySystem
   ```

2. **Run the application:**
   You can use the included Maven wrapper. No need to install Maven globally!
   
   *On Windows:*
   ```cmd
   mvnw.cmd spring-boot:run
   ```
   *On Mac/Linux:*
   ```bash
   ./mvnw spring-boot:run
   ```

3. **Access the application:**
   Open your browser and navigate to:
   👉 **`http://localhost:8080`**

### Database Access (H2 Console)
The application uses an in-memory H2 database for development. Data is reset every time the application restarts.
* **URL:** `http://localhost:8080/h2-console`
* **JDBC URL:** `jdbc:h2:mem:injurydb`
* **Username:** `sa`
* **Password:** *(leave blank)*

---

## 🛠️ Tech Stack Explained

* **Backend:** Spring Boot (Web, Data JPA, Security)
* **Database:** H2 In-Memory Database
* **Frontend Rendering:** Thymeleaf
* **CSS Framework:** Tailwind CSS (via CDN for zero-build configuration)
* **Interactivity:** Alpine.js (via CDN for lightweight DOM manipulation)

---

## 🔐 Security and Email Configuration

Passwords are stored with **BCrypt hashing** (not plain text). Login supports migrated legacy accounts by re-hashing old plain passwords after a successful login.

Set these in `src/main/resources/application.properties` (or environment variables) for SMTP:

* `spring.mail.host`
* `spring.mail.port`
* `spring.mail.username`
* `spring.mail.password`
* `app.mail.sender`
* `app.mail.receiver`

On each injury assessment submission, the app sends a notification email to `app.mail.receiver`.

---

## 🍪 How to Test Session Cookies

1. Open browser DevTools → **Application/Storage** → **Cookies**.
2. Login from `/login`; confirm a `JSESSIONID` cookie appears.
3. Check attributes:
   * `HttpOnly` should be enabled.
   * `SameSite` should be `Lax`.
4. Use app pages requiring login (e.g., `/dashboard`) and confirm access works while cookie exists.
5. Logout from `/logout` and verify protected pages redirect to `/login` and session changes.

---

## 📸 Screenshots

*(Replace these with actual project screenshots before final submission)*

| Login / Signup | Patient Dashboard | Injury Assessment | Appointment Booking |
| :---: | :---: | :---: | :---: |
| <img src="https://placehold.co/300x200?text=Login+Page" width="300" alt="Login"/> | <img src="https://placehold.co/300x200?text=Dashboard" width="300" alt="Dashboard"/> | <img src="https://placehold.co/300x200?text=Injury+Form" width="300" alt="Injury Form"/> | <img src="https://placehold.co/300x200?text=Booking+Slots" width="300" alt="Booking"/> |

---

## 👥 Development Team

* **[Name 1]** - Security & Setup Lead
* **[Name 2]** - Domain & Doctor Logic Lead
* **[Name 3]** - Appointment & Booking Flow Lead
* **[Name 4]** - Reports, Injuries & Integration Lead

---
*Developed for a 2-week agile sprint by 2nd-year CS students.*
