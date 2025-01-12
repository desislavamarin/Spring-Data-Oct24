package bg.softuni.entities.paymentSystem;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "credit_card")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "expiration_month")
    private Instant expirationMonth;

    @Column(name = "expiration_year")
    private Instant expirationYear;

    public CreditCard() {
    }
}
