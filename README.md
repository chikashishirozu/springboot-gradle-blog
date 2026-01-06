# springboot-gradle-blog

https://start.spring.io/　にアクセスし、✅ 最初の入口

→ Spring Initializr（公式サイト）でプロジェクトを生成・ダウンロード

🔁 その後

→ gradlew を使ってビルド・依存関係取得

→ 慣れてきたら SDKMAN! や IDE 連携に進む

# Javaのインストール

sudo dnf install java-21-openjdk java-21-openjdk-devel

sudo alternatives --config java

または、

正しい SDKMAN のインストール方法（公式）

SDKMAN は curl で入れるのが唯一の正解 です。

✅ 1️⃣ 必要なもの（ほぼ入っている）

sudo dnf install curl zip unzip

✅ 2️⃣ SDKMAN をインストール

curl -s "https://get.sdkman.io" | bash

✅ 3️⃣ shell を再読み込み

source "$HOME/.sdkman/bin/sdkman-init.sh"

または新しいターミナルを開く。

✅ 4️⃣ 動作確認

sdk version

バージョンが出れば成功。

なぜ root（sudo）で入れないのか

1. SDKMANをインストール

2. 

bash

curl -s "https://get.sdkman.io" | bash

source "$HOME/.sdkman/bin/sdkman-init.sh"

3. Gradleをインストール

bash

sdk install gradle

4. インストール確認

bash

gradle --version

5. Gradle Wrapperを生成

bash

cd ~/springboot-gradle-blog-main

gradle wrapper

🧠 雑学・業界話

SDKMAN は ユーザーごとの環境管理

Java / Gradle / Kotlin のバージョンは

プロジェクトや人ごとに違う

/usr/bin に入れると衝突する

そのため、

「SDKMAN は ~/.sdkman に入る」

という設計になっています。

これは pyenv / nvm / rbenv と同じ思想です。

sdk list java

Oracle JDK
→ ライセンス確認が面倒（個人学習以外）

Zulu / Corretto
→ クラウド特化用途が多い

Temurin
→ 学習・個人開発・実務、全部OK

最新のJavaを確認後インストール

sdk install java 21.0.9-tem

sdk use java 21.0.9-tem

SDKMAN! の PATH が bashrc で下の方にある

# alternatives の JDK が使われている場合、

👉 対策：bashrc の SDKMAN 設定を一番最後に移動する

✔ 修正手順

nano ~/.bashrc

以下のように 一番下だけに残す：

SDKMAN設定

export SDKMAN_DIR="$HOME/.sdkman"

[[ -s "$SDKMAN_DIR/bin/sdkman-init.sh" ]] && source "$SDKMAN_DIR/bin/sdkman-init.sh"


保存したら反映：

source ~/.bashrc


# 🧹 コンテナを作り直す

pgAdmin のエラーステータスを解消するため再作成します：

docker compose down

docker compose up -d

docker ps

# SELinux がコンテナの Web アクセスをブロックしている（Fedoraあるある）

getenforce が Enforcing なら高確率でこれ。

一時的に確認のため無効化：

sudo setenforce 0

SELinuxを元に戻す時

sudo setenforce 1

# SELinux を有効のまま pgAdmin を動かす方法（正攻法）

もし今後「Enforcing のまま動かしたい」なら：

方法A：ポートバインドを許可する

sudo setsebool -P container_connect_any 1

方法B：Podman rootless のポートを明示的に許可

sudo semanage port -a -t http_port_t -p tcp 5050

http://localhost:5050　を確認

# キャッシュをクリアして再ビルド

時にはキャッシュが原因でエラーが発生することがあります。

次のコマンドでキャッシュをクリアし、再ビルドを試してみてください。

bash

./gradlew clean build --no-build-cache

./gradlew clean build -x test  # まずはテストなしでビルド

# Gradle における「依存関係修理」コマンド

bash

# 依存関係を再解決

./gradlew dependencies --refresh-dependencies

./gradlew build --refresh-dependencies

# または全ての依存関係を再ダウンロード

rm -rf ~/.gradle/caches/modules-2/

# オフラインモードで依存関係解決を試す

./gradlew --offline dependencies

本当に必要なもの（実務的）

✅ ① Gradle Wrapper の本体を取得済み

一度オンラインで：

./gradlew --version

これで：

~/.gradle/wrapper/dists/gradle-8.x/

が作られます。

✅ ② 依存関係をすべて事前取得

./gradlew build

または

./gradlew dependencies

これで：

~/.gradle/caches/modules-2/files-2.1/


に 全 jar がキャッシュされます。

✅ ③ ここで初めて --offline が意味を持つ

./gradlew --offline build

👉 ネットに一切出ずに成功すれば OK。

4️⃣ 業界話：CI / 社内ネットワーク事情

実務ではよくあります：

社内ネットワークは 外部アクセス禁止

Maven Central 直アクセス不可

プロキシ or ミラー必須

そのため現場では：

一度オンライン環境でキャッシュ

.gradle をアーティファクトとして保存

Docker イメージに焼き込み

という運用が普通です。

# 不要依存を検出する（整理の核心）

プラグイン使用（定番）

plugins {
    id "com.autonomousapps.dependency-analysis" version "1.32.0"
}

実行

./gradlew buildHealth

結果

使ってない依存

testにしか要らない依存

直接依存にすべきもの

👉 「削除候補リスト」を自動生成

# 実行コマンド例

bash

./gradlew bootRun --args='--spring.profiles.active=dev'

./gradlew bootRun

java -jar build/libs/xxxx.jar


⚠️ All users, emails, and passwords are dummy values.

⚠️ This SQL file is for local development only.



# この設計図を各レイヤーごとに詳しく解説します。


# 📋 全体アーキテクチャ

このアプリケーションはレイヤードアーキテクチャを採用しており、以下の層で構成されています：


# 1️⃣ クライアント層（Client Layer

Browser: HTTPを通じてWebMVC層と通信

ユーザーインターフェースを提供


# 2️⃣ WebMVC層（Web/MVCレイヤー）

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


# 3️⃣ サービス層（Service Layer）

ビジネスロジックを実装：

UserService: ユーザー関連のCRUD操作

CustomerUserDetailsService: Spring Security用のユーザー詳細取得

BlogService: ブログ記事のCRUD操作


# 4️⃣ プレゼンテーション層（Presentation Layer）

テンプレートエンジンを使用：

Admin Templates: 管理画面用テンプレート

Thymeleaf Templates: 一般ユーザー画面用

static/images: 画像ファイル

static/css: スタイルシート


# 5️⃣ データアクセス層（Data Access Layer）

リポジトリパターンを採用：

UserRepository: ユーザーデータアクセス

PostRepository: 投稿データアクセス

BlogRepository: ブログデータアクセス

JDBCまたはJPAを使用してH2データベースと接続



# 6️⃣ 永続化層（Persistence）

DataLoader: 初期データの投入

Flyway Migrations: データベーススキーマのバージョン管理

H2 Database: インメモリまたはファイルベースのデータベース


# 7️⃣ 設定・起動層（Configuration & Boot）

RootTemplateConfig: テンプレート設定

BlogApplication: Spring Bootのメインクラス

application.properties: 各種設定

build.gradle: 依存関係管理


# 8️⃣ 外部サービス層（External Services）

RootTemplateExample: 外部テンプレートの例

External REST API: 外部APIとの連携


⚠️ 問題点と改善提案

🔴 深刻な問題点


# 1. セキュリティ層の配置が不明瞭

Spring Security Filter Chainが図の右側に独立して配置されていますが、実際には全てのコントローラーの前段で動作すべきです

改善: セキュリティフィルターをWebMVC層の入口に明確に配置


# 2. サービス層の責務が不明確

CustomerUserDetailsServiceがUserServiceと並列に配置されていますが、これは認証専用サービスです

改善: 認証関連サービスと業務ロジックサービスを明確に分離


# 3. リポジトリとデータベースの接続方法が混在

JDBC、JDBCの表記が複数あり、JPAなのかJDBCなのか不明確

改善: データアクセス技術を統一（推奨：Spring Data JPA）

🟡 中程度の問題点


# 4. AdminControllerとAuthControllerの責務重複

管理者認証がどちらで処理されるか不明確

改善: AuthControllerを一般認証専用、AdminControllerを管理機能専用に明確化


# 5. テンプレート層の構造が複雑

Admin TemplatesとThymeleaf Templatesが分離されていますが、両方Thymeleafを使用している可能性

改善: テンプレートを機能別（public/admin）で整理


# 6. 外部サービスとの統合ポイントが不明確

External REST APIがどのように使用されるか、どこから呼ばれるか不明

改善: サービス層に外部API呼び出し専用のクラスを追加（例：ExternalBlogApiClient）

🟢 軽微な改善提案


# 7. エラー処理の一元化

ErrorControllerだけでなく、グローバル例外ハンドラーの追加を推奨

追加: @ControllerAdviceを使用した統一的な例外処理


# 8. キャッシュ層の欠如

頻繁にアクセスされるブログ記事などにキャッシュがない

追加: Spring CacheまたはRedisの導入


# 9. DTO層の欠如

エンティティを直接コントローラーで使用している可能性

追加: DTO（Data Transfer Object）層を追加してエンティティを保護


# 10. ログ・監視機能が不明

ログ管理やアプリケーション監視の仕組みが見えない

追加: SLF4J + Logback、Spring Boot Actuatorの導入

✅ 優れている点

レイヤーの分離: 各層が明確に分離されている

Spring Securityの導入: セキュリティを考慮した設計

データベースマイグレーション: Flywayによるスキーマ管理

初期データ投入: DataLoaderによる開発環境の整備

RESTful設計: コントローラーが適切に機能分割されている
