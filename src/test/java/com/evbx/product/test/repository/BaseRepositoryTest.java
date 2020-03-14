package com.evbx.product.test.repository;

import com.evbx.product.support.JpaVerificationService;
import com.evbx.product.support.ProjectionVerificationService;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@ComponentScan("com.evbx.product")
@Sql(scripts = { "classpath:script/clean.sql", "classpath:script/data.sql" })
public abstract class BaseRepositoryTest {

    JpaVerificationService jpaVerificationService = new JpaVerificationService();
    ProjectionVerificationService projectionVerificationService = new ProjectionVerificationService();
}
