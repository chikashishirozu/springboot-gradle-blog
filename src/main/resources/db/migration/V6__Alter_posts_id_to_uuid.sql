-- 一時的なUUIDカラムを作成
ALTER TABLE posts ADD COLUMN temp_id UUID;

-- 既存の整数IDをUUIDに変換（例えば、md5ハッシュを使用）
-- 注：実際のビジネスロジックに合わせて変換方法を選択
UPDATE posts SET temp_id = gen_random_uuid();

-- 外部キー制約がある場合は一時的に削除（ある場合）
-- ALTER TABLE 関連テーブル DROP CONSTRAINT 制約名;

-- 元のIDカラムを削除
ALTER TABLE posts DROP COLUMN id;

-- 一時カラムを正式なIDカラムにリネーム
ALTER TABLE posts RENAME COLUMN temp_id TO id;

-- IDカラムを主キーに設定
ALTER TABLE posts ADD PRIMARY KEY (id);

-- 必要に応じてインデックスを再作成
-- CREATE INDEX idx_posts_created_at ON posts(created_at);
