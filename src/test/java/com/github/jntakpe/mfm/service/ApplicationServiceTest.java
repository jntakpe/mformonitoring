package com.github.jntakpe.mfm.service;

import com.github.jntakpe.mfm.MfmApp;
import com.github.jntakpe.mfm.domain.Application;
import com.github.jntakpe.mfm.repository.ApplicationRepository;
import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Tests de {@link ApplicationService}
 *
 * @author jntakpe
 */
@IntegrationTest
@SpringApplicationConfiguration(classes = MfmApp.class)
public class ApplicationServiceTest extends AbstractTestNGSpringContextTests {

    public static final String URL = "https://fra.herokuapp.com/rest/manage/health";

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private Mongo mongo;

    @Autowired
    private MongoProperties mongoProperties;

    private MongoOperations mongoOperations;

    private Long initCount;

    private String appId;

    @BeforeClass
    public void setUp() throws Exception {
        mongoOperations = new MongoTemplate(mongo, mongoProperties.getDatabase());
    }

    @BeforeMethod
    public void beforeMethod() {
        mongoOperations.dropCollection(Application.class);
        mongoOperations.createCollection(Application.class);
        mongoOperations.indexOps(Application.class).ensureIndex(new Index().unique().on("url", Sort.Direction.ASC));
        mongoOperations.insert(data(), Application.class);
        appId = mongoOperations.find(Query.query(where("artifactId").is("eers")), Application.class).get(0).getId();
        initCount = count();
        assertThat(initCount).isNotZero();
    }

    private List<Application> data() {
        List<Application> apps = new ArrayList<>();
        Application eers = new Application();
        eers.setName("Entrée en relation");
        eers.setUrl("https://fra.herokuapp.com/rest/manage/health");
        eers.setEnvironment("DEVELOPPEMENT");
        eers.setGroupId("com.bforbank");
        eers.setArtifactId("eers");
        eers.setVersion("0.0.1-SNAPSHOT");
        Application ec = new Application();
        ec.setName("Espace client");
        ec.setUrl("https://fra.herokuapp.com/rest/manage/health3");
        ec.setEnvironment("DEVELOPPEMENT");
        ec.setGroupId("com.bforbank");
        ec.setArtifactId("ec");
        ec.setVersion("0.0.1-SNAPSHOT");
        apps.add(eers);
        apps.add(ec);
        return apps;
    }

    private Long count() {
        return mongoOperations.count(new BasicQuery("{}"), Application.class);
    }

    @Test
    public void testFindAll_shouldFindAtLeastOne() {
        assertThat(applicationService.findAll()).isNotEmpty().hasSize(initCount.intValue());
    }

    @Test
    public void testFindByUrl_shouldFind() {
        Optional<Application> opt = applicationService.findByUrl(URL);
        assertThat(opt.isPresent()).isTrue();
        assertThat(mongoOperations.find(Query.query(where("url").is(URL)), Application.class).get(0)).isEqualTo(opt.get());
    }

    @Test
    public void testFindByUrl_shouldNotFind() {
        assertThat(applicationService.findByUrl("http://someunknownurl.io").isPresent()).isFalse();
    }

    @Test
    public void testSave_shouldCreate() {
        Application application = new Application();
        String url = "http://randomurl.io";
        application.setUrl(url);
        application.setName("testApp");
        Application saved = applicationService.save(application);
        assertThat(saved).isNotNull();
        assertThat(count()).isEqualTo(initCount + 1);
        assertThat(mongoOperations.findById(saved.getId(), Application.class)).isNotNull();
    }

    @Test
    public void testSave_shouldEdit() {
        Application application = applicationRepository.findOne(appId);
        assertThat(application).isNotNull();
        String testAppName = "testAppName";
        application.setName(testAppName);
        Application saved = applicationService.save(application);
        assertThat(saved.getName()).isEqualTo(testAppName);
        assertThat(mongoOperations.find(Query.query(where("name").is(testAppName)), Application.class)).isNotNull().isNotEmpty();
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void testSave_shouldConstraintViolationFail() {
        Application application = new Application();
        application.setUrl(mongoOperations.findById(appId, Application.class).getUrl());
        application.setName("APP");
        applicationService.save(application);
    }

    @Test
    public void testDelete_shoudDelete() {
        applicationService.delete(appId);
        assertThat(count()).isEqualTo(initCount - 1);
        assertThat(applicationRepository.findOne(appId)).isNull();
        assertThat(mongoOperations.findById(appId, Application.class)).isNull();
    }

    @Test
    public void findById_shouldFind() {
        List<Application> applications = mongoOperations.find(Query.query(where("artifactId").is("eers")), Application.class);
        assertThat(applications).isNotEmpty().hasSize(1);
        Application app = applicationService.findById(appId);
        assertThat(app).isNotNull();
    }

    @Test
    public void findById_shouldNotFind() {
        assertThat(applicationService.findById("someunknownid")).isNull();
    }

}
