# Getting IntelliJ IDEA up & running

## Make sure Lombok is activaded
Go to _Settings > Build > Compiler > Annotation Processors_ and check _Enable annotation processing_

## Make sure Gradle uses the correct JDK
If you get warnings like 
_Could not target platform: 'Java SE 11' using tool chain: 'JDK 10 (1.10)'_,
 * Go to _Settings > Build Tools > Gradle_ and set the Gradle JVM to _Use Project JDK_
 * Go to _Project Settings > Project_ and set the Projdect JDK to _JDK 13_ (or something really recent)
 
 ## Set up a database view/connection of the IDE
 * Open the file `src/main/resources/application.properties`
 * Copy the value of `spring.datasource.url`
 * Go to _Database (right margin) > + (New) > Datasource from URL
 * Paste the value of `spring.datasource.url`
 * Select the PostgreSQL driver
 * Enter the username and password found in the `application.properties` file
 
 The database is called 'signup', but the tables are not created in the 'public' schema in the datatbase, which IntelliJ assumes.
 To change the schema within the database:
 * Expand _signup@localhost > databases > signup [1 of 4]_
 * Klick on _[1 of 4]_ and tick the schema named _signup_ + Enter
 
 Now you can expand the signup schema and view the tables
 