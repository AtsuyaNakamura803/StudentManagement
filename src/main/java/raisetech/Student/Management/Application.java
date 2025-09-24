package raisetech.Student.Management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

/**
 * StudentManagementアプリケーションの起動クラス。
 *
 * <p>
 * Spring Boot アプリケーションとして起動し、MyBatis の XML マッピングで
 * 定義された Mapper を自動スキャンする。
 * </p>
 *
 * <p>
 * Mapper のスキャン対象パッケージ: {@code raisetech.Student.Management.repository}
 * </p>
 */
@SpringBootApplication
@MapperScan("raisetech.Student.Management.repository") // MyBatis Mapperのスキャン
public class Application {

    /**
     * アプリケーションのエントリーポイント。
     *
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}