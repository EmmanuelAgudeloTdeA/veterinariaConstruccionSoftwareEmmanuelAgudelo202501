package app.adapters.rest.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequest {
    private String userName;
    private String password;
    private long document;
    private String name;
    private String dateOfBirthday;

    @Override
    public String toString() {
        return "UserRequest{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", document=" + document +
                ", name='" + name + '\'' +
                ", dateOfBirthday='" + dateOfBirthday + '\'' +
                '}';
    }
}
