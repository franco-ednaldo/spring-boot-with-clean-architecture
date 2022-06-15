package com.fullcycle.admin.catalogo.infrastructure.category;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.infrastructure.GatewayTest;
import com.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryEntity;
import com.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@GatewayTest
class CategoryGatewayTest {

    @Autowired
    private CategoryGateway categoryGateway;

    @Autowired
    private CategoryRepository repository;


    @Test
    void givenAValidCategory_whenCallsCreate_should_ReturnANewCategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        assertEquals(0, repository.count());

        final var actualCategory = this.categoryGateway.create(aCategory);
        assertNotNull(actualCategory);
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.getId());
    }

    @Test
    void givenAValidCategory_whenCallsUpdate_should_ReturnANewCategoryUpdated() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var aCategory = Category.newCategory("filme", "A ctegoria mais assistida", true);

        assertEquals(0, repository.count());
        this.repository.saveAndFlush(CategoryEntity.from(aCategory));

        final Category aUpdatedCategory = aCategory.clone().update(expectedName, expectedDescription, expectedIsActive);
        final var actualCategory = this.categoryGateway.update(aUpdatedCategory);

        assertNotNull(actualCategory);
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.getId());
    }

}
