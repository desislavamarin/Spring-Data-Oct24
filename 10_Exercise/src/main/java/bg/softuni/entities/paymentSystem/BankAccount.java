package bg.softuni.entities.paymentSystem;

import jakarta.persistence.*;

@Entity
@Table(name = "bank_acount")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private String name;

    @Basic
    private String SWIFTCode;

    public BankAccount() {
    }
}
