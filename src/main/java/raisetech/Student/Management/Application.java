package raisetech.Student.Management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

/**
 * StudentManagement アプリケーション起動クラス。
 *
 * <p>Spring Boot アプリケーションとして起動し、MyBatis の XML Mapper をスキャンします。</p>
 *
 * <p>Mapper スキャン対象パッケージ: {@code raisetech.Student.Management.repository}</p>
 */
@SpringBootApplication
@MapperScan("raisetech.Student.Management.repository")
public class Application {

    /**
     * アプリケーションのエントリポイント。
     *
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}