package org.side.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

@SpringBootApplication(scanBasePackages = {"me.ramswaroop.jbot", "slackBot"})
@LineMessageHandler
@EnableConfigurationProperties
@EntityScan(basePackages = { "org.side.entities" })
@ComponentScan(basePackages = { "org.side.controller", "org.side.service" })
@ComponentScan(basePackages = { "slackBot" })
@EnableJpaRepositories(basePackages = { "org.side.dao" })

public class HrBotApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(HrBotApplication.class, args);
	}
}
