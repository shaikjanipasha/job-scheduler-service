-- Liquibase formatted sql
CREATE TABLE IF NOT EXISTS jobschedulerservice.distributed_scheduler_lock (
    id SERIAL PRIMARY KEY,
    lock_key VARCHAR(255) NOT NULL,
    locked BOOLEAN NOT NULL,
    lock_time TIMESTAMP WITH TIME ZONE,
    expiry_time TIMESTAMP WITH TIME ZONE,
    last_heartbeat_time TIMESTAMP WITH TIME ZONE
);
