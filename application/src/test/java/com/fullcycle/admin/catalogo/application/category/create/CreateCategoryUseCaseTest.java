package com.fullcycle.admin.catalogo.application.category.create;

import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCategoryUseCaseTest {

    @InjectMocks
    private DefaultCreateCategoryUseCase defaultCreateCategoryUseCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.create(any())).thenAnswer(returnsFirstArg());

        final var useCase = new DefaultCreateCategoryUseCase(categoryGateway);
        final var actualOutput = useCase.execute(aCommand).get();

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(categoryGateway, times(1)).create(argThat(aCategory -> {
            return Objects.equals(expectedName, aCategory.getName())
                && Objects.equals(expectedDescription, aCategory.getDescription())
                && Objects.equals(expectedIsActive, aCategory.isActive())
                && Objects.nonNull(aCategory.getId())
                && Objects.nonNull(aCategory.getCreatedAt())
                && Objects.nonNull(aCategory.getUpdatedAt());
        }));
    }

    @Test
    void givenAInvalidName_whenCallsCreateCategory_thenShouldReturnDomainException() {
        final var expectedName = "";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedMessage = "'Name' should not be null or empty";
        final var errorCount = 1;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final var useCase = new DefaultCreateCategoryUseCase(categoryGateway);

        final var notification  =  useCase.execute(aCommand).getLeft();

        assertEquals(expectedMessage, notification.getErros().get(0).message());
        assertEquals(errorCount, notification.getErros().size());

        verify(categoryGateway, times(0)).create(any());
    }

}
