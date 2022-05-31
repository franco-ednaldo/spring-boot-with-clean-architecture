package com.fullcycle.admin.catalogo.application.category.create;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.validation.handler.Notification;
import io.vavr.control.Either;

import static io.vavr.API.*;


public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

    private final CategoryGateway categoryGateway;


    public DefaultCreateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public Either<Notification, CreateCategoryOutPut> execute(final CreateCategoryCommand aCommand) {
        final var aName = aCommand.name();
        final var aDescription = aCommand.description();
        final var isActive = aCommand.isActive();
        final var aCategory = Category.newCategory(aName, aDescription, isActive);
        final var notification = Notification.create();

        aCategory.validate(notification);

        return notification.hasError() ? Left(notification) : create(aCategory);
    }

    private Either<Notification, CreateCategoryOutPut> create(Category aCategory) {
        return Try(() -> this.categoryGateway.create(aCategory))
            .toEither()
            .bimap(Notification::create, CreateCategoryOutPut::from);
    }

}
