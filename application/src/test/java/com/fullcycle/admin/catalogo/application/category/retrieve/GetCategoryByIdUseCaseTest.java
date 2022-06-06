package com.fullcycle.admin.catalogo.application.category.retrieve;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCategoryByIdUseCaseTest {

    @InjectMocks
    private DefaultGetCategoryByIdUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    void givenAvalidId_whenCallasGetCategory_shouldReturnCategory() {
        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var expectedId = aCategory.getId().getValue();
        when(this.categoryGateway.findById(CategoryID.from(expectedId)))
            .thenReturn(Optional.of(aCategory.clone()));

        final var actualCategory = useCase.execute(expectedId);

        assertEquals(CategoryOutput.from(aCategory), actualCategory);
    }

    @Test
    void givenAInvalidId_whenCallasDelete_shouldThrowsException() {
        final var expectedId = CategoryID.from("123456");
        final var expextedErrorMessage = "";
        when(categoryGateway.findById(eq(expectedId))).thenReturn(Optional.empty());

        final var expectedException = assertThrows(DomainException.class, () -> {
            this.useCase.execute(expectedId.getValue());
        });

        assertEquals(expextedErrorMessage, expectedException.getMessage());
    }

}
