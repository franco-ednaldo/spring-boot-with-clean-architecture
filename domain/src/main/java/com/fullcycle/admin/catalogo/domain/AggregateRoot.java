package com.fullcycle.admin.catalogo.domain;

public abstract class AggregateRoot<T extends Identifier> extends Entity<T> {

    public AggregateRoot(final T id) {
        super(id);
    }

}
