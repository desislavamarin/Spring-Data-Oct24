package bg.softuni;

import bg.softuni.entities.dtos.EmployeeDTO;
import bg.softuni.services.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Main implements CommandLineRunner {

    private final EmployeeService employeeService;

    public Main(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int bornBefore = Integer.parseInt(scanner.nextLine());

        List<EmployeeDTO> result = employeeService.findEmployeesBornBefore(bornBefore);

        System.out.println(result);
    }
}
