## Welcome to PV-Calculator project

This is a CLI application that was made for my personal use in my work as an renewable energy engineer. 
Its purpose is to create basic materials lists for a photovoltaic installation based on the required input data. 
It creates both electric and construction materials lists, and exports list to an Excel file.

This application is made for standard installations up to 60 kWp. All the formulas are based on good engineering practice.
If there are some special requirements you should take note, to consider any specific installation requirements.

## Features

- creating list of basic electrical materials for PV installation like cables, electrical breakers, inverters, switchboard and else.
- creating list of basic construction materials for different installation types on flat roof or sloping roof
- save the list to an Excel file to the location of your choice

## Technologies

- Java 22
- Lombok
- Apache POI
- JUnit5
- Mockito

## How to run

>*note*
>
>Due to the fact that I made this app for personal use, it is purely CLI app with no real frontend.
>
>Also text within app is in polish language
>
>This project was made to help me speed up some work.

**To run the app open command line runner and follow the steps:**

**1. Clone the repository**
  ```bash
  git clone https://github.com/PatrykKukula/pv-calculator.git
```

**2. Create JAR file**
   ```bash
   mvn clean install
   ```
   
**3. Run JAR file**
 ```bash
  java -jar target/pv-calculator-1.0-jar-with-dependencies.jar
```


## How to navigate application

>*note*
>
>*app is made to be easy to operate, so most of the time you need to provide assigned numeric option, in order to provide input data*
>
>*no need for any text typing*
>*if you provide input data that is incorrect you will get the information to correct the data. You cannot move on until you provide valid input*

First you need to choose what to do. Create new list or exit program.

![start](https://github.com/user-attachments/assets/1e72be02-50ec-473c-b694-65fba788b071)

When you chose to create new list, you need to provide basic information about installation.

In first step, you need to provide basic information about pv modules such as:

- power
- frame thickness
- module width and length

If you do so you can step over, or input data again.

![Screenshot_1](https://github.com/user-attachments/assets/57922bc7-5ffa-4bb9-b205-1867314db852)

In next step you have menu with options:

1. add new installation
2. return to previous menu
3. show installation list
4. save list to file
5. clear installation list

  ![Screenshot_1](https://github.com/user-attachments/assets/23e173d0-0add-4fa9-936a-773effceb4d9)

**Let's break down each step.**

**1. Add new installation.** 

In this step you need to provide basic information about the installation such as:

- Surge arrester type (T1 or T1+2)
- Modules orientation (vertical/horizontal)
- Type of construction. Currently you have 5 options to choose that are most popular and covers most cases.
- Length of AC and DC cables route
- Number of string

  ![Screenshot_2](https://github.com/user-attachments/assets/69eafc45-245c-4940-9a8a-3a762b4eec3b)

  
After you provide all the needed information, you need to setup rows of modules.

![Screenshot_3](https://github.com/user-attachments/assets/724f5dfa-be57-4fc1-827d-3255f106f02e)

You need to type in number of modules that are gonna be in the row, and you can add multiple rows. If all your rows are set, you can move on, or add rows again if you made any mistake. 

![Screenshot_3](https://github.com/user-attachments/assets/8c347c39-c4c0-487e-9276-2eb37efc5cef)

At this point your installation is added to list, and required materials are stored and ready to be saved. You can add multiple installations.

**2. Exit to previous menu**

Choose that option if you finished adding installations, saved your list to file and want to exit the app.

**3. Show installation list**

This option let you see already added installations.

![Screenshot_4](https://github.com/user-attachments/assets/1b837414-8167-4ee7-af88-e4849dc9bddf)

**4. Save list to the file**

You need to provide full path in pattern {drive}:/{folderName}/.../{folderName}

And then in the next step you need to provide file name.

The result is in the folder you chose:

![result](https://github.com/user-attachments/assets/45ef4b5c-0b81-444c-a4a4-e3750cde8b1e)

Result inside the file:

![excel](https://github.com/user-attachments/assets/ea4b8fa2-0499-4889-b9c8-723c502b2749)


**5. Clear installation list**

Clears currently added installations. No more, no less.
