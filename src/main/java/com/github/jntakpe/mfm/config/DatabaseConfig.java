package com.github.jntakpe.mfm.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuration de la database MongoDB
 *
 * @author jntakpe
 */
@Configuration
@AutoConfigureAfter(MongoAutoConfiguration.class)
@EnableJpaAuditing
@EnableJpaRepositories("com.github.jntakpe.mfm.repository")
public class DatabaseConfig {

}
