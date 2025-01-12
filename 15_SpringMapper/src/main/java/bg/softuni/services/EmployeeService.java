package bg.softuni.services;

import bg.softuni.entities.dtos.EmployeeDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {

    List<EmployeeDTO> findEmployeesBornBefore(int bornBefore);
}
