import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class Main {

    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        //Business Logic
        //changeCasing(entityManager);
        //containsEmployee(entityManager);
        //employeesWithSalary(entityManager);
        //employeesFromDepartment(entityManager);
        //addNewAddress(entityManager);
        //addressesWithCount(entityManager);
        //getEmployeesWithProject(entityManager);
        //findLastProjects(entityManager);
        //increaseSalary(entityManager);

        //maxSalaryFromDepartment(entityManager);
        //deleteTown(entityManager);


        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private static void deleteTown(EntityManager entityManager) throws IOException {
        String townName =  READER.readLine();
        List<Employee> result = entityManager.createQuery("FROM Employee WHERE address.town.name = :name", Employee.class)
                .setParameter("name",townName)
                .getResultList();

        result.forEach(e -> {
            e.setAddress(null);
            entityManager.persist(e);
        });

        List<Address> addresses = entityManager.createQuery("FROM Address WHERE town.name = :name", Address.class)
                .setParameter("name",townName)
                .getResultList();
        addresses.forEach(entityManager::remove);

        Town town = entityManager.createQuery("FROM Town WHERE name = :name", Town.class)
                .setParameter("name",townName)
                .getSingleResult();
        entityManager.remove(town);

        System.out.printf("%d address in %s deleted", addresses.size(), townName);
    }

    private static void maxSalaryFromDepartment(EntityManager entityManager) {
        List<Object[]> result = entityManager.createQuery("SELECT d.name, MAX(e.salary) FROM Department d " +
                        "JOIN d.employees e " +
                        "GROUP BY d.name " +
                        "HAVING MAX(e.salary) NOT BETWEEN 30000 AND 70000", Object[].class)
                .getResultList();
        result.forEach(r -> System.out.printf("%s %s%n", r[0], r[1]));
    }

    private static void increaseSalary(EntityManager entityManager) {
        entityManager.createQuery("FROM Employee WHERE department.name " +
                        "IN ('Engineering', 'Tool Design', 'Marketing', 'Information Services')", Employee.class)
                .getResultList()
                .forEach(e -> {
                    e.setSalary(e.getSalary().multiply(BigDecimal.valueOf(1.12)));
                    entityManager.persist(e);
                    System.out.printf("%s %s ($%.2f)%n", e.getFirstName(), e.getLastName(), e.getSalary());
                        });
    }

    private static void findLastProjects(EntityManager entityManager) {
        entityManager.createQuery("FROM Project ORDER BY startDate DESC", Project.class)
                .setMaxResults(10)
                .getResultList()
                .forEach(p -> System.out.printf("Project name: %s%n" +
                                        " \tProject Description: %s%n" +
                                        " \tProject Start Date:%s%n" +
                                        " \tProject End Date: %s%n",
                        p.getName(), p.getDescription(), p.getStartDate(), p.getEndDate()));
    }

    private static void getEmployeesWithProject(EntityManager entityManager) throws IOException {
        Employee employee = entityManager.find(Employee.class, Integer.parseInt(READER.readLine()));
        if (employee.getProjects().isEmpty()) {
            return;
        }

        System.out.printf("%s %s - %s%n", employee.getFirstName(), employee.getLastName(), employee.getJobTitle());

        employee.getProjects()
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(p -> System.out.printf("%s%n", p.getName()));
    }

    private static void addressesWithCount(EntityManager entityManager) {
        entityManager.createQuery("FROM Address ORDER BY employees.size DESC", Address.class)
                .setMaxResults(10)
                .getResultList()
                .forEach(a -> System.out.printf("%s, %s - %d employees%n",
                        a.getText(), a.getTown().getName(), a.getEmployees().size()));
    }

    private static void addNewAddress(EntityManager entityManager) throws IOException {
        String lastName = READER.readLine();

        Address address = new Address();
        address.setText("Vitoshka 15");
        entityManager.persist(address);

        Employee employee = entityManager.createQuery("FROM Employee WHERE lastName = :lastName", Employee.class)
                .setParameter("lastName", lastName)
                .getSingleResult();
        employee.setAddress(address);
        entityManager.persist(employee);
    }

    private static void employeesFromDepartment(EntityManager entityManager) {
        entityManager.createQuery("FROM Employee WHERE department.name = 'Research and Development' " +
                "ORDER BY salary, id", Employee.class)
                .getResultStream()
                .forEach(e -> System.out.printf("%s %s from %s - $%.2f%n",
                        e.getFirstName(), e.getLastName(),
                        e.getDepartment().getName(), e.getSalary()));
    }

    private static void employeesWithSalary(EntityManager entityManager) {
        entityManager.createQuery("FROM Employee WHERE salary > 50000", Employee.class)
                .getResultStream()
                .forEach(e -> System.out.println(e.getFirstName()));
    }

    private static void containsEmployee(EntityManager entityManager) throws IOException {
        String[] input = READER.readLine().split("\\s+");
        List<Employee> resultList = entityManager
                .createQuery("FROM Employee WHERE firstName = :firstName AND lastName = :lastName", Employee.class)
                .setParameter("firstName", input[0])
                .setParameter("lastName", input[1])
                .getResultList();

        System.out.println(resultList.isEmpty() ? "No" : "Yes");
    }

    private static void changeCasing(EntityManager entityManager) {
        List<Town> towns = entityManager.createQuery("FROM Town WHERE LENGTH(name) > 5", Town.class).getResultList();
        towns.forEach(t -> {
            t.setName(t.getName().toUpperCase());
            entityManager.persist(t);
        });

        entityManager.createQuery("UPDATE Town SET name = UPPER(name) WHERE LENGTH(name) > 5").executeUpdate();
    }
}