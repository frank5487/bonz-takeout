package en.upenn.bonz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@ServletComponentScan // to activate servlet filter
@EnableTransactionManagement // activate transaction
@EnableCaching // activate cache function
public class BonzApplication {

    public static void main(String[] args) {
        SpringApplication.run(BonzApplication.class, args);
        log.info("environment has set up...");
    }
}
