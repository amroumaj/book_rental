CREATE TYPE status_type AS ENUM ('LOGGED_IN', 'LOGGED_OUT');

CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       status status_type NOT NULL
);
