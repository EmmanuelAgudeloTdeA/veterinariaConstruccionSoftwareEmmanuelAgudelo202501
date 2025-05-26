package app.adapters.rest.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersonRequest {
    private long document;
    private String name;
    private String dateOfBirthday;

    @Override
    public String toString() {
        return "PersonRequest{" +
                ", document=" + document +
                ", name='" + name + '\'' +
                ", dateOfBirthday='" + dateOfBirthday + '\'' +
                '}';
    }
}
