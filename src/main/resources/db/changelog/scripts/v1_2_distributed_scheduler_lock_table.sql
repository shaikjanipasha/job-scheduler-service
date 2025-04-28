-- Liquibase formatted sql
CREATE TABLE IF NOT EXISTS jobschedulerservice.distributed_scheduler_lock (
    id SERIAL PRIMARY KEY,
    lock_key VARCHAR(255) NOT NULL,
    locked BOOLEAN NOT NULL,
    lock_time TIMESTAMP WITH TIME ZONE,
    expiry_time TIMESTAMP WITH TIME ZONE,
    last_heartbeat_time TIMESTAMP WITH TIME ZONE
);

CREATE INDEX IF NOT EXISTS idx_distributed_scheduler_lock_key ON jobschedulerservice.distributed_scheduler_lock (lock_key);
CREATE INDEX IF NOT EXISTS idx_distributed_scheduler_lock_expiry_time ON jobschedulerservice.distributed_scheduler_lock (expiry_time);

