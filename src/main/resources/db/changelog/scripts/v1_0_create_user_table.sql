-- Liquibase formatted sql
CREATE TABLE IF NOT EXISTS jobschedulerservice.user
(
    id UUID DEFAULT gen_random_uuid(),
    user_name VARCHAR(60) UNIQUE NOT NULL,
    encrypted_password VARCHAR(255) NOT NULL,
    first_name VARCHAR(40),
    last_name VARCHAR(40),
    email VARCHAR(100) UNIQUE,
    role VARCHAR(40) NOT NULL DEFAULT 'USER',
    created_by VARCHAR(40) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(40),
    updated_at TIMESTAMP WITH TIME ZONE,
    CONSTRAINT pk_user PRIMARY KEY (user_name)
);

-- creation of index
CREATE INDEX IF NOT EXISTS idx_user_username ON jobschedulerservice.user (user_name);
