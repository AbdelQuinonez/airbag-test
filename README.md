
# Airbag Android

This repository hosts the code of the Airbag project.

## Base Architecture

**MVVM**

I used Model-View-ViewModel (MVVM) that is a software design pattern that is structured to separate program logic and user which is separated in three principal layers:

**Model:** This layer is responsible for the abstraction of the data sources. Model and ViewModel work together to get and save the data.
**View:** The purpose of this layer is to inform the ViewModel about the user’s action. This layer observes the ViewModel and does not contain any kind of application logic.
**ViewModel:** It exposes those data streams which are relevant to the View. Moreover, it serves as a link between the Model and the View.
  

## Project Structure
  
The project structure is based on Android recommended standards from the [Guide to app architecture](https://developer.android.com/topic/architecture)

#### **utils**

Here we have to define all required utilities such as constants, permission tools, dispatchers tools... 

#### **database**

Mainly here we have to define the application database with their daos and models that will be responsible for the persistence of the data

#### **di**

Here we have to define all our classes and abstract classes that are going to be injected into our code to avoid dependencies

#### **domain**

domain package is responsible for encapsulating complex business logic, or simple business logic that is reused by multiple ViewModels, in our case domain models and workers

#### **interactors**

This package contains the business logic of the app (use cases) and sits between the data module and the ui module.

This module could be optional. If not added, business logic would be included in viewmodels, but was implemented to apply clean architecture principles

#### **remote**

Here we have to define the repositories that are connected to remote calls including their implementations

#### **ui**

This layer is responsible of displaying the data on screen.
Some of the tasks are listed below:

- Views that are rendered on screen (lists, button)

- State holders that hold the data and logic exposed to the UI (viewmodels)


  

## Naming conventions


The project follows the naming conventions that are in the following links:


- [Kotlin Style Guide](https://developer.android.com/kotlin/style-guide)
