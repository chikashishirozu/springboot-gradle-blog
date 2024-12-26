import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class InsertRoleData {

    public static void main(String[] args) {
        // H2 データベースの URL（パスを適宜変更してください）
        String jdbcUrl = "jdbc:h2:~/test";  // H2 のデータベース URL
        String username = "sa";  // ユーザー名
        String password = "";    // パスワード（空でOK）

        // SQL クエリ
        String insertSQL = "INSERT INTO roles (id, role_name, created_at, updated_at) VALUES (?, ?, NOW(), NOW())";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            // UUID の生成
            String roleId1 = UUID.randomUUID().toString();
            String roleId2 = UUID.randomUUID().toString();

            // PreparedStatement の作成
            try (PreparedStatement stmt = connection.prepareStatement(insertSQL)) {
                // 1行目の挿入
                stmt.setString(1, roleId1);
                stmt.setString(2, "ROLE_USER");
                stmt.executeUpdate();

                // 2行目の挿入
                stmt.setString(1, roleId2);
                stmt.setString(2, "ROLE_ADMIN");
                stmt.executeUpdate();

                System.out.println("Roles have been inserted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

