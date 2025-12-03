# Photovoltaic Project Manager

Web application created to help managing photovoltaic investments projects and create materials needed to build PV installation efortlessly.

This application allows end users to manage their photovoltaic projects. Users can create many projects and keep them in one place, making them easy to access and manage. While managing your installations is comfortable, **this application stands out for it's function to create basic electrical and construction materials** for variety types of photovoltaic installations.

Creating installation is easy and user friendly - there is only need to fill simple form with basic data about installation. Everything else is done in the backend of the application using OOP principles and design patterns like strategy or factory, which makes application scalable, easy to maintain and fullfill open/closed principle. This application can handle installations up to 49,995 kW due to the polish energetic law policy. Data is validated in the backend of the application, preventing to save invalid installation, and also if something goes wrong, notification or error message is displayed to user.

**Live demo available at https://photovoltaic-project-manager.onrender.com**

Read the following sections for more details.

# Core features

- Role based access
- Creating and managing projects
- Creating and managing PV installations
- Creating material lists effortlessly
- Exporting material lists to an excel file
- Live demo and CI/CD with GitHub Actions

# Technologies

- Spring Boot (Security, JPA, Validation)
- PostgreSQL
- Vaadin
- JUnit5 & Mockito
- Lombok
- Apache POI OOXML
- GitHub Actions
- Docker
- Caffeine


 
# Application security

Application is secured with Spring Boot security in the backend layer. Endpoints are secured with role based access - user needs to be logged in and have proper 'USER' role.
 
Any activity will be blocked on the server side if user is not logged in. Furthermore, UI routes will redirect to login page, when anonymous user tries to reach protected endpoint.

More than that, services have implemented validation to keep users from having access to other users resources.

As the extra security layer services use method level security.

To be able to use application, user need to be logged in and create account, if don't already have one.
 
Registration form has validation rules and user need to meet special conditions for username and password.

<img width="1919" height="912" alt="1" src="https://github.com/user-attachments/assets/1735e6ac-18a7-491c-9ceb-d2bcd3afd1f9" />




# Data validation and exception handling




Application is validated in multiple places through backend services and UI components like notification or error messages. Server won't let invalid data to be put into database. 

Services will validate data, throw existing or custom expection, and display notification to user in browser. Custom exception have two different messages - one to be displayed for user, and one to be displayed in logs. 

In top of that applicataion uses **Bean Validation** to display proper information under the data forms before hitting save button. Notification will be shown if user persists to save invalid data. Most of the validation has custom rules on the created installation parameters. 


## Bean level validation

Below are two examples of bean level validation.

In order to register correctly, user need to provide correct username and password. 

<img width="1919" height="912" alt="2" src="https://github.com/user-attachments/assets/246a8eb5-0d82-4ea1-a6d0-1b03b43f325c" />


While creating projects, special conditions have to be met. For example allowed PV modules parameters have to be in range of values available in the market.

<img width="726" height="556" alt="3" src="https://github.com/user-attachments/assets/4de3142a-2f95-4f11-903e-1c0008cdec56" />


## Catching exceptions with notifications

Before inserting data into database everything have to be valid. Not everything can be done through Bean Validation, some things need to be done in the services. 

For example if login credentials are invalid, notification is poped.

<img width="406" height="426" alt="4" src="https://github.com/user-attachments/assets/b43ffa1e-5315-4b5d-90ef-201fc61bd9fc" />


Another example, while creating installation, due to the polish energetic law restriction microinstallations are up to 50 kWp (exclusive) of electrical power. That is why, maximum installation power is 49,995 (modules have power step of 5 Wp). Exceeding that power will end with notification.


<img width="516" height="810" alt="5" src="https://github.com/user-attachments/assets/1c76fac5-e961-479d-9bb8-2f26ae6f1de3" />



## Caching

Application is cached using Caffeine with custom CaffeineCache implementation to provide logging for cache operations.

Example log for missing cache:

<img width="1502" height="28" alt="image" src="https://github.com/user-attachments/assets/95b6136a-c31b-4505-bf32-a0c0e3836bf3" />


## Testing

Application is tested with JUnit5 and Mockito for various scenarios. Dozens of tests are implemented to provide application reliability.

## CI/CD

CI/CD is applied with GitHub Actions. Workflow run's all the tests, caching Maven dependencies, building JAR file and deploying application on Render server via Render API.


# Navigation through application

## Main view

After login user can either browse projects or create new one.

<img width="1911" height="919" alt="6" src="https://github.com/user-attachments/assets/b1ac9088-57bf-4566-a718-65a5fd8f0658" />



## Creating and updating project

To create project user need to fill form with basic data about project and most basic data about PV modules that will be used. This data is needed to create electrical materials.

<img width="1917" height="916" alt="7" src="https://github.com/user-attachments/assets/b1b78b89-fafd-494a-958d-7785001d334d" />


Updating project has same form, with that difference that it is filled with currently set values.

Updating module data in project will trigger service method to recreate materials in every installation in this project, to keep data synchronized.



## Creating and updating installation

To create installation user need to provide basic data about installation. Many of the form fields are selectable comboboxes to choose from. User doesn't really need to know a lot of technical stuff to create installation, only most basic like cables length (for example from the inverter to the switchboard in the building), phase number or if building has lighting protection.

Form layout in add and update installation routes are implemented with factory and strategy patterns.

<img width="416" height="816" alt="8" src="https://github.com/user-attachments/assets/4a32db5b-13c5-4196-8310-4c60b131d74f" />


Updating installation data will trigger service method to recreate each material in installation, to keep data synchronized.


## Browsing projects

In *my projects route*, user can browse all the projects. Browsing projects uses pagination system. This route show basic project data and creation date.

<img width="1911" height="918" alt="9" src="https://github.com/user-attachments/assets/83fcba9d-4932-40d2-9814-cfcb2b58bcbe" />


To see all the details, user need to go to the project route. All the details are available there, as well as buttons to update and remove project data or export materials for all the installations within project to excel.
Also, that route contains all the installations within the project (pageable).


<img width="1891" height="916" alt="10" src="https://github.com/user-attachments/assets/49ea5af5-4860-41e8-a552-c75af402f8fb" />


## Browsing installations

Installation can be reached from the project page. In this route, there are all the installation details, button to export materials for the installation to excel, button to get back to project and edit/remove icon.

Below the basic data, there are also construction and electrical materials for given installatiion.

<img width="760" height="842" alt="11" src="https://github.com/user-attachments/assets/4f556f87-0458-481e-96c4-b344bda62ab2" />



## Exporting to excel

When clicking *export* button, user need to provide file name in the dialog window. After that, file will be downloaded to the folder specified in the browser settings.

<img width="198" height="154" alt="image" src="https://github.com/user-attachments/assets/e55ff3fb-b07b-4d85-af2c-0d58efe01f23" />


<img width="468" height="148" alt="image" src="https://github.com/user-attachments/assets/4449af5a-e570-4747-ab74-df005f16ce23" />

In the downloaded file for whole project there are 2 sheets. Materials and summary.

In **Materials** sheet there is basic data and materials for the installation.

<img width="730" height="760" alt="image" src="https://github.com/user-attachments/assets/521b04c1-dc64-4f54-b70e-d2380b9454de" />

In **Summary** sheet there is projects summary with materials for all the installation for given project.

<img width="636" height="742" alt="image" src="https://github.com/user-attachments/assets/24ffc509-baeb-4eee-9702-5edf95d36118" />

Exporting single installation materials doesn't have summary sheet.


# Creating materials

Materials are created on the server side, using factory that creates each material based on the input parameter. Materials are then assembled into whole installation in the Assembler class. These patterns makes application easy to scale up for different types. Materials are created and calculated based on Author engineering expertise.

Creating materials is user friendy with combobox values to pick and form validation. Application takes various scenarios and parameters to consider, making application versatile.

Materials are created based on the following parameters:


**Module data from project**

- Module power

- Module length, width and frame
  

**Installation data**

- Installation type. Currently there is implemented 5 types of installation with factory pattern.
    
- Module orientation - horizontal or vertical to pick
 
 - Phase number - single or three phased to pick
 
- Lighting protection in building - present or not present to pick
 
- Cables length - user need to provide assumed values
 
- Number of string - user need to provide assumed values
 
- Rows of PV modules - user can add multiple rows of modules

  **Each field is validated with Bean Validator and protected on the backend from saving invalid values into database**

  Those parametrs allows application to estimate needed materials with high accuracy.


**Important note**

To use application user need to have at least basic knowledge about how to build photovoltaic installation and what are the components of building electrical energy supply system.

Application takes into account construction materials with very high accuracy. When it comes to electrical materials, application creates basic materials that are preety much always needed to build installation according to law, polish norms and good engineering practice, however installations may have some special requirments that may vary. If installation goes out of the typical polish house box, user should check created material list carefully and take into account every single special requirment or request.




