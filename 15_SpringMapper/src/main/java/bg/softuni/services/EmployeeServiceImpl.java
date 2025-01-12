package bg.softuni.services;

import bg.softuni.entities.Employee;
import bg.softuni.entities.dtos.EmployeeDTO;
import bg.softuni.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<EmployeeDTO> findEmployeesBornBefore(int bornBefore) {
        LocalDate before = LocalDate.of(bornBefore, 1, 1);

        List<Employee> employees = employeeRepository
                .findByBirthdayBeforeOrderBySalarySalaryDesc(before);

        ModelMapper mapper = new ModelMapper();

        return employees
                .stream()
                .map(e -> mapper.map(e, EmployeeDTO.class))
                .toList();
    }
}
