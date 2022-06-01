package com.fullcycle.admin.catalogo.domain.validation;

import java.util.List;

public interface ValidationHandler {
    ValidationHandler append(Error anError);

    ValidationHandler append(ValidationHandler aHandler);

    ValidationHandler validate(Validation validation);

    List<Error> getErros();

    default boolean hasError() {
        return getErros() != null && !(getErros().isEmpty());
    }

    default Error firstError() {
        if(getErros() != null && !(getErros().isEmpty())) {
            return getErros().get(0);
        }
        return null;
    }

    interface Validation {
        void validate();
    }

}
