package com.fullcycle.admin.catalogo.infrastructure.category;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.domain.category.CategorySearchQuery;
import com.fullcycle.admin.catalogo.domain.pagination.Pagination;
import com.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
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
        return null;
    }

    @Override
    public Category update(final Category aCategory) {
        return null;
    }

    @Override
    public void deleteById(final CategoryID anId) {

    }

    @Override
    public Optional<Category> findById(final CategoryID anId) {
        return Optional.empty();
    }

    @Override
    public Pagination<Category> findAll(final CategorySearchQuery serach) {
        return null;
    }

}
