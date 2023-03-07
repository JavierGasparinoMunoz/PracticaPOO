/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practicapoo;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Javierino
 */
public class CinemaDispenser {

    /**
     * @param args the command line arguments
     */
    
    //Clase main, arranca el programa
    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException {
        Multiplex multiplex = new Multiplex();
        multiplex.setIdiom("English");
        multiplex.start();
    }
    
}
