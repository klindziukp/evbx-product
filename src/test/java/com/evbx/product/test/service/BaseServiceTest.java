package com.evbx.product.test.service;

import com.evbx.product.data.ProductModelTestDataStorage;
import com.evbx.product.layer.service.ApiClientService;
import com.evbx.product.model.domain.ProductModel;
import com.evbx.product.model.dto.ProductModelDto;
import com.evbx.product.support.JpaVerificationService;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@ComponentScan("com.evbx.product")
@Sql(scripts = { "classpath:script/clean.sql", "classpath:script/data.sql" })
public abstract class BaseServiceTest {

    JpaVerificationService jpaVerificationService = new JpaVerificationService();

    ApiClientService getMockApiClientService() {
        ApiClientService apiClientService = Mockito.mock(ApiClientService.class);
        ProductModelDto productModeldto = ProductModelTestDataStorage.getProductModelsDto().get(0);
        Mockito.when(apiClientService.composeProductModelDto(ArgumentMatchers.any(ProductModel.class),
                ArgumentMatchers.any(ProductModelDto.class))).thenReturn(productModeldto);
        Mockito.doNothing().when(apiClientService).verifyResourceIds(ArgumentMatchers.any(ProductModel.class));
        return apiClientService;
    }

    void mockSecurityContext() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("script");
        SecurityContextHolder.setContext(securityContext);
    }
}
