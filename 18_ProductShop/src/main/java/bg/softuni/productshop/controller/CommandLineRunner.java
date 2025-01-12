package bg.softuni.productshop.controller;

import bg.softuni.productshop.service.CategoryService;
import bg.softuni.productshop.service.ProductService;
import bg.softuni.productshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {

    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public CommandLineRunner(CategoryService categoryService,
                             ProductService productService,
                             UserService userService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (this.categoryService.isImported()) {
            this.categoryService.seedCategories();
        }

        if (!this.userService.isImported()) {
            this.userService.seedUsers();
        }

        if (!this.productService.isImported()) {
            this.productService.seedProducts();
        }

        this.productService.exportProductsInRange();
    }
}
