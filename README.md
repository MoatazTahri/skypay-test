# SkyPay Test 

## Overview  
This is a project for **SkyPay** focused on implementing and testing basic banking functions using **JUnit** and **Mockito**. The main goal is to apply **acceptance testing** principles.
*Notice : There is no database used in this project.

## Project Structure  
- **Transaction (class)**: Represents a single banking operation (date,deposit, and withdrawal). 
- **AccountService (Interface)**: Defines core banking operations (`deposit`, `withdraw`, `printStatement`).  
- **Account (Implementation)**: Implements `AccountService`, manages transactions locally without using a database.  
- **Client**: Works with `AccountService` to perform banking operations.  
- **Tests**: Uses **JUnit** and **Mockito** to test `Client` and `Account` behavior. 
