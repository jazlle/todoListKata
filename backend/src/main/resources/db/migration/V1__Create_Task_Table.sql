CREATE TABLE TASK (
    id BIGSERIAL PRIMARY KEY,
    label VARCHAR(255) NOT NULL,
    completed BOOLEAN DEFAULT FALSE
);