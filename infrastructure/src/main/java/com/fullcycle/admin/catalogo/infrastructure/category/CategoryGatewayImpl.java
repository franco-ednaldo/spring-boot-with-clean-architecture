package com.fullcycle.admin.catalogo.infrastructure.category;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.domain.category.CategorySearchQuery;
import com.fullcycle.admin.catalogo.domain.pagination.Pagination;
import com.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryEntity;
import com.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategoryGatewayImpl implements CategoryGateway {

    private final CategoryRepository repository;

    public CategoryGatewayImpl(final CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category create(final Category aCategory) {
        return this.repository.save(CategoryEntity.from(aCategory)).toAggregate();
    }

    @Override
    public Category update(final Category aCategory) {
        return this.repository.save(CategoryEntity.from(aCategory)).toAggregate();
    }

    @Override
    public void deleteById(final CategoryID anId) {
        this.repository.deleteById(anId.getValue());
    }

    @Override
    public Optional<Category> findById(final CategoryID anId) {
        return this.repository.findById(anId.getValue()).map(item -> item.toAggregate());
    }

    @Override
    public Pagination<Category> findAll(final CategorySearchQuery serach) {
        return null;
    }
}
