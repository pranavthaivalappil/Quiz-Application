-- migrations/002_add_indexes.sql
-- Migration: Add indexes for performance

CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_attempt_user ON quiz_attempts(user_id); 