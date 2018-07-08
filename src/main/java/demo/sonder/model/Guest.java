package demo.sonder.model;
import javax.persistence.*;

@Entity
public class Guest {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    @OneToOne
    private ContactInfo contactInfo;


}
