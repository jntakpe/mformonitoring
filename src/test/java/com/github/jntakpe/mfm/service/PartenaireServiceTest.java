package com.github.jntakpe.mfm.service;

import com.github.jntakpe.mfm.MfmApp;
import com.github.jntakpe.mfm.domain.Application;
import com.github.jntakpe.mfm.domain.Partner;
import com.github.jntakpe.mfm.domain.Status;
import com.github.jntakpe.mfm.repository.PartnerRepository;
import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Tests associés à l'entité {@link Partner}
 *
 * @author jntakpe
 */
@IntegrationTest
@SpringApplicationConfiguration(classes = MfmApp.class)
public class PartenaireServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private Mongo mongo;

    @Autowired
    private MongoProperties mongoProperties;

    private MongoOperations mongoOperations;

    private Long initCount;

    private String partnerId;

    @BeforeClass
    public void setUp() throws Exception {
        mongoOperations = new MongoTemplate(mongo, mongoProperties.getDatabase());
        mongoOperations.dropCollection(Application.class);
        mongoOperations.createCollection(Application.class);
    }

    @BeforeMethod
    public void beforeMethod() {
        mongoOperations.dropCollection(Partner.class);
        mongoOperations.createCollection(Partner.class);
        mongoOperations.insert(data(mongoOperations.findAll(Application.class)), Partner.class);
        partnerId = mongoOperations.find(Query.query(where("name").is("selClient")), Partner.class).get(0).getId();
        initCount = count();
        assertThat(initCount).isNotZero();
    }

    public static Set<Partner> data(List<Application> applications) {
        Set<Partner> partners = new HashSet<>();
        Partner selClient = new Partner();
        selClient.setName("selClient");
        selClient.setUrl("jdbc:oracle:thin:@10.199.160.20:49404/bnkdev");
        selClient.setStatus("UP");
        selClient.setApplications(new HashSet<>(applications));
        partners.add(selClient);
        Partner selCrm = new Partner();
        selCrm.setName("selCrm");
        selCrm.setUrl("jdbc:oracle:thin:@10.199.160.20:49405/bnkdev");
        selCrm.setStatus("DOWN");
        selCrm.setApplications(new HashSet<>(applications));
        partners.add(selCrm);
        return partners;
    }

    private Long count() {
        return mongoOperations.count(new BasicQuery("{}"), Partner.class);
    }

    @Test
    public void testSave_shouldCreate() {
        Partner partner = new Partner();
        partner.setStatus("DOWN");
        partner.setName("Some partner");
        partner.setUrl("http://www.mymonitoring.url");
        Partner saved = partnerService.save(partner);
        assertThat(saved).isNotNull();
        assertThat(count()).isEqualTo(initCount + 1);
    }

}
