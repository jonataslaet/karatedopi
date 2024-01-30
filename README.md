# Karatedopi

Here you find an application that manages karate practitioners and tournaments. You also find the UML project of this application.


## 🚀 Introduction

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.


### 📋 Prerequisites

Before having the technologies installed, it is crucial to have Docker installed.


### 🔧 Running Backend Environment

This is a step-by-step guide on what should be done to have a backend development environment running via the command line.

01. Clone the project to any folder on your operating system

```
git clone https://github.com/jonataslaet/karatedopi
```

02. Navigate through the command line to the karatedopi folder and execute the following command:

```
docker-compose -p karatedopi -f docker-compose.yml up -d
```

03. Right after the previous step, you will have the application running and working, except for the email sending and password renewal part. These parts require you to modify the docker-compose.yml file, adding two environment variables to the karatedopi-backend container, which are: EMAIL_USERNAME and EMAIL_PASSWORD. In them, you put your Gmail and the password generated when enabling your Gmail to be accessed by our application. To learn how to enable Gmail access, you can follow from 5:44 to 6:59 [in this tutorial](https://www.youtube.com/watch?v=A-MUW28njOE&ab_channel=CodeElevate&t=344s)

## 🛠️ Technologies 

The following are the technologies used in building this project:

* [Java 17](https://www.oracle.com/java/) - Backend programming language
* [Angular 16](https://angular.io/) - Framework for Single Page Application
* [Angular Material](https://material.angular.io/) - UI Component Library
* [TypeScript](https://www.typescriptlang.org/docs/) - JavaScript superset programming language
* [UML](https://www.uml.org/) - Unified Modeling Language
* [Astah Community 7.2.0](https://astah.net/products/astah-uml/) - UML Editor

## ✒️ Authors

Here are all the names that have contributed in some way to the development of this project::

* **Jonatas Laet** - *Documentation and Development* - [jonataslaet](https://github.com/jonataslaet)

You can also see the list of all the [colaboradores](https://github.com/jonataslaet/karatedopi/graphs/contributors) who participated in this project.


---
⌨️ with ❤️ by: 
[Jonatas Laet](https://github.com/jonataslaet) 😊