package MarketProject.backend;

import MarketProject.backend.entity.Comment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories

public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);


        System.out.println();
    }
}
