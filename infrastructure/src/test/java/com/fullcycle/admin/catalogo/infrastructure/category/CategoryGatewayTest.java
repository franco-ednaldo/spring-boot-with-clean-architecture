package com.fullcycle.admin.catalogo.infrastructure.category;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.domain.category.CategorySearchQuery;
import com.fullcycle.admin.catalogo.infrastructure.GatewayTest;
import com.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryEntity;
import com.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void givenAPrePersistedCategoryAndValidCategoryId_whenTryToDeleteIt_shouldDeleteCategory() {
        final var aCategory = Category.newCategory("Movies", "A category the most see", true);
        assertEquals(0, repository.count());
        final var aSavedCategory = this.repository.saveAndFlush(CategoryEntity.from(aCategory));
        assertEquals(1, repository.count());

        this.categoryGateway.deleteById(CategoryID.from(aSavedCategory.getId()));
        assertEquals(0, repository.count());
    }

    @Test
    void givenAPrePersistedCategoryAndInValidCategoryId_whenTryToDeleteIt_shouldDeleteCategory() {
        assertEquals(0, repository.count());
        this.categoryGateway.deleteById(CategoryID.from("invalid"));
        assertEquals(0, repository.count());
    }

    @Test
    void givenPrePersistedValidCategory_whenCallsFindById_should_ReturnACategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var aCategory = Category.newCategory(expectedName, expectedDescription, true);
        final var expectedId = aCategory.getId();

        assertEquals(0, repository.count());
        this.repository.saveAndFlush(CategoryEntity.from(aCategory));

        final var actualCategory = this.categoryGateway.findById(expectedId).get();

        assertNotNull(actualCategory);
        assertEquals(expectedId.getValue(), actualCategory.getId().getValue());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.getId());
    }

    @Test
    void givenPrePersistedInvalidCategory_whenCallsFindById_should_ReturnEmpty() {
        final var actualCategory = this.categoryGateway.findById(CategoryID.from("empty"));
        assertTrue(actualCategory.isEmpty());
    }

    @Test
    public void givenPrePersistedCategories_whenCallsFindAll_shouldReturnPaginated() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 3;

        final var movies = Category.newCategory("Movies", null, true);
        final var series = Category.newCategory("Series", null, true);
        final var documentary = Category.newCategory("Documentary", null, true);
        assertEquals(0, repository.count());

        this.repository.saveAll(List.of(
            CategoryEntity.from(movies),
            CategoryEntity.from(series),
            CategoryEntity.from(documentary)));

        assertEquals(3, repository.count());

        final var categorySearch = new CategorySearchQuery(0, 1, "", "name", "asc");
        final var actualResult = this.categoryGateway.findAll(categorySearch);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedPage, actualResult.items().size());
        assertEquals(documentary.getId(), actualResult.items().get(0).getId());
    }

    @Test
    public void givenEmptyCategories_whenCallsFindAll_shouldReturnEmptyPage() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 0;

        final var categorySearch = new CategorySearchQuery(0, 1, "", "name", "asc");
        final var actualResult = this.categoryGateway.findAll(categorySearch);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedPage, actualResult.items().size());
    }

    @Test
    public void givenFollowPagination_whenCallsFindAll_shouldReturnPaginated() {
        var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 3;

        final var movies = Category.newCategory("Movies", null, true);
        final var series = Category.newCategory("Series", null, true);
        final var documentary = Category.newCategory("Documentary", null, true);
        assertEquals(0, repository.count());

        this.repository.saveAll(List.of(
            CategoryEntity.from(movies),
            CategoryEntity.from(series),
            CategoryEntity.from(documentary)));

        assertEquals(3, repository.count());

        // page 0
        var categorySearch = new CategorySearchQuery(0, 1, "", "name", "asc");
        var actualResult = this.categoryGateway.findAll(categorySearch);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedPage, actualResult.items().size());
        assertEquals(documentary.getId(), actualResult.items().get(0).getId());

        // page 1
        expectedPage = 1;
        categorySearch = new CategorySearchQuery(1, 1, "", "name", "asc");
        actualResult = this.categoryGateway.findAll(categorySearch);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedPage, actualResult.items().size());
        assertEquals(movies.getId(), actualResult.items().get(0).getId());

        // page 2
        expectedPage = 2;
        categorySearch = new CategorySearchQuery(2, 1, "", "name", "asc");
        actualResult = this.categoryGateway.findAll(categorySearch);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedPage, actualResult.items().size());
        assertEquals(movies.getId(), actualResult.items().get(0).getId());
    }

    @Test
    public void givenPrePersistedCategoriesAndDocAsTerms_whenCallsFindAllMatchesCategoryName_shouldReturnPaginated() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 1;

        final var movies = Category.newCategory("Movies", null, true);
        final var series = Category.newCategory("Series", null, true);
        final var documentary = Category.newCategory("Documentary", null, true);
        assertEquals(0, repository.count());

        this.repository.saveAll(List.of(
            CategoryEntity.from(movies),
            CategoryEntity.from(series),
            CategoryEntity.from(documentary)));

        assertEquals(3, repository.count());

        final var categorySearch = new CategorySearchQuery(0, 1, "doc", "name", "asc");
        final var actualResult = this.categoryGateway.findAll(categorySearch);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedPage, actualResult.items().size());
        assertEquals(documentary.getId(), actualResult.items().get(0).getId());
    }

    @Test
    public void givenPrePersistedCategoriesAndMuchWatchesAsTerms_whenCallsFindAllMatchesCategoryDescription_shouldReturnPaginated() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 1;
        final var expectedTheMostWatched = "Categories the most watched";

        final var movies = Category.newCategory("Movies", "Categories the most watched", true);
        final var series = Category.newCategory("Series", "A category watched ", true);
        final var documentary = Category.newCategory("Documentary", "A category less watched", true);
        assertEquals(0, repository.count());

        this.repository.saveAll(List.of(
            CategoryEntity.from(movies),
            CategoryEntity.from(series),
            CategoryEntity.from(documentary)));

        assertEquals(3, repository.count());

        final var categorySearch = new CategorySearchQuery(0, 1, expectedTheMostWatched, "name", "asc");
        final var actualResult = this.categoryGateway.findAll(categorySearch);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedPage, actualResult.items().size());
        assertEquals(movies.getId(), actualResult.items().get(0).getId());
    }
}
