import java.sql.*;
import java.util.UUID;

public class InsertPostData {

    public static void main(String[] args) {
        // データベース接続情報
        String url = "jdbc:h2:~/test";
        String username = "sa";
        String password = "";
        
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // SQL 文
            String sql = "INSERT INTO posts (id, title, contents, username, created_at, updated_at) VALUES (?, ?, ?, ?, NOW(), NOW())";
            
            // PreparedStatement の作成
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                // ランダムな UUID を生成
                UUID postId1 = UUID.randomUUID();
                UUID postId2 = UUID.randomUUID();
                
                // 最初の投稿データを設定
                statement.setObject(1, postId1);
                statement.setString(2, "First Post");
                statement.setString(3, "This is the content of the first post.");
                statement.setString(4, "user1");
                statement.addBatch(); // バッチに追加
                
                // 2番目の投稿データを設定
                statement.setObject(1, postId2);
                statement.setString(2, "Second Post");
                statement.setString(3, "This is the content of the second post.");
                statement.setString(4, "user2");
                statement.addBatch(); // バッチに追加
                
                // バッチ実行
                statement.executeBatch();
                System.out.println("Posts inserted successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

