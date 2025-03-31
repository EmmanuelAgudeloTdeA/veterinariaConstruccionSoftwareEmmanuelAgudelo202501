package app.adapters.persons.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@Entity
@Table(name = "persons")
@NoArgsConstructor
public class PersonEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long personId;
    @Column(name = "document")
    private long document;
    @Column(name = "name")
    private String name;
    @Column(name = "date_of_birthday")
    private Date dateOfBirthday;
    @Column(name = "role")
    private String role;

}
