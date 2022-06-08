package com.fullcycle.admin.catalogo.application.category.retrieve.list;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;

import java.time.Instant;

public record CategoryListOutPut(
    CategoryID id,
    String name,
    String description,
    boolean isActive,
    Instant createAt,
    Instant deleteAt
) {

    public static CategoryListOutPut from(Category aCategory) {
        return new CategoryListOutPut(
            aCategory.getId(),
            aCategory.getName(),
            aCategory.getDescription(),
            aCategory.isActive(),
            aCategory.getCreatedAt(),
            aCategory.getDeletedAt());
    }

}
