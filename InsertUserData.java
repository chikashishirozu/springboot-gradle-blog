import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertUserData {
    public static void main(String[] args) {
        // パスワードをエンクリプトするためのPasswordEncoderのインスタンスを作成
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        // パスワードをエンクリプト
        String rawPassword1 = "password1";
        String encryptedPassword1 = passwordEncoder.encode(rawPassword1);

        String rawPassword2 = "password2";
        String encryptedPassword2 = passwordEncoder.encode(rawPassword2);

        // データベース接続設定
        String url = "jdbc:h2:~/test"; // H2 データベースの接続URL
        String username = "sa"; // データベースのユーザー名
        String password = ""; // データベースのパスワード（空の場合）

        // SQLクエリ
        String sql = "INSERT INTO users (id, username, email, password, roles, created_at, updated_at) VALUES (?, ?, ?, ?, ?, NOW(), NOW())";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            // SQL文を準備
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // ID、ユーザー名、メールアドレス、エンクリプトされたパスワード、役割を設定
            pstmt.setString(1, java.util.UUID.randomUUID().toString()); // ランダムなUUIDをIDに使用
            pstmt.setString(2, "user1");
            pstmt.setString(3, "user1@example.com");
            pstmt.setString(4, encryptedPassword1); // エンクリプトされたパスワードを設定
            pstmt.setString(5, "USER");

            // SQLを実行してデータを挿入
            pstmt.executeUpdate();

            // 2番目のユーザーのデータも挿入
            pstmt.setString(1, java.util.UUID.randomUUID().toString());
            pstmt.setString(2, "user2");
            pstmt.setString(3, "user2@example.com");
            pstmt.setString(4, encryptedPassword2); // エンクリプトされたパスワードを設定
            pstmt.setString(5, "ADMIN");

            pstmt.executeUpdate();

            System.out.println("データが正常に挿入されました。");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



