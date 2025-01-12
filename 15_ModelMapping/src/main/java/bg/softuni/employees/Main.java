package bg.softuni.employees;

import bg.softuni.employees.dtos.ManagerDTO;
import bg.softuni.employees.entities.Employee;
import org.modelmapper.ModelMapper;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Employee firstEmployee = new Employee("Stephen", "Bjorn", 4300);
        Employee secondEmployee = new Employee("Kirilyc", "Lefi", 4400);
        Employee thirdEmployee = new Employee("Jurgen", "Straus", 1000.95);
        Employee fourthEmployee = new Employee("Moni", "Kozinac", 2030.99);
        Employee fifthEmployee = new Employee("Kopp", "Spidok", 2000.21);

        Employee managerOne = new Employee("Steve", "Jobbsen", List.of(firstEmployee, secondEmployee));
        Employee managerTwo = new Employee("Carl", "Kormac", List.of(thirdEmployee, fourthEmployee, fifthEmployee));

        ModelMapper modelMapper = new ModelMapper();
        ManagerDTO oneDTO = modelMapper.map(managerOne, ManagerDTO.class);

        System.out.println(oneDTO);
    }
}
