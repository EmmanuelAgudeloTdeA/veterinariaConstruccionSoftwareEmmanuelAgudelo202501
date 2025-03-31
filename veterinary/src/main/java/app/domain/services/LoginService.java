package app.domain.services;

import app.domain.models.User;
import app.ports.UserPort;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Setter
@Getter
@Service
public class LoginService {
    @Autowired
    private UserPort userPort;

    public User login(User user) throws Exception {
        User userValidated = userPort.findByUserName(user);
        if(userValidated == null){
            throw new Exception("usuario o contraseña invalida");
        }
        if (!user.getPassword().equals(userValidated.getPassword())) {
          throw new Exception("usuario o contraseña invalida");
        }
        return userValidated;
    }
}
