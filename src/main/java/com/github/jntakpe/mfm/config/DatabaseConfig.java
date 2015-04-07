package com.github.jntakpe.mfm.config;

import com.github.jntakpe.mfm.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.annotation.PostConstruct;

/**
 * Configuration de la database MongoDB
 *
 * @author jntakpe
 */
@Configuration
@AutoConfigureAfter(MongoAutoConfiguration.class)
@EnableMongoAuditing
@EnableMongoRepositories("com.github.jntakpe.mfm.repository")
public class DatabaseConfig {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseConfig.class);

    @Autowired
    private MongoOperations mongoOperations;

    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener() {
        return new ValidatingMongoEventListener(validator());
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @PostConstruct
    public void initDatabase() {
        LOG.warn("Initialisation de MongoDB avec des données de développement");
        mongoOperations.dropCollection(Application.class);
        mongoOperations.createCollection(Application.class);
        mongoOperations.dropCollection(Partner.class);
        mongoOperations.createCollection(Partner.class);
        mongoOperations.dropCollection(Notification.class);
        mongoOperations.createCollection(Notification.class);
//        Application eersDev = new Application();
//        eersDev.setName("Entrée en relation");
//        eersDev.setUrl("https://fra.herokuapp.com/rest/dev/eers/manage/info");
//        eersDev.setEnvironment(Environment.DEVELOPPEMENT);
//        eersDev.setArtifactId("eers");
//        eersDev.setVersion("0.0.1-SNAPSHOT");
//        eersDev.setStatus(Status.UP);
//        mongoOperations.insert(eersDev);
//        Application eersAssemb = new Application();
//        eersAssemb.setName("Entrée en relation");
//        eersAssemb.setUrl("https://fra.herokuapp.com/rest/assembl/eers/manage/info");
//        eersAssemb.setEnvironment(Environment.ASSEMBLAGE);
//        eersAssemb.setArtifactId("eers");
//        eersAssemb.setVersion("0.0.1-SNAPSHOT");
//        eersAssemb.setStatus(Status.UP);
//        mongoOperations.insert(eersAssemb);
//        Application ec = new Application();
//        ec.setName("Espace client");
//        ec.setUrl("https://fra.herokuapp.com/rest/dev/ec/manage/info");
//        ec.setEnvironment(Environment.DEVELOPPEMENT);
//        ec.setArtifactId("ec");
//        ec.setVersion("0.0.1-SNAPSHOT");
//        ec.setStatus(Status.UP);
//        mongoOperations.insert(ec);
//        Application bss = new Application();
//        bss.setName("BSS");
//        bss.setUrl("https://fra.herokuapp.com/rest/dev/bss/manage/info");
//        bss.setEnvironment(Environment.DEVELOPPEMENT);
//        bss.setArtifactId("bss");
//        bss.setVersion("0.0.1-SNAPSHOT");
//        bss.setStatus(Status.UP);
//        mongoOperations.insert(bss);
    }
}
