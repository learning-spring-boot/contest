package polaromatic

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ImportResource
import org.springframework.web.filter.CharacterEncodingFilter

import javax.servlet.Filter

@SpringBootApplication
@ImportResource("classpath:resources.xml")
class PolaromaticApplication {

    static void main(String[] args) {
        SpringApplication.run PolaromaticApplication, args
    }

    @Bean
    public Filter characterEncodingFilter() {
        def characterEncodingFilter = new CharacterEncodingFilter()
        characterEncodingFilter.encoding = "UTF-8"
        characterEncodingFilter.forceEncoding = true

        return characterEncodingFilter
    }
}