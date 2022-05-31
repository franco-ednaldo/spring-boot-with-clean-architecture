package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryTest {

    @Test
    void givenAvalidParams_whenCallNewCategory_thenInstantiateACategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        assertNotNull(actualCategory);
        assertNotNull(actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());

        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    void givenInvalidNullName_whenCallNewCategoryAndValidate_thenShouldReciverError() {
        final String expectedName = null;
        final var expectedErrorCount = 1;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'Name' should not be null or empty";

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var actualException = Assertions.assertThrows(DomainException.class,
            () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    void givenInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldReciverError() {
        final String expectedName = "  ";
        final var expectedErrorCount = 1;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'Name' should not be null or empty";

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var actualException = Assertions.assertThrows(DomainException.class,
            () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    void givenInvalidGreaterThaName255_whenCallNewCategoryAndValidate_thenShouldReciverError() {
        final String expectedName = """
                fajdflkajdflaksdjflasdkjfalskdfjkkkkkdaflkasdjfalkjfalkdfjalkdfjaldfkjasdlfkjasflkajsdflkajsdflaksdjfalksdfjalskdfjalksdfjaldksfjalsd
                fajdflkajdflaksdjflasdkjfalskdfjkkkkkdaflkasdjfalkjfalkdfjalkdfjaldfkjasdlfkjasflkajsdflkajsdflaksdjfalksdfjalskdfjalksdfjaldksfjalsd
                fajdflkajdflaksdjflasdkjfalskdfjkkkkkdaflkasdjfalkjfalkdfjalkdfjaldfkjasdlfkjasflkajsdflkajsdflaksdjfalksdfjalskdfjalksdfjaldksfjalsd
            """;
        final var expectedErrorCount = 1;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'Name' should not be more then 255 characters";

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var actualException = Assertions.assertThrows(DomainException.class,
            () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }
}
