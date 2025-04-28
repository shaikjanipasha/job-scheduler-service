-- Liquibase formatted sql
CREATE TABLE IF NOT EXISTS jobschedulerservice.job
(
        id BIGSERIAL NOT NULL,
        name VARCHAR(255) NOT NULL UNIQUE,
        status VARCHAR(50) NOT NULL,
        schedule_type VARCHAR(50) NOT NULL,
        cron_expression VARCHAR(50) ,
        fixed_rate_ms BIGINT,
        retry_policy VARCHAR(100),
        payload JSONB,
        last_run_at TIMESTAMP WITH TIME ZONE,
        next_run_at TIMESTAMP WITH TIME ZONE,
        max_retries INT,
        retry_delay_in_seconds INT,
        exponential_base INT,
        exponential_initial_delay INT,
        retries_attempted INT,
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
CREATE INDEX IF NOT EXISTS idx_job_status ON jobschedulerservice.job(status);
CREATE INDEX IF NOT EXISTS idx_job_next_run_at ON jobschedulerservice.job(next_run_at);

