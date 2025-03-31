package app.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
public class Person {
    private long personId;
    private long document;
    private String name;
    private Date dateOfBirthday;
    private String role;

    public Person(long personId, long document, String name, Date dateOfBirthday, String role) {
        this.personId = personId;
        this.document = document;
        this.name = name;
        this.dateOfBirthday = dateOfBirthday;
        this.role = role;
    }
}
