##CS3300-project-2

Team Members: Enioluwa Daniel Adebisi, Mukhtar Kussaiynbekov, Zach McGee, Samuel Zhang, Chunhao Zou

###Workflow
To use the deployed version, go to:
[link here]

To run the backend of the project, follow these steps:
1. install Maven
2. run command `mvn package`
3. run command `export GOOGLE_APPLICATION_CREDENTIALS={path to src/main/resources/bill-splitter-331718-df5c7122b89f.json}`
4. run command `mvn spring-boot:run`

To run the frontend of the project, follow these steps:
1. install npm
2. run command `npm install`
3. run command `npm run start`

###Usage
[incomplete]

###Software
- `\src\main\java` contains the backend code
    - `..\com.group1.billsplitter` contains the SpringBoot controllers and SQL database integration
    - `..\entities` contains the Java objects representing bills and related things
- `\front-end\project-2\src` contains the frontend code
    - `..\pages` and `..\components` comprise the application dashboard
