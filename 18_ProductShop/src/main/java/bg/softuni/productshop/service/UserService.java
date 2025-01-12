package bg.softuni.productshop.service;

import java.io.IOException;

public interface UserService {
    void seedUsers() throws IOException;
    boolean isImported();
}
