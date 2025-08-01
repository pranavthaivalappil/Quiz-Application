-- schema.sql
-- MySQL schema for QuizMaster Application
-- Author: Your Name
-- Last updated: 2024-06-08

-- This script creates all tables required for the QuizMaster app.
-- Run with: mysql -u root -p < schema.sql

CREATE DATABASE IF NOT EXISTS quiz_app;
USE quiz_app;

-- Table: users
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE,
    best_score INT DEFAULT 0
);

-- Table: questions
CREATE TABLE IF NOT EXISTS questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question TEXT NOT NULL,
    option1 VARCHAR(255) NOT NULL,
    option2 VARCHAR(255) NOT NULL,
    option3 VARCHAR(255) NOT NULL,
    option4 VARCHAR(255) NOT NULL,
    correct_answer VARCHAR(255) NOT NULL
);

-- Table: quiz_attempts
CREATE TABLE IF NOT EXISTS quiz_attempts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    score INT NOT NULL,
    attempt_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
); 