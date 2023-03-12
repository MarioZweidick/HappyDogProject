# HappyDog

### A Website for dog owners and trainers to find high quality dog trainings.

### Description
> HappyDog is a WebProject for the subject Requirments Engineering for the University of Applied Sciences "Campus02". 

### Technicals
- **Backend:**<br>
-- Java Spring<br>
-- Spring Security<br>
-- MVC<br>
-- Spring Data JPA<br>
-- PostgreSQL Database<br>

- **Frontend:**<br>
-- HTML<br>
-- CSS<br>
-- Jquery<br>
-- Thymleaf (Template Engine)<br>


### Folder Structure
```
📦src
 ┣ 📂main
 ┃ ┣ 📂java
 ┃ ┃ ┗ 📂at
 ┃ ┃ ┃ ┗ 📂happydog
 ┃ ┃ ┃ ┃ ┗ 📂test
 ┃ ┃ ┃ ┃ ┃ ┣ 📂controller ----------------- Controllers
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Controller.java            
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ProfileController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂email ---------------------- Email
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜EmailConstant.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜EmailSender.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜EmailService.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂enity ---------------------- Entitys
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AppUser.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AppUserRoles.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Location.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜Training.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂registrationUtil ----------- Registration and Token Entity + Validator
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂token                
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ConfirmationToken.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ConfirmationTokenRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ConfirmationTokenService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂validator
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜EmailValidator.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PasswordValidator.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UsernameValidator.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserRegistrationRequest.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂repository ----------------------------- Repositories for DB queries
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜AppUserRepository.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂security ------------------------------- Security + SecurityConfig
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WebSecurityConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜PasswordEncoder.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂service -------------------------------- Services
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AppUserService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserRegistrationService.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜TestApplication.java
 ┃ ┗ 📂resources -------------------------------------- Resources
 ┃ ┃ ┣ 📂static
 ┃ ┃ ┣ 📂templates ------------------------------------ Templates - HTML Files
 ┃ ┃ ┃ ┣ 📜email-confirmation.html
 ┃ ┃ ┃ ┣ 📜index.html
 ┃ ┃ ┃ ┣ 📜login.html
 ┃ ┃ ┃ ┣ 📜logout.html
 ┃ ┃ ┃ ┣ 📜profile.html
 ┃ ┃ ┃ ┗ 📜registration.html
 ┃ ┃ ┗ 📜application.properties
 ┗ 📂test --------------------------------------------- Tests
 ┃ ┗ 📂java
 ┃ ┃ ┗ 📂at
 ┃ ┃ ┃ ┗ 📂happydog
 ┃ ┃ ┃ ┃ ┗ 📂test
 ┃ ┃ ┃ ┃ ┃ ┗ 📜TestApplicationTests.java
 
