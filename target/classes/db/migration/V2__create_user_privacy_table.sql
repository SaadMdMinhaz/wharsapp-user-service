CREATE TABLE user_privacy (
    user_id UNIQUEIDENTIFIER NOT NULL,
    last_seen_visibility NVARCHAR(20) NOT NULL DEFAULT 'EVERYONE',
    profile_photo_visibility NVARCHAR(20) NOT NULL DEFAULT 'EVERYONE',
    about_visibility NVARCHAR(20) NOT NULL DEFAULT 'EVERYONE',
    read_receipt_enabled BIT NOT NULL DEFAULT 1,
    CONSTRAINT pk_user_privacy PRIMARY KEY CLUSTERED (user_id),
    CONSTRAINT fk_user_privacy_user FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE
);
