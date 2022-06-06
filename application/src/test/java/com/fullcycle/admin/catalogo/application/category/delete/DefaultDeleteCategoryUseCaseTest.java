package com.fullcycle.admin.catalogo.application.category.delete;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DefaultDeleteCategoryUseCaseTest {
    @InjectMocks
    private DefaultDeleteCategoryUseCase useCase;
    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(this.categoryGateway);
    }

    @Test
    void givenAvalidId_whenCallasDelete_shouldDeletedCategory() {
        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var expectedId = aCategory.getId().getValue();
        doNothing().when(this.categoryGateway).deleteById(CategoryID.from(expectedId));

        assertDoesNotThrow(() -> useCase.execute(expectedId));

        verify(this.categoryGateway, times(1)).deleteById(CategoryID.from(expectedId));
    }

    @Test
    void givenAInvalidId_whenCallasDelete_shouldThrowsException() {
        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var expectedId = aCategory.getId();

        doThrow(new IllegalStateException("Gateway error"))
            .when(categoryGateway).deleteById(expectedId);

        Assertions.assertThrows(IllegalStateException.class, () -> {
            this.useCase.execute(expectedId.getValue());
        });

        verify(this.categoryGateway, times(1)).deleteById(expectedId);
    }
}