package com.fullcycle.admin.catalogo.infrastructure.category;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.domain.category.CategorySearchQuery;
import com.fullcycle.admin.catalogo.domain.pagination.Pagination;
import com.fullcycle.admin.catalogo.infrastructure.utils.SpecificationUtils;
import com.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryEntity;
import com.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.fullcycle.admin.catalogo.infrastructure.utils.SpecificationUtils.like;
import static org.springframework.data.domain.Sort.Direction.fromString;

@Component
public class CategoryGatewayImpl implements CategoryGateway {

    private final CategoryRepository repository;

    public CategoryGatewayImpl(final CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category create(final Category aCategory) {
        return save(aCategory);
    }

    @Override
    public Category update(final Category aCategory) {
        return save(aCategory);
    }

    @Override
    public void deleteById(final CategoryID anId) {
        final var id = anId.getValue();
        if (this.repository.existsById(id)) {
            this.repository.deleteById(anId.getValue());
        }
    }

    @Override
    public Optional<Category> findById(final CategoryID anId) {
        return this.repository.findById(anId.getValue()).map(item -> item.toAggregate());
    }

    @Override
    public Pagination<Category> findAll(final CategorySearchQuery search) {
        final var pageRequest = PageRequest.of(
            search.page(),
            search.perPage(),
            Sort.by(fromString(search.direction()), search.sort()));

        final var specifications = Optional.ofNullable(search.terms())
            .filter(str -> !str.isBlank())
            .map(this::getCondition)
            .orElseGet(() -> Specification.where(null));

        final var pageResult = this.repository.findAll(Specification.where(specifications), pageRequest);

        return new Pagination<>(pageResult.getNumber(), pageResult.getSize(), pageResult.getTotalElements(),
            pageResult.map(CategoryEntity::toAggregate).toList());
    }

    private Specification<CategoryEntity> getCondition(final String str) {
        return SpecificationUtils.
            <CategoryEntity>like("name", str)
            .or(like("description", str));
    }


    private Category save(final Category aCategory) {
        return this.repository.save(CategoryEntity.from(aCategory)).toAggregate();
    }

}
