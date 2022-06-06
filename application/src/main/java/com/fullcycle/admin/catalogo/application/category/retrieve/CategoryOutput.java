package com.fullcycle.admin.catalogo.application.category.retrieve;

import com.fullcycle.admin.catalogo.domain.category.Category;

import java.time.Instant;

public record CategoryOutput(
    String id,
    String aName,
    String aDescription,
    boolean isActive,
    Instant createdAt,
    Instant updatedAt,
    Instant deletedAt) {

    public static CategoryOutput from(final Category aCategory) {
        return new CategoryOutput(
            aCategory.getId().getValue(),
            aCategory.getName(),
            aCategory.getDescription(),
            aCategory.isActive(),
            aCategory.getCreatedAt(),
            aCategory.getUpdatedAt(),
            aCategory.getDeletedAt()
        );
    }

}
