package com.evbx.product.layer.service.impl;

import com.evbx.product.apiclient.ApiClient;
import com.evbx.product.config.ServiceConfig;
import com.evbx.product.constant.Item;
import com.evbx.product.constant.ResourceEndpoint;
import com.evbx.product.layer.service.ApiClientService;
import com.evbx.product.model.domain.ProductModel;
import com.evbx.product.model.dto.ProductModelDto;
import com.evbx.product.model.dto.resource.Book;
import com.evbx.product.model.dto.resource.IndustryReport;
import com.evbx.product.model.dto.resource.Specification;
import com.evbx.product.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
public class ApiClientServiceImpl implements ApiClientService {

    private ApiClient apiClient;
    private ServiceConfig serviceConfig;

    @Autowired
    public ApiClientServiceImpl(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }

    @Override
    public ProductModelDto composeProductModelDto(ProductModel productModel, ProductModelDto productModelDto) {
        String bookPath = ResourceEndpoint.BOOK_ENDPOINT + productModel.getBookId();
        String industryReportPath = ResourceEndpoint.INDUSTRY_REPORT_ENDPOINT + productModel.getIndustryReportId();
        String specificationPath = ResourceEndpoint.SPECIFICATION_ENDPOINT + productModel.getSpecificationReportId();

        CompletableFuture<Book> bookFuture = composeFutureTask(bookPath, Book.class);
        CompletableFuture<IndustryReport> reportFuture = composeFutureTask(industryReportPath, IndustryReport.class);
        CompletableFuture<Specification> specFuture = composeFutureTask(specificationPath, Specification.class);

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(bookFuture, reportFuture, specFuture);
        combinedFuture.join();

        productModelDto.getResourcesDto().setBook(bookFuture.join());
        productModelDto.getResourcesDto().setIndustryReport(reportFuture.join());
        productModelDto.getResourcesDto().setSpecification(specFuture.join());
        return productModelDto;
    }

    private <T> CompletableFuture<T> composeFutureTask(String bookPath, Class<T> classOfT) {
        return CompletableFuture.supplyAsync(() -> getResource(bookPath, classOfT));
    }

    @Override
    public void verifyResourceIds(ProductModel productModel) {
        validateResources(getApiClient(), productModel);
    }

    private void validateResources(ApiClient apiClient, ProductModel productModel) {
        if (Objects.nonNull(productModel.getBookId())) {
            List<Long> bookIds = getResourceIds(apiClient, ResourceEndpoint.BOOK_ENDPOINT_IDS);
            ValidationUtil.validateItemId(Item.E_BOOK, bookIds, productModel.getBookId());
        }
        if (Objects.nonNull(productModel.getIndustryReportId())) {
            List<Long> reportIds = getResourceIds(apiClient, ResourceEndpoint.INDUSTRY_REPORT_ENDPOINT_IDS);
            ValidationUtil.validateItemId(Item.INDUSTRY_REPORT, reportIds, productModel.getIndustryReportId());
        }
        if (Objects.nonNull(productModel.getSpecificationReportId())) {
            List<Long> specIds = getResourceIds(apiClient, ResourceEndpoint.SPECIFICATION_ENDPOINT_IDS);
            ValidationUtil.validateItemId(Item.SPECIFICATION, specIds, productModel.getSpecificationReportId());
        }
    }

    private List<Long> getResourceIds(ApiClient apiClient, String resourcePath) {
        Long[] ids = apiClient.get(resourcePath, Long[].class);
        return Arrays.asList(ids);
    }

    private ApiClient getApiClient() {
        if (Objects.isNull(apiClient)) {
            apiClient = new ApiClient(serviceConfig);
        }
        return apiClient;
    }

    private <T> T getResource(String path, Class<T> classOfT) {
        return getApiClient().get(path, classOfT);
    }
}
