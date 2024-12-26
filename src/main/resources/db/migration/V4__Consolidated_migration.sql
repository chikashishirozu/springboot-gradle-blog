-- V4__Consolidated_migration.sql
DROP TABLE posts;

DROP TABLE roles;

DROP TABLE users CASCADE;

-- ユーザーテーブルの作成
CREATE TABLE users (
    id UUID PRIMARY KEY,  -- UUIDの自動生成
    username VARCHAR(150) UNIQUE NOT NULL,
    email VARCHAR(255) NOT NULL, 
    password VARCHAR(255) NOT NULL,
    roles VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ポストテーブルの作成
CREATE TABLE posts (
    id UUID PRIMARY KEY,  -- UUIDの自動生成
    title VARCHAR(255) UNIQUE NOT NULL,
    contents TEXT NOT NULL,
    username VARCHAR(150) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- rolesテーブルの作成
CREATE TABLE roles (
    id UUID PRIMARY KEY,
    role_name VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- user_rolesテーブルの作成
CREATE TABLE user_roles (
    user_id UUID,
    role_id UUID,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);



