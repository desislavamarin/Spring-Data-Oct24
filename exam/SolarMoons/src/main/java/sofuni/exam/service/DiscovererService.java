package sofuni.exam.service;

import sofuni.exam.models.entity.Discoverer;

import java.io.IOException;
import java.util.Optional;

public interface DiscovererService {

    boolean areImported();

    String readDiscovererFileContent() throws IOException;

    String importDiscoverers() throws IOException;
}
