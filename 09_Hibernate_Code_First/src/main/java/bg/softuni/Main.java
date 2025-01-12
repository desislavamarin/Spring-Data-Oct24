package bg.softuni;

import bg.softuni.entities.vehicles.Bike;
import bg.softuni.entities.vehicles.Car;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("jpa_relations");
        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        Bike bike = new Bike();
        bike.setWheelSize(15);

        Car car = new Car();
        car.setPaintCode("black");

        entityManager.persist(bike);
        entityManager.persist(car);

        entityManager.getTransaction().commit();
        entityManager.close();


    }
}