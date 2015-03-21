package com.github.jntakpe.mfm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Configuration de la database MongoDB
 *
 * @author jntakpe
 */
@Configuration
@EnableMongoAuditing
@EnableMongoRepositories("com.github.jntakpe.mfm.repository")
public class DatabaseConfig {


}
