package app.adapters.inputs;

import app.adapters.inputs.utils.UserValidator;
import app.adapters.inputs.utils.Utils;
import app.domain.models.User;
import app.domain.services.LoginService;
import app.ports.InputPort;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@Component
public class LoginInput implements InputPort {
    private Map<String, InputPort> inputs;
    @Autowired
    private AdminInput adminInput;
    @Autowired
    private SellerInput sellerInput;
    @Autowired
    private VeterinarianInput veterinarianInput;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private LoginService loginService;

    private User userLogged = null;
    private final String MENU = "Ingrese la opcion que desea:\n 1. iniciar sesion \n 2. Salir\n";

    public LoginInput(AdminInput adminInput, SellerInput sellerInput, VeterinarianInput veterinarianInput) {
        super();
        this.adminInput = adminInput;
        this.sellerInput = sellerInput;
        this.veterinarianInput = veterinarianInput;
        this.inputs = new HashMap<String, InputPort>();
        inputs.put("admin", adminInput);
        inputs.put("seller", sellerInput);
        inputs.put("veterinarian", veterinarianInput);
    }

    @Override
    public void menu() throws Exception {
        boolean sesion = true;
        while (sesion) {
            sesion = options();
        }
    }

    private boolean options() throws Exception {
        try {
            System.out.println(MENU);
            String option = Utils.getReader().nextLine();
            switch (option) {
                case "1":
                    this.login();
                    return true;
                case "2":
                    System.out.println("Saliendo...");
                    return false;
                default:
                    System.out.println("Opcion invalida.");
                    return true;
            }
        } catch (Exception e) {
            System.out.println("Algo salio mal: "+e.getMessage());
            return true;
        }
    }

    public void login() throws Exception {
        try {
            System.out.println("Ingrese su usuario");
            String userName = userValidator.userNameValidator(Utils.getReader().nextLine());
            System.out.println("Ingrese su contraseña");
            String password = userValidator.passwordValidator(Utils.getReader().nextLine());
            User user = new User();
            user.setUserName(userName);
            user.setPassword(password);
            user = loginService.login(user);
            this.userLogged = user;
            InputPort inputPort = inputs.get(user.getRole());
            inputPort.menu();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
