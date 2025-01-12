package bg.softuni;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        Configuration cfg = new Configuration();
        cfg.configure();

        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session currentSession = sessionFactory.openSession();
        currentSession.beginTransaction();

        Student student = new Student();
        student.setName("Hiber");
        currentSession.persist(student);

        List<Student> fromStudent =
                currentSession.createQuery("FROM Student, Student.class").list();
        for (Student s : fromStudent) {
            System.out.println(s.getName());
        }

        currentSession.getTransaction().commit();
        currentSession.close();
    }
}