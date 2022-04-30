package en.upenn.bonz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class BonzApplication {

    public static void main(String[] args) {
        SpringApplication.run(BonzApplication.class, args);
        log.info("environment has set up...");
    }
}
