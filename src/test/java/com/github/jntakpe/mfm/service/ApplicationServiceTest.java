package com.github.jntakpe.mfm.service;

import com.github.jntakpe.mfm.MfmApp;
import com.github.jntakpe.mfm.domain.Application;
import com.github.jntakpe.mfm.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

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
    private DataSource dataSource;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApplicationRepository applicationRepository;

    private JdbcTemplate jdbcTemplate;

    private Integer initCount;

    @BeforeClass
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @BeforeMethod
    public void beforeMethod() {
        initCount = count();
        assertThat(initCount).isNotZero();
    }

    private Integer count() {
        return jdbcTemplate.queryForObject("select count(*) from application", Integer.class);
    }

    @Test
    public void testFindAll_shouldFindAtLeastOne() {
        assertThat(applicationService.findAll()).isNotEmpty().hasSize(initCount);
    }

    @Test
    public void testFindByUrl_shouldFind() {
        assertThat(applicationService.findByUrl(URL).isPresent()).isTrue();
    }

    @Test
    public void testFindByUrl_shouldNotFind() {
        assertThat(applicationService.findByUrl("http://someunknownurl.io").isPresent()).isFalse();
    }

    @Test
    public void testSave_shouldCreate() {
        Application application = new Application();
        application.setUrl("http://randomurl.io");
        application.setName("testApp");
        assertThat(applicationService.save(application)).isNotNull();
        assertThat(initCount).isEqualTo(count() - 1);
    }

    @Test
    public void testSave_shouldEdit() {
        Application application = applicationRepository.findOne(1L);
        assertThat(application).isNotNull();
        String testAppName = "testAppName";
        Integer lock = application.getLock();
        application.setName(testAppName);
        Application saved = applicationService.save(application);
        assertThat(saved.getName()).isEqualTo(testAppName);
        assertThat(saved.getLock()).isEqualTo(lock + 1);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void testSave_shouldConstraintViolationFail() {
        Application application = new Application();
        application.setUrl(URL);
        applicationService.save(application);
    }

    @Test
    public void testDelete_shoudDelete() {
        applicationService.delete(4L);
        assertThat(count()).isEqualTo(initCount - 1);
        assertThat(applicationRepository.findOne(4L)).isNull();
    }

}
