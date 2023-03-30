# HappyDog

### A Website for dog owners and trainers to find high quality dog trainings.

## Description
> HappyDog is a WebProject for the subject Requirments Engineering for the University of Applied Sciences "Campus02". 

## Technicals
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

## Setting up development enviroment
> <a href="https://github.com/horoscloud/HappyDogProject/wiki/Setup-development">Setup development enviroment</a>

## Application endpoints
> <a href="https://github.com/horoscloud/HappyDogProject/wiki/Application-Endpoints">Application endpoints</a>


## Folder Structure (30.03.)
```
main                                                   
├─ java                                                
│  └─ at                                               
│     └─ happydog                                      
│        └─ test                                       
│           ├─ api                                     
│           │  └─ google                               
│           │     └─ geocoding                         
│           │        ├─ ApiConstant.java               
│           │        ├─ Geocoding.java                 
│           │        └─ GeocodingInterface.java        
│           ├─ configuration                           
│           │  ├─ PreloadDatabase.java                 
│           │  └─ SetupEmailConfirmation.java          
│           ├─ controller                              
│           │  ├─ FrontController.java                 
│           │  ├─ ProfileController.java               
│           │  ├─ TrainingController.java              
│           │  ├─ UserController.java                  
│           │  └─ UserRESTController.java              
│           ├─ email                                   
│           │  ├─ EmailConstant.java                   
│           │  ├─ EmailSender.java                     
│           │  └─ EmailService.java                    
│           ├─ enity                                   
│           │  ├─ AppUser.java                         
│           │  ├─ AppUserImage.java                    
│           │  ├─ AppUserRoles.java                    
│           │  ├─ Location.java                        
│           │  └─ Training.java                        
│           ├─ imageUtil                               
│           │  └─ ImageUtil.java                       
│           ├─ jsonUtil                                
│           │  └─ Parser.java                          
│           ├─ registrationUtil                        
│           │  ├─ token                                
│           │  │  ├─ ConfirmationToken.java            
│           │  │  ├─ ConfirmationTokenRepository.java  
│           │  │  └─ ConfirmationTokenService.java     
│           │  ├─ validator                            
│           │  │  ├─ EmailValidator.java               
│           │  │  ├─ PasswordValidator.java            
│           │  │  └─ UsernameValidator.java            
│           │  └─ UserRegistrationRequest.java         
│           ├─ repository                              
│           │  ├─ AppUserImageRepository.java          
│           │  ├─ AppUserRepository.java               
│           │  └─ TrainingRepository.java              
│           ├─ security                                
│           │  ├─ config                               
│           │  │  └─ WebSecurityConfig.java            
│           │  └─ PasswordEncoder.java                 
│           ├─ service                                 
│           │  ├─ AppUserService.java                  
│           │  ├─ TrainingService.java                 
│           │  └─ UserRegistrationService.java         
│           └─ TestApplication.java                    
└─ resources                                           
   ├─ static                                           
   │  └─ style                                         
   │     ├─ entry                                      
   │     │  ├─ email-confirmation.css                  
   │     │  ├─ login.css                               
   │     │  ├─ logout.css                              
   │     │  └─ registration.css                        
   │     ├─ profiles                                   
   │     │  ├─ owner.css                               
   │     │  ├─ profile.css                             
   │     │  └─ trainer.css                             
   │     ├─ main.css                                   
   │     ├─ reset-config.css                           
   │     └─ training.css                               
   ├─ templates                                        
   │  ├─ tentry                                        
   │  │  ├─ email-confirmation.html                    
   │  │  ├─ login.html                                 
   │  │  ├─ logout.html                                
   │  │  └─ registration.html                          
   │  ├─ terror                                        
   │  ├─ tprofile                                      
   │  │  ├─ owner.html                                 
   │  │  └─ trainer.html                               
   │  ├─ index.html                                    
   │  └─ training.html                                 
   └─ application.properties                           
