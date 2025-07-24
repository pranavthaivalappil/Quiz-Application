-- README.sql
-- Example queries for QuizMaster database

-- Get all users and their best scores
SELECT name, best_score FROM users;

-- Get all questions
SELECT * FROM questions;

-- Get quiz attempts for a user
SELECT * FROM quiz_attempts WHERE user_id = 1;

-- Get the top 5 scores
SELECT name, best_score FROM users ORDER BY best_score DESC LIMIT 5; 