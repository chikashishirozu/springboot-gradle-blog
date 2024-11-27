-- 仮のデータを挿入する SQL 文

-- POSTS テーブルにデータを挿入
INSERT INTO posts (id, title, contents, username, created_at, updated_at)
VALUES 
    (gen_random_uuid(), 'First Post', 'This is the content of the first post.', 'user1', NOW(), NOW()),
    (gen_random_uuid(), 'Second Post', 'This is the content of the second post.', 'user2', NOW(), NOW());

-- ROLES テーブルにデータを挿入
INSERT INTO roles (id, role_name, created_at, updated_at)
VALUES 
    (gen_random_uuid(), 'ROLE_USER', NOW(), NOW()),
    (gen_random_uuid(), 'ROLE_ADMIN', NOW(), NOW());

-- USERS テーブルにデータを挿入
INSERT INTO users (id, username, email, password, roles, created_at, updated_at)
VALUES 
    (gen_random_uuid(), 'user1', 'user1@example.com', 'password1', 'USER', NOW(), NOW()),
    (gen_random_uuid(), 'user2', 'user2@example.com', 'password2', 'ADMIN', NOW(), NOW());

-- USER_ROLES テーブルにデータを挿入
INSERT INTO user_roles (user_id, role_id)
VALUES 
    ((SELECT id FROM users WHERE username = 'user1'), (SELECT id FROM roles WHERE role_name = 'ROLE_USER')),
    ((SELECT id FROM users WHERE username = 'user2'), (SELECT id FROM roles WHERE role_name = 'ROLE_ADMIN'));




