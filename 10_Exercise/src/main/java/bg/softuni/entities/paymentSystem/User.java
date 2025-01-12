package bg.softuni.entities.paymentSystem;

import jakarta.persistence.*;

@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Basic
    private String email;

    @Basic
    private String password;

    @OneToOne
    private BillingDetail billingDetail;

    public User() {
    }
}
