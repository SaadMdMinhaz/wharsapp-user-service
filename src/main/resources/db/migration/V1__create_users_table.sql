CREATE TABLE users (
    id UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID(),
    phone_number NVARCHAR(20) NOT NULL,
    username NVARCHAR(50) NOT NULL,
    display_name NVARCHAR(100) NULL,
    about NVARCHAR(500) NULL,
    profile_picture_url NVARCHAR(500) NULL,
    created_at DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    is_active BIT NOT NULL DEFAULT 1,
    CONSTRAINT pk_users PRIMARY KEY CLUSTERED (id),
    CONSTRAINT uq_users_phone_number UNIQUE (phone_number),
    CONSTRAINT uq_users_username UNIQUE (username)
);
