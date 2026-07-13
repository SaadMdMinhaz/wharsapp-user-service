CREATE TABLE contacts (
    id UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID(),
    owner_user_id UNIQUEIDENTIFIER NOT NULL,
    contact_user_id UNIQUEIDENTIFIER NOT NULL,
    nickname NVARCHAR(100) NULL,
    created_at DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT pk_contacts PRIMARY KEY CLUSTERED (id),
    CONSTRAINT uq_contacts_owner_contact UNIQUE (owner_user_id, contact_user_id),
    CONSTRAINT fk_contacts_owner FOREIGN KEY (owner_user_id)
        REFERENCES users (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_contacts_contact FOREIGN KEY (contact_user_id)
        REFERENCES users (id)
        ON DELETE NO ACTION
);
