package com.fullcycle.admin.catalogo.application.category.update;

import com.fullcycle.admin.catalogo.application.category.create.CreateCategoryCommand;
import com.fullcycle.admin.catalogo.application.category.create.DefaultCreateCategoryUseCase;
import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateCategoryUseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    void givenAValidCategory_whenCallsUpdateCategory_shouldReturnCategoryId() {
        final var aCategory = Category.newCategory("Filme", null, true);
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(
            expectedId.getValue(),
            expectedName,
            expectedDescription,
            expectedIsActive);
        when(categoryGateway.findById(eq(expectedId))).thenReturn(Optional.of(aCategory.clone()));
        when(categoryGateway.update(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());
        verify(categoryGateway, times(1)).findById(eq(expectedId));
        verify(categoryGateway, times(1)).update(argThat(aUpdateCategory -> {
            return Objects.equals(expectedName, aUpdateCategory.getName())
                && Objects.equals(expectedDescription, aUpdateCategory.getDescription())
                && Objects.equals(expectedIsActive, aUpdateCategory.isActive())
                && Objects.equals(expectedId, aUpdateCategory.getId())
                && aCategory.getUpdatedAt().isBefore(aUpdateCategory.getUpdatedAt())
                && Objects.equals(aCategory.getCreatedAt(), aUpdateCategory.getCreatedAt());
        }));
    }

    @Test
    void givenAInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException() {
        final var aCategory = Category.newCategory("Filme", null, true);
        final var expectedName = "";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedMessage = "'Name' should not be null or empty";
        final var errorCount = 1;
        final var expectedId = aCategory.getId();
        final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.findById(eq(expectedId))).thenReturn(Optional.of(aCategory.clone()));
        final var useCase = new DefaultUpdateCategoryUseCase(categoryGateway);

        final var notification = useCase.execute(aCommand).getLeft();

        assertEquals(expectedMessage, notification.firstError().message());
        assertEquals(errorCount, notification.getErros().size());

        verify(categoryGateway, times(0)).update(any());
    }

}