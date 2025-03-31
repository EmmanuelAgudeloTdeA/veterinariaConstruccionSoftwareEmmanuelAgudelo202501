package app.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
public class User extends Person{
    private long userId;
    private String userName;
    private String password;

    public User(long personId, long document, String name, Date dateOfBirthday, String role, long userId, String userName, String password) {
        super(personId, document, name, dateOfBirthday, role);
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }
}
