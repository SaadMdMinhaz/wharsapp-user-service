CREATE TABLE blocked_users (
    id UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID(),
    blocker_id UNIQUEIDENTIFIER NOT NULL,
    blocked_id UNIQUEIDENTIFIER NOT NULL,
    created_at DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT pk_blocked_users PRIMARY KEY CLUSTERED (id),
    CONSTRAINT uq_blocked_users_blocker_blocked UNIQUE (blocker_id, blocked_id),
    CONSTRAINT fk_blocked_users_blocker FOREIGN KEY (blocker_id)
        REFERENCES users (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_blocked_users_blocked FOREIGN KEY (blocked_id)
        REFERENCES users (id)
        ON DELETE NO ACTION
);
