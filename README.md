# CIS 183 Homework 03

R.Sondergeld
Monroe County Community College
CIS 183
Mobile App Development

## Description
- This application is designed to create and edit student accounts
- Student information is stored and retrieved from a SQLite database
- Each student has the following information:
  - Student ID ( 6 digit number and primary key)
  - First Name
  - Last Name
  - Email
  - Age
  - GPA
  - Major
- Additionally, there is a major object which has the following information:
  - Id ( auto increment primary key )
  - Name
  - Prefix

## Demonstrations

### Insert Demonstration

https://github.com/user-attachments/assets/5849c2f5-80d8-4cc4-a5ae-812951156cd6

### Update Demonstration

https://github.com/user-attachments/assets/5ea94d46-f53c-449e-af53-fd4d4eca2443

### Filter Demonstration

https://github.com/user-attachments/assets/256729f9-777e-48d8-8410-15eef12503ea

### Delete Demonstration

https://github.com/user-attachments/assets/46ef5104-2f55-4989-8ff1-b0e6946e2041


## Requirements
- [x] Data entered into the project must be persistent and stored in a database with two tables:
  - [x] Students
  - [x] Majors
- [x] Must make a student object and table with the following attributes:
  - [x] First name
  - [x] Last Name
  - [x] Username ( in this case Student ID )
  - [x] Email
  - [x] Age
  - [x] GPA
  - [x] Major
- [x] Must make a major object and major table with the following attributes:  
  - [x] Major Id
  - [x] Major name
  - [x] Major PRefix (CIS, BUS, etc.)
- [x] Must use a List view on the main screen to show all students ( Used RecycleView in place of list-view )
- [x] Must create a custom cell that contains the first name, last name, and user name (or Student ID in this case)
- [ ] Must create a custom cell and adapter for your list view on the search page ( did not use a search page )
- [x] After adding a student, you should clear all of the fields
- [x] Must work with 0-n students in our system
