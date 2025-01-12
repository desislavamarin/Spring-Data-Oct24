package bg.softuni.entities.paymentSystem;

import jakarta.persistence.*;

@Entity
@Table(name = "billing_detail")
public class BillingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private int number;

    @OneToOne
    private User owner;

    @OneToOne
    private CreditCard creditCard;

    @OneToOne
    private BankAccount bankAccount;

    public BillingDetail() {
    }
}
