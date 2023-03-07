/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicapoo;

import java.io.FileNotFoundException;
import java.io.IOException;
import sienens.CinemaTicketDispenser;

/**
 *
 * @author Javierino
 */
public class Multiplex {
    private String idiom;
    private CinemaTicketDispenser cinemaTicketDispenser;
    static MainMenu operation;

    
    //Constructor de la clase Multiplex
    public Multiplex() throws FileNotFoundException, ClassNotFoundException, IOException{
    }

    //Método encargado de iniciar tanto el dispensador como el mení principal
    public void start() throws FileNotFoundException, ClassNotFoundException, IOException{
        cinemaTicketDispenser = new CinemaTicketDispenser();
        operation = new MainMenu(cinemaTicketDispenser,this);
        operation.doOperation();
    }

    //Getter de la variable idiom
    public String getIdiom() {
        return idiom;
    }

    //Setter de la variable idiom
    public void setIdiom(String idiom) {
        this.idiom = idiom;
    }
    
}
