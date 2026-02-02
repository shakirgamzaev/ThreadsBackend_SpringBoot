# Threads Clone Backend

A robust RESTful API backend for a social media application inspired by Threads. This project is built with **Kotlin** and **Spring Boot**, featuring secure authentication and efficient data handling.

## 🚀 Overview

This backend service manages the core functionality of the social platform, including user authentication, thread creation, and data retrieval. It is designed with a focus on clean architecture, separation of concerns, and type safety using Kotlin.

## 🛠️ Tech Stack

*   **Language:** Kotlin (v2.2)
*   **Framework:** Spring Boot (v4.0.1)
*   **Build Tool:** Maven
*   **Database:** PostgreSQL
*   **Security:** Spring Security, JWT (JSON Web Tokens)
*   **Cryptography:** BouncyCastle
*   **JSON Processing:** Jackson

## ✨ Key Features

*   **Secure Authentication:**
    *   Stateless authentication using JWT.
    *   Custom Security Filter Chain implementation (`JWTFilter`).
    *   Secure password handling.
*   **User Management:**
    *   User registration and login.
    *   Profile data management.
*   **Thread Operations:**
    *   Create new threads (posts).
    *   Retrieve threads for authenticated users.
*   **Error Handling:**
    *   Centralized global error handling (`GlobalErrorHandler`) for consistent API responses.
*   **Database Integration:**
    *   Repository pattern for clean data access (`ThreadsRepository`, `UserRepo`).

## 📂 Project Structure

The project follows a standard Spring Boot layered architecture:
