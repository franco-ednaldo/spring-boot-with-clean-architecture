package com.fullcycle.admin.catalogo.infrastructure.utils;

import org.springframework.data.jpa.domain.Specification;

public final class SpecificationUtils {

    private SpecificationUtils() {
    }

    public static <T> Specification<T> like(final String prop, final String value) {
        return (root, query, cb) -> cb.like(cb.upper(root.get(prop)), like(value));
    }

    private static String like(final String value) {
        return "%".concat(value.toUpperCase()).concat("%");
    }

}
