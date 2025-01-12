package bg.softuni.productshop.service.impls;

import bg.softuni.productshop.data.entities.Category;
import bg.softuni.productshop.data.entities.Product;
import bg.softuni.productshop.data.entities.User;
import bg.softuni.productshop.data.repositories.CategoryRepository;
import bg.softuni.productshop.data.repositories.ProductRepository;
import bg.softuni.productshop.data.repositories.UserRepository;
import bg.softuni.productshop.service.ProductService;
import bg.softuni.productshop.service.dtos.ProductSeedJsonDTO;
import bg.softuni.productshop.service.dtos.ProductsExportJsonDTO;
import bg.softuni.productshop.service.dtos.UserSeedJsonDTO;
import bg.softuni.productshop.utils.ValidatorUtil;
import com.google.gson.Gson;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final String JSON_PATH = "src/main/resources/json/products.json";

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final Gson gson;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              UserRepository userRepository,
                              CategoryRepository categoryRepository,
                              ModelMapper modelMapper,
                              ValidatorUtil validatorUtil, Gson gson) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.gson = gson;
    }

    @Override
    public void seedProducts() throws IOException {
        ProductSeedJsonDTO[] productSeedJsonDTOS = this.gson.fromJson(String
                .join("", Files.readAllLines(Path.of(JSON_PATH))), ProductSeedJsonDTO[].class);
        for (ProductSeedJsonDTO seedJsonDTO : productSeedJsonDTOS) {
            Product product = this.modelMapper.map(seedJsonDTO, Product.class);
            product.setSeller(getRandomUser(true));
            product.setBuyer(getRandomUser(false));
            product.setCategories(getRandomCategories());

            this.productRepository.saveAndFlush(product);
        }
    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();
        int count = ThreadLocalRandom
                .current()
                .nextInt(1, 4);
        for (int i = 0; i < count; i++) {
            categories.add(this.categoryRepository.findById(ThreadLocalRandom
                    .current()
                    .nextInt(1, (int) this.categoryRepository.count() + 1)).get());
        }
        return categories;
    }

    private User getRandomUser(boolean flag) {
        int randomId = 0;
        if (flag) {
            randomId = ThreadLocalRandom
                    .current()
                    .nextInt(1, (int) (this.userRepository.count() + 1));
        } else {
            randomId = ThreadLocalRandom
                    .current()
                    .nextInt(1, (int) (this.userRepository.count()));
        }
        return this.userRepository
                .findById(randomId).get();
    }

    @Override
    public boolean isImported() {
        return this.productRepository.count() > 0;
    }

    @Override
    public void exportProductsInRange() throws IOException {
        Set<Product> products = this.productRepository
                .findAllByPriceBetweenAndBuyerIsNull(BigDecimal.valueOf(500), BigDecimal.valueOf(1000));
        Set<ProductsExportJsonDTO> dtos = products.stream()
                .map(p -> {
                    ProductsExportJsonDTO product = this.modelMapper.map(p, ProductsExportJsonDTO.class);
                    product.setSeller(p.getSeller().getFirstName() + " " + p.getSeller().getLastName());
                    return product;
                })
                .collect(Collectors.toSet());

        String json = this.gson.toJson(products);
        FileWriter fileWriter = new FileWriter("src/main/resources/json/exports/products-in-range.json");
        fileWriter.write(json);
        fileWriter.close();
    }

}
