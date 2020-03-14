package com.evbx.product.test.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ComponentScan("com.evbx.product")
@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
@SpringBootTest
@Sql(scripts = { "classpath:script/clean.sql", "classpath:script/data.sql" })
abstract class BaseControllerTest {

}
