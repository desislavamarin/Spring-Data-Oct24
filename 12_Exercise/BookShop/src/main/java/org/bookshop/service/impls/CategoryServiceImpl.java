package org.bookshop.service.impls;

import org.bookshop.data.entities.Category;
import org.bookshop.data.repositories.CategoryRepository;
import org.bookshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final String CATEGORIES_PATH = "src/main/resources/files/categories.txt";

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories() throws IOException {
        //read categories.txt
        Set<Category> categories = new HashSet<>();

        Files.readAllLines(Path.of(CATEGORIES_PATH))
                .stream()
                .filter(line -> !line.trim().isEmpty())
                .forEach(line -> {
                    Category category = new Category(line);
                    categories.add(category);
                });

        this.categoryRepository.saveAllAndFlush(categories);
        System.out.printf("Count of imported categories - %d%n", this.categoryRepository.count());
    }

    @Override
    public boolean isImported() {
        return this.categoryRepository.count() > 0;
    }
}
