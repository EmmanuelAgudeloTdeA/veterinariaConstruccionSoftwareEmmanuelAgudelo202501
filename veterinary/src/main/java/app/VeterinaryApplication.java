package app;

import app.adapters.inputs.LoginInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VeterinaryApplication implements CommandLineRunner {

    @Autowired
    private LoginInput loginInput;

    @Override
    public void run(String... args) throws Exception {
        loginInput.menu();
    }

    public static void main(String[] args) {
        SpringApplication.run(VeterinaryApplication.class, args);
    }

}
