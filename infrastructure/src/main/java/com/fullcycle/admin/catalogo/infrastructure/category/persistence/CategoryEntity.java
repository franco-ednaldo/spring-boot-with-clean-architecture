package com.fullcycle.admin.catalogo.infrastructure.category.persistence;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "category")
public class CategoryEntity {

    @Id
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 4000)
    private String description;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "create_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createAt;

    @Column(name = "update_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant updateAt;

    @Column(name = "delete_at", columnDefinition = "DATETIME(6)")
    private Instant deleteAt;

    public CategoryEntity() {

    }

    public static CategoryEntity from(final Category aCategory) {
        return new CategoryEntity(
            aCategory.getId().getValue(),
            aCategory.getName(),
            aCategory.getDescription(),
            aCategory.isActive(),
            aCategory.getCreatedAt(),
            aCategory.getUpdatedAt(),
            aCategory.getDeletedAt()
        );
    }

    public Category toAggregate() {
        return Category.with(
            CategoryID.from(this.id),
            this.getName(),
            this.getDescription(),
            this.getActive(),
            this.getCreateAt(),
            this.getUpdateAt(),
            this.getDeleteAt());
    }


    private CategoryEntity(
        final String id,
        final String name,
        final String description,
        final Boolean active,
        final Instant createAt,
        final Instant updateAt,
        final Instant deleteAt) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.deleteAt = deleteAt;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(final Instant createAt) {
        this.createAt = createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(final Instant updateAt) {
        this.updateAt = updateAt;
    }

    public Instant getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(final Instant deleteAt) {
        this.deleteAt = deleteAt;
    }

}
