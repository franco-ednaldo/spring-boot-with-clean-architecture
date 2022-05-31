package com.fullcycle.admin.catalogo.domain.validation.handler;

import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.Error;
import com.fullcycle.admin.catalogo.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Notification implements ValidationHandler {


    public static Notification create() {
        return new Notification(new ArrayList<>());
    }

    public static Notification create(final Error aError) {
        return new Notification(new ArrayList<>()).append(aError);
    }

    public static Notification create(final Throwable throwable) {
        return create(new Error(throwable.getMessage()));
    }

    private final List<Error> errors;

    private Notification(final List<Error> errors) {
        this.errors = errors;
    }

    @Override
    public Notification append(final Error anError) {
        this.errors.add(anError);
        return this;
    }

    @Override
    public Notification append(final ValidationHandler aHandler) {
        this.errors.addAll(aHandler.getErros());
        return this;
    }

    @Override
    public Notification validate(final Validation aValidation) {
        try {
            aValidation.validate();
        } catch (final DomainException ex) {
            this.errors.addAll(ex.getErrors());
        } catch (final Throwable ex) {
            this.errors.add(new Error(ex.getMessage()));
        }
        return this;
    }

    @Override
    public List<Error> getErros() {
        return Collections.unmodifiableList(this.errors);
    }

}
