package com.evbx.product.data;

import com.evbx.product.model.data.ItemData;
import com.evbx.product.model.domain.ProductModel;
import com.evbx.product.model.dto.ProductModelDto;
import com.evbx.product.model.dto.resource.Book;
import com.evbx.product.model.dto.resource.IndustryReport;
import com.evbx.product.model.dto.resource.ResourcesDto;
import com.evbx.product.model.dto.resource.Specification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public final class ProductModelTestDataStorage {

    private ProductModelTestDataStorage() {
    }

    public static ItemData<ProductModelDto> getProductModelItemData() {
        return new ItemData<>(getProductModelsDto());
    }

    public static List<ProductModelDto> getProductModelsDto() {
        List<ProductModelDto> productModelsDto = new ArrayList<>();

        ProductModel productModel = getProductModels().get(0);
        ProductModelDto firstProductModelDto = new ProductModelDto().setId(productModel.getId()).setModelName(
                productModel.getModelName()).setDescriptions(productModel.getDescriptions()).setResourcesDto(
                getResourcesDto());
        productModelsDto.add(firstProductModelDto);
        productModelsDto.add(firstProductModelDto);
        productModelsDto.add(firstProductModelDto);

        return productModelsDto;
    }

    public static List<ProductModel> getProductModels() {
        List<ProductModel> productModels = new ArrayList<>();

        ProductModel firstProductModel = new ProductModel();
        firstProductModel.setId(100L);
        firstProductModel.setProductId(100L).setModelName("Product-model-0").setBookId(1L).setIndustryReportId(1L)
                .setSpecificationReportId(1L).setDescriptions(
                Collections.singletonList(DescriptionTestDataStorage.getTestDescriptions().get(0))).setCreatedBy(
                "script-0");
        productModels.add(firstProductModel);

        ProductModel secondProductModel = new ProductModel();
        secondProductModel.setId(101L);
        secondProductModel.setProductId(101L).setModelName("Product-model-1").setBookId(2L).setIndustryReportId(2L)
                .setSpecificationReportId(2L).setDescriptions(
                Collections.singletonList(DescriptionTestDataStorage.getTestDescriptions().get(1)));
        secondProductModel.setCreatedBy("script-1");
        productModels.add(secondProductModel);

        ProductModel thirdProductModel = new ProductModel();
        thirdProductModel.setId(102L);
        thirdProductModel.setProductId(102L).setModelName("Product-model-2").setBookId(3L).setIndustryReportId(3L)
                .setSpecificationReportId(3L).setDescriptions(
                Collections.singletonList(DescriptionTestDataStorage.getTestDescriptions().get(2))).setCreatedBy(
                "script-2");
        productModels.add(thirdProductModel);
        return productModels;
    }

    public static ProductModel getMutationProductModel() {
        ProductModel productModel = new ProductModel();
        productModel.setProductId(100L).setModelName("Product-model-777").setBookId(777L).setIndustryReportId(777L)
                .setSpecificationReportId(777L).setDescriptions(
                Collections.singletonList(DescriptionTestDataStorage.getTestDescriptions().get(0))).setCreatedAt(
                Calendar.getInstance().getTime());
        return productModel;
    }

    private static ResourcesDto getResourcesDto() {
        return new ResourcesDto().
                setBook(getBook()).setIndustryReport(getIndustryReport()).setSpecification(getSpec());
    }

    private static Book getBook() {
        Book mutationBook = new Book().setBookName("Book-name-999");
        mutationBook.setDescription("Book-description-999").setText("Book-text-999");
        return mutationBook;
    }

    private static IndustryReport getIndustryReport() {
        IndustryReport report = new IndustryReport().setIndustryName("Industry-name-888");
        report.setDescription("Industry-description-888").setText("Industry-text-888");
        return report;
    }

    private static Specification getSpec() {
        Specification mutationSpec = new Specification().setSpecificationName("Specification-name-777");
        mutationSpec.setDescription("Specification-description-777").setText("Specification-text-777");
        return mutationSpec;
    }
}