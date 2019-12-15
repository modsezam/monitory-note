# monitory-note
Application for managing people and vehicles entering the facility.

### technology stack
* backend: Java, Spring Boot, Spring Security
* database: MySQL, Spring Data
* frontend: Thymeleaf, Bootstrap

### Application description
Application for notifying people and vehicles entering the facility. Via the website it will be possible to submit notifications by authorized users. Reported notifications will be approved by the System Administrator.
Security will have access to approved notifications and will allow vehicles and people to enter the facility on their basis.

### Integrations
Service integration with LPR cameras (License Plate Recognition System). The entry camera, by processing the image, generates files that are saved on an FTP server. The application will download these files and parse the file name for the data saved to the database. Data such as camera name, license plate number, date and time will be saved.
Data read from LPR cameras are presented on the main page. clicking on the license plate will move to the company website where all vehicles and employees will be presented along with information about the date of notification. People without a valid notification will be highlighted in red.

### run application
The application is available at:
http://localhost:8080

### Contributor:
* https://github.com/SzymonKuhn

### Screen shots
screen shot 1
![project-readers 1](https://github.com/modsezam/monitory-note/blob/master/src/main/resources/images/monitory-note-ps-3.png)
screen shot 2
![project-readers 2](https://github.com/modsezam/monitory-note/blob/master/src/main/resources/images/monitory-note-ps-1.png)
screen shot 
![project-readers 2](https://github.com/modsezam/monitory-note/blob/master/src/main/resources/images/monitory-note-ps-2.png)
