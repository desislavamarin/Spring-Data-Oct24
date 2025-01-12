package bg.softuni.entities.dtos;

public class EmployeeDTO {
    private String firstName;
    private String lastName;
    private double salary;
    private EmployeeDTO manager;

    public EmployeeDTO() {}

    public EmployeeDTO getManager() {
        return manager;
    }

    public void setManager(EmployeeDTO manager) {
        this.manager = manager;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return String.format("%s %s %.2f - Manager: %s",
                firstName, lastName, salary,
                manager == null ? "[no manager]" : manager.getLastName());
    }
}
