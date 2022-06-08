package com.fullcycle.admin.catalogo.application.category.retrieve.get;

import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.Error;

import java.util.function.Supplier;

public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultGetCategoryByIdUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public CategoryOutput execute(final String anIn) {
        final var categoryId = CategoryID.from(anIn);
        return this.categoryGateway.findById(categoryId)
            .map(CategoryOutput::from)
            .orElseThrow(notfound(categoryId));
    }

    private Supplier<DomainException> notfound(final CategoryID id) {
        return () -> DomainException.with(new Error("Category with ID %s was not found".formatted(id.getValue())));
    }

}
