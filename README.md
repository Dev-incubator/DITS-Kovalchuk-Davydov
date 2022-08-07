# dits_for_dev_inc
DITS is a dev incbator testing system. It is a system for testing student , in this application we habe 2 roles: user and admin.
User can pass tests and see its statistics.
Admin can CRUD users, tests and see statistics by topics, tests, questions , answers and users.
For authorizing and authentication it uses Spring Security. 

Getting Started
=====================
##### 1. git clone https://github.com/DevIncubator/DevIncubatorTestingSystem
##### 2. start your application from your Idea
        ## Before start install Java https://www.java.com/
        ## Allow all installations suggested by IDE
        ## After indexing all your files accept all plugins, which IDE propose to install
        ## Leave default settings
        ## Plugins needed: spring, maven, jenkins and thymeleaf
        ## Add build config for DitsApplication
        ## Restart your application to apply your changes in application
##### 3. open http://localhost:8080/

* Credentials for user : user - user.
* Credentials for admin : admin - admin

Using technologies
=====================
##### 1. Java 11
##### 2. PostgreSQL
##### 3. Thymeleaf
##### 4. Spring
##### 5. Hibernate
##### 6. H2-database for testing
##### 7. Bootstrap

Files path
=====================
This path - templates - uses by default in Spring boot application (It is classpath for spring boot)
1. src/main/resources/static - you can find static resources such as : images , css , js 
2. src/main/resources/templates - you can find html pages 
3. src/main/resources - you can find application configurations
4. src/main/java - it is a path for main logic of your application
5. src/main/test - it is a path, where maven will start unit testing


TODO
=====================
1. Create a restriction for user to check out any page when any test is in progress;
2. Create a button to abort current test;
3. Create a timer that will abort test that is in progress if user goes out of the time.
4. If connection is lost, show ajax alerts or warnings (frontend) without redirect.
5. Creating user with existing name must throw an exception (frontend), also warning.
6. Project size is over 100Mb, remove unused packages or libs.
