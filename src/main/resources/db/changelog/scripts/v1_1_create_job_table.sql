-- Liquibase formatted sql
CREATE TABLE IF NOT EXISTS jobschedulerservice.job
(
        id BIGSERIAL NOT NULL,
        name VARCHAR(255) NOT NULL UNIQUE,
        status VARCHAR(50) NOT NULL,
        schedule_type VARCHAR(50) NOT NULL,
        cron_expression VARCHAR(50) NOT NULL,
        fixed_rate_ms BIGINT NOT NULL,
        retry_policy VARCHAR(100) NOT NULL,
        payload JSONB,
        last_run_at TIMESTAMP WITH TIME ZONE,
        next_run_at TIMESTAMP WITH TIME ZONE,
        version INT,
        created_by VARCHAR(40),
        created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
        updated_by VARCHAR(40),
        updated_at TIMESTAMP WITH TIME ZONE,
        CONSTRAINT pk_job_jobname PRIMARY KEY (name)
);

-- creation of index
CREATE INDEX IF NOT EXISTS idx_job_name ON jobschedulerservice.job(name);
CREATE INDEX IF NOT EXISTS idx_job_id ON jobschedulerservice.job(id);

