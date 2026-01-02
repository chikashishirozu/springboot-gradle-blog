-- 仮のデータを挿入する SQL 文

-- まずユーザーを作成
INSERT INTO users (id, username, email, password, enabled, account_non_locked)
VALUES 
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'user1', 'user1@example.com', '$2a$10$CE9zBhxW6rA3GsMsr2E9K.MzvtQi5wwZDYNkFN0Vok8hNZoZuAJ52', true, true),
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a22', 'user2', 'user2@example.com', '$2a$10$PCr0UPRyFIrh7DLLh1X8Q.D0KsWHW9hdhQ94cN.QjJbSlsjtaiStW', true, true);

-- POSTS テーブルにデータを挿入
INSERT INTO posts (title, content, user_id)
VALUES 
    ('First Post', 'This is the content of the first post.', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11'),
    ('Second Post', 'This is the content of the second post.', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a22');

-- ROLES テーブルにデータを挿入
INSERT INTO roles (name)
VALUES 
    ('ROLE_USER'),
    ('ROLE_ADMIN');

-- USER_ROLES テーブルにデータを挿入
INSERT INTO user_roles (user_id, role)
VALUES 
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'ROLE_USER'),
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a22', 'ROLE_ADMIN');




