-- Liquibase formatted sql
CREATE TABLE IF NOT EXISTS jobschedulerservice.user
(
    id UUID DEFAULT gen_random_uuid(),
    user_name VARCHAR(60) UNIQUE NOT NULL,
    encrypted_password VARCHAR(255) NOT NULL,
    first_name VARCHAR(40),
    last_name VARCHAR(40),
    email VARCHAR(100) UNIQUE,
    created_by VARCHAR(40) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(40),
    updated_at TIMESTAMP WITH TIME ZONE,
    CONSTRAINT pk_user PRIMARY KEY (user_name)
);

-- creation of index
CREATE INDEX IF NOT EXISTS idx_user_username ON jobschedulerservice.user (user_name);

-- insert two sample users in to the system
INSERT INTO jobschedulerservice.user (
    id,
    user_name,
    encrypted_password,
    first_name,
    last_name,
    email,
    created_by,
    created_at,
    updated_by,
    updated_at
)
VALUES (
    gen_random_uuid(),
    'testuser1',
    'NzIzMjQxNjgyN3Rlc3R1c2VyMQ==',
    'test',
    'user1',
    'testuser1@example.com',
    'system',
    CURRENT_TIMESTAMP,
    'system',
    CURRENT_TIMESTAMP
),
(
gen_random_uuid(),
    'testuser2',
    'NzIzMjQxNjgyN3Rlc3R1c2VyMg==',
    'test',
    'user2',
    'testuser2@example.com',
    'system',
    CURRENT_TIMESTAMP,
    'system',
    CURRENT_TIMESTAMP
);
