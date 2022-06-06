package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.AggregateRoot;
import com.fullcycle.admin.catalogo.domain.validation.ValidationHandler;

import java.time.Instant;

public class Category extends AggregateRoot<CategoryID> implements Cloneable {

    private String name;

    private String description;

    private boolean isActive;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;

    private Category(final CategoryID id, final String name, final String description, final boolean isActive, final Instant createdAt,
        final Instant updatedAt, final Instant deletedAt) {
        super(id);
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Category newCategory(final String aName, final String aDescription, final boolean isActive) {
        final var id = CategoryID.unique();
        final var now = Instant.now();
        return new Category(id, aName, aDescription, isActive, now, now, now);
    }

    public Category update(final String aName, final String aDescription, final boolean isActive) {
        this.name = aName;
        this.description = aDescription;
        if (isActive) {
            this.activate();
        } else {
            this.deActivate();
        }
        return this;
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new CategoryValidator(this, handler).validate();
    }

    public CategoryID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return isActive;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public Category activate() {
        this.isActive = true;
        this.updatedAt = Instant.now();
        return this;
    }

    public Category deActivate() {
        if (getDeletedAt() == null) {
            this.deletedAt = Instant.now();
            this.isActive = false;
        }
        return this;
    }

    @Override
    public Category clone() {
        try {
            return  (Category) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}