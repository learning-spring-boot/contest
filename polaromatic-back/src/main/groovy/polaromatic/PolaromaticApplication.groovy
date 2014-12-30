package polaromatic

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ImportResource

@SpringBootApplication
@ImportResource("classpath:resources.xml")
class PolaromaticApplication {

    static void main(String[] args) {
        SpringApplication.run PolaromaticApplication, args
    }

}