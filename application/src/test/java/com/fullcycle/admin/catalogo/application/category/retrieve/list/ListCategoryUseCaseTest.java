package com.fullcycle.admin.catalogo.application.category.retrieve.list;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.domain.category.CategorySearchQuery;
import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.pagination.Pagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListCategoryUseCaseTest {

    @InjectMocks
    private DefaultListCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    void givenValidQuery_whenCallasListCategories_shouldReturnListCategories() {
        final var categories = List.of(
            Category.newCategory("Filmes", "A categoria mais assistida", true),
            Category.newCategory("Series", "A categoria mais assistida", true));
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var exportSort = "createdAt";
        final var expectedDirection = "asc";
        final var query = new CategorySearchQuery(expectedPage, expectedPerPage, expectedTerms, exportSort, expectedDirection);
        final var expectedPagination = new Pagination<>(expectedPage, expectedPerPage, categories.size(), categories);
        final var expectedItemsCount = 2;
        final var expectedResult = expectedPagination.map(CategoryListOutPut::from);

        when(this.categoryGateway.findAll(eq(query))).thenReturn(expectedPagination)
            .thenReturn(expectedPagination);

        final var actualResult = useCase.execute(query);

        assertEquals(expectedItemsCount, actualResult.items().size());
        assertEquals(expectedResult, actualResult);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(categories.size(), actualResult.total());
    }

    @Test
    void givenAInvalidQuery_whenCallListCategories_shouldEmptyCategories() {

    }

    @Test
    void givenAInvalidQuery_whenCallListCategories_shouldThrowsException() {

    }

}
