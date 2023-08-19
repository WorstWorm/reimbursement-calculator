# Business Trip Reimbursement Calculation Application

Java Application to manage the reimbursement of expenses of a user after a business trip.

## Project setup
1. Install and configure Java according to information provided at Oracle Java 
page [https://www.oracle.com/java/];
Backend code is created with plain Java 11 but Vaadin frontend dependencies can need Java 17.
2. Install and configure Maven tool according to information provided at Apache Maven Project
page [https://maven.apache.org/]
3. Clone repository to your local machine.
4. Open project to the IDE of your choice as a Maven project.
5. Run tests using 'mvn test' command in terminal or in other way used in your IDE.
6. Run using 'mvn jetty:run' command in terminal or in other way used in your IDE.
7. Open [http://localhost:8080](http://localhost:8080) in the browser.

## Project UI

### General Notice
The program does not rely on an external database, and all information is stored as session data. 
Upon closing and restarting the application, all changes will be lost. However, switching between 
users is entirely possible during the same session.

### Main View
The initial screen allows you to log in using the login credentials configured in the StartData file. 
Eventually, the administrator has the ability to add/remove/modify new users, although this functionality 
is not implemented in the FrontEnd (the backend code is ready). The login data is not secured 
(as it was not the project's objective).

### User View
After logging in as a user, a list of all Reimbursement Claims assigned to this account is displayed. 
Using the "create New Reimbursement" button, the user can create a new claim. The scope of the trip is
set using two pickers: "start of trip" and "end of trip". The "day to be disabled" picker allows 
the addition of a day for which no reimbursement is requested (each such day must be confirmed using
the "add disabled day" button). The added disabled days will appear in the table above. 
If they extend beyond the start and end of the trip, they will still be visible but won't be counted 
in the total days for reimbursement.

Below, a receipt can be added by selecting its type and value. Then press the "add receipt" button 
– this will be included in the claim's data and shown in the table above. The "car mileage" field allows 
entry of the distance traveled during the trip.

The prepared claim is approved by clicking the "confirm reimbursement" button. The claim will then appear 
in the table with information about the anticipated reimbursement (including limits and rates set by 
the administrator). Attempting to access the User View without logging in prompts for appropriate action.

NOTE: Field validation has not been implemented, which means entering text into numeric fields is possible 
(although it will result in an error in the console when attempting to confirm).

NOTE: The reimbursement value is determined according to the administrator's settings at the given moment.
Changes in rates and limits do not affect existing claims (only new ones).

### Admin View
The administrator's view enables the configuration of the daily allowance value (a predefined reimbursement 
value for each day of the trip), car mileage value (a specified reimbursement amount for each driven mile), 
total reimbursement limit (the maximum amount that can be reimbursed), and mileage limit
(the maximum amount that can be reimbursed for mileage). Changes in each of these fields must be confirmed 
using the appropriate "confirm" button.

In the center of the screen, there is a table with receipt categories and their corresponding monetary 
limits. The "modify" button allows the selection of a specific category and the introduction of changes. 
The "remove category" button enables the removal of a selected category. The "add new receipt category" 
button allows the creation of a new category. All changes need to be confirmed with the "confirm" button
at the right side.

Attempting to access the Admin View without logging in prompts for the appropriate action.

NOTE: Field validation has not been implemented, which means that entering text into numeric fields 
is possible (although it will result in an error in the console when attempting to confirm).

NOTE: Negative values (defaulting to -1.0) indicate that the specific value is not considered 
(the limit is disabled in such cases).

## Project structure
- Under the 'src/main/java' are located Application sources
   - 'src/main/java/dto' - stores files necessary for handling certain tables conveniently.
   - 'src/main/java/entities' - contains entity classes on which the program operates.
   - 'src/main/java/enums' - holds enumerations for user types.
   - 'src/main/java/mapper' - includes mappers that transform entities into DTO files for tables.
   - 'src/main/java/repository' - serves as the program's internal database, storing current object values and lists.
   - 'src/main/java/service' - stores classes with business logic, performing calculations and other actions on entities.
   - 'src/main/java/vaadin' - stores classes needed to run Vaadin views, as well as sample starter data for testing the application.
- Under the 'src/test' are located the TestBench test files (tests have been implemented for the most important business classes - UserOperationService and CalculationService))
- 'src/main/resources' contains configuration files and static resources
- The 'frontend' directory in the root folder contains client-side 
  dependencies and resource files. Example CSS styles used by the application 
  are located under 'frontend/themes'