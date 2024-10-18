package com.haile.exe101.depairapplication.services;

import com.haile.exe101.depairapplication.models.entity.Category;
import com.haile.exe101.depairapplication.repositories.CategoryRepository;
import com.haile.exe101.depairapplication.services.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category createOrUpdateCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public boolean deleteCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            categoryRepository.delete(category);
            return true;
        }
        return false;
    }
}
