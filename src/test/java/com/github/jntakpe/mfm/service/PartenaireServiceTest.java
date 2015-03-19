package com.github.jntakpe.mfm.service;

import com.github.jntakpe.mfm.MfmApp;
import com.github.jntakpe.mfm.domain.Partner;
import com.github.jntakpe.mfm.domain.Status;
import com.github.jntakpe.mfm.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

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
    private PartnerRepository partnerRepository;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    private Integer initCount;

    @BeforeClass
    public void setUp() throws Exception {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @BeforeMethod
    public void beforeMethod() {
        initCount = count();
        assertThat(initCount).isNotZero();
    }

    private Integer count() {
        return jdbcTemplate.queryForObject("select count(*) from partner", Integer.class);
    }

    @Test
    public void testSave_shouldCreate() {
        Partner partner = new Partner();
        partner.setStatus(Status.UP);
        partner.setName("Some partner");
        partner.setUrl("http://www.mymonitoring.url");
        partner.setApplications(new HashSet<>(applicationService.findAll()));
        Partner saved = partnerService.save(partner);
        assertThat(saved).isNotNull();
        assertThat(count()).isEqualTo(initCount + 1);
    }

    @Test
    public void testSave_shouldUpdate() {
        Partner partner = partnerRepository.findOne(1L);
        Partner saved = partnerService.save(partner);
        assertThat(partner.getLock()).isEqualTo(saved.getLock());
    }
}
