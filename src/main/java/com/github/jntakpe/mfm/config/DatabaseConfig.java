package com.github.jntakpe.mfm.config;

import com.mongodb.Mongo;
import org.mongeez.Mongeez;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Configuration de la database MongoDB
 *
 * @author jntakpe
 */
@Configuration
@EnableMongoAuditing
@Import(MongoAutoConfiguration.class)
@EnableMongoRepositories("com.github.jntakpe.mfm.repository")
public class DatabaseConfig extends AbstractMongoConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseConfig.class);

    public static final String MONGEEZ_PATH = "/mongeez/master.xml";

    @Autowired
    private Mongo mongo;

    @Autowired
    private MongoProperties mongoProperties;

    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener() {
        return new ValidatingMongoEventListener(validator());
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public Mongeez mongeez() {
        LOG.debug("Configuration de Mongeez");
        Mongeez mongeez = new Mongeez();
        mongeez.setFile(new ClassPathResource(MONGEEZ_PATH));
        mongeez.setMongo(mongo);
        mongeez.setDbName(mongoProperties.getDatabase());
        mongeez.process();
        return mongeez;
    }

    @Override
    protected String getDatabaseName() {
        return mongoProperties.getDatabase();
    }

    @Override
    public Mongo mongo() throws Exception {
        return mongo;
    }
}
