package com.fullcycle.admin.catalogo.application.category.retrieve.list;

import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategorySearchQuery;
import com.fullcycle.admin.catalogo.domain.pagination.Pagination;

public class DefaultListCategoryUseCase extends ListCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultListCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public Pagination<CategoryListOutPut> execute(final CategorySearchQuery aQuery) {
        return this.categoryGateway.findAll(aQuery)
            .map(CategoryListOutPut::from);
    }

}
