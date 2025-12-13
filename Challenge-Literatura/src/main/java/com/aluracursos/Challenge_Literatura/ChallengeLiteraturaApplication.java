package com.aluracursos.Challenge_Literatura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.aluracursos.Challenge_Literatura.principal.Principal;

//import java.security.Principal;

@SpringBootApplication
public class ChallengeLiteraturaApplication implements CommandLineRunner {

    @Autowired
    private Principal principal;

	public static void main(String[] args) {
		SpringApplication.run(ChallengeLiteraturaApplication.class, args);
	}
    //es la forma de indicar que un metodo en una clase esta sobrescribiendo (override) un metodo heredado de una clase padre o una interfaz
    @Override
    public void run(String... args) throws Exception {
        principal.muestraElMenu();

    }


    //
   /* @Override
    public void run(String...args) throws Exception {
        Principal principal = new Principal();
        principal.muestraElMenu();*/

}
