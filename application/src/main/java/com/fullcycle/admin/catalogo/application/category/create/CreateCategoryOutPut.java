package com.fullcycle.admin.catalogo.application.category.create;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;

public record CreateCategoryOutPut(CategoryID id) {

    public static CreateCategoryOutPut from(final Category category) {
        return new CreateCategoryOutPut(category.getId());
    }

}
