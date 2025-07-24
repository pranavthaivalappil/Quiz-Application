-- seed.sql
-- Sample data for QuizMaster Application
USE quiz_app;

-- Sample users
INSERT INTO users (name, email, best_score) VALUES
('Alice', 'alice@example.com', 80),
('Bob', 'bob@example.com', 60);

-- Sample questions
INSERT INTO questions (question, option1, option2, option3, option4, correct_answer) VALUES
('What is the capital of France?', 'Berlin', 'London', 'Paris', 'Rome', 'Paris'),
('Which language runs in a web browser?', 'Java', 'C', 'Python', 'JavaScript', 'JavaScript');

-- Sample quiz attempts
INSERT INTO quiz_attempts (user_id, score) VALUES
(1, 80),
(2, 60); 