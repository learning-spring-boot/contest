package com.maciejwalkowiak.mercury.mail.javamail;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@ConditionalOnProperty(name = "spring.mail.host")
class JavaMailConfiguration {
}
