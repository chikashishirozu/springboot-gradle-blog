# springboot-gradle-blog

# キャッシュをクリアして再ビルド

時にはキャッシュが原因でエラーが発生することがあります。

次のコマンドでキャッシュをクリアし、再ビルドを試してみてください。

bash

./gradlew clean build --no-build-cache

# 実行コマンド例

bash

./gradlew bootRun

java -jar build/libs/xxxx.jar


⚠️ All users, emails, and passwords are dummy values.
⚠️ This SQL file is for local development only.

この設計図を各レイヤーごとに詳しく解説します。

📋 全体アーキテクチャ
このアプリケーションはレイヤードアーキテクチャを採用しており、以下の層で構成されています：

1️⃣ クライアント層（Client Layer）
Browser: HTTPを通じてWebMVC層と通信
ユーザーインターフェースを提供
2️⃣ WebMVC層（Web/MVCレイヤー）
Spring Security Filter Chainが全てのリクエストを認証・認可チェック

コントローラー群：

ErrorController: エラーハンドリング
AdminController: 管理者機能
LoginController: ログイン処理
AuthController: 認証関連
UserController: ユーザー管理
PostController: 投稿管理
BlogController: ブログ表示
HomeController: ホーム画面
各コントローラーは対応するサービスを呼び出し、ビューにデータを渡します。

3️⃣ サービス層（Service Layer）
ビジネスロジックを実装：

UserService: ユーザー関連のCRUD操作
CustomerUserDetailsService: Spring Security用のユーザー詳細取得
BlogService: ブログ記事のCRUD操作
4️⃣ プレゼンテーション層（Presentation Layer）
テンプレートエンジンを使用：

Admin Templates: 管理画面用テンプレート
Thymeleaf Templates: 一般ユーザー画面用
static/images: 画像ファイル
static/css: スタイルシート
5️⃣ データアクセス層（Data Access Layer）
リポジトリパターンを採用：

UserRepository: ユーザーデータアクセス
PostRepository: 投稿データアクセス
BlogRepository: ブログデータアクセス
JDBCまたはJPAを使用してH2データベースと接続

6️⃣ 永続化層（Persistence）
DataLoader: 初期データの投入
Flyway Migrations: データベーススキーマのバージョン管理
H2 Database: インメモリまたはファイルベースのデータベース
7️⃣ 設定・起動層（Configuration & Boot）
RootTemplateConfig: テンプレート設定
BlogApplication: Spring Bootのメインクラス
application.properties: 各種設定
build.gradle: 依存関係管理
8️⃣ 外部サービス層（External Services）
RootTemplateExample: 外部テンプレートの例
External REST API: 外部APIとの連携
⚠️ 問題点と改善提案
🔴 深刻な問題点
1. セキュリティ層の配置が不明瞭
Spring Security Filter Chainが図の右側に独立して配置されていますが、実際には全てのコントローラーの前段で動作すべきです
改善: セキュリティフィルターをWebMVC層の入口に明確に配置
2. サービス層の責務が不明確
CustomerUserDetailsServiceがUserServiceと並列に配置されていますが、これは認証専用サービスです
改善: 認証関連サービスと業務ロジックサービスを明確に分離
3. リポジトリとデータベースの接続方法が混在
JDBC、JDBCの表記が複数あり、JPAなのかJDBCなのか不明確
改善: データアクセス技術を統一（推奨：Spring Data JPA）
🟡 中程度の問題点
4. AdminControllerとAuthControllerの責務重複
管理者認証がどちらで処理されるか不明確
改善: AuthControllerを一般認証専用、AdminControllerを管理機能専用に明確化
5. テンプレート層の構造が複雑
Admin TemplatesとThymeleaf Templatesが分離されていますが、両方Thymeleafを使用している可能性
改善: テンプレートを機能別（public/admin）で整理
6. 外部サービスとの統合ポイントが不明確
External REST APIがどのように使用されるか、どこから呼ばれるか不明
改善: サービス層に外部API呼び出し専用のクラスを追加（例：ExternalBlogApiClient）
🟢 軽微な改善提案
7. エラー処理の一元化
ErrorControllerだけでなく、グローバル例外ハンドラーの追加を推奨
追加: @ControllerAdviceを使用した統一的な例外処理
8. キャッシュ層の欠如
頻繁にアクセスされるブログ記事などにキャッシュがない
追加: Spring CacheまたはRedisの導入
9. DTO層の欠如
エンティティを直接コントローラーで使用している可能性
追加: DTO（Data Transfer Object）層を追加してエンティティを保護
10. ログ・監視機能が不明
ログ管理やアプリケーション監視の仕組みが見えない
追加: SLF4J + Logback、Spring Boot Actuatorの導入
✅ 優れている点
レイヤーの分離: 各層が明確に分離されている
Spring Securityの導入: セキュリティを考慮した設計
データベースマイグレーション: Flywayによるスキーマ管理
初期データ投入: DataLoaderによる開発環境の整備
RESTful設計: コントローラーが適切に機能分割されている
