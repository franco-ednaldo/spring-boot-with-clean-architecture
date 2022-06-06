package com.fullcycle.admin.catalogo.application.category.delete;

public record DeleteCategoryCommand(String id){
    public static DeleteCategoryCommand with(String id) {
        return new DeleteCategoryCommand(id);
    }
}
