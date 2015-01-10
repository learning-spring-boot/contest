package com.maciejwalkowiak.mercury.mail;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ConditionalOnBean(MailProperties.class)
@ComponentScan
class MailConfiguration {
}
