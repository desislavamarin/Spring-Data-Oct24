package bg.softuni._11_springultimate.service;

import bg.softuni._11_springultimate.repositories.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    @Override
    public void register() {
        System.out.println("Register Student");
    }
}
