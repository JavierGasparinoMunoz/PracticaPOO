/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicapoo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sienens.CinemaTicketDispenser;

/**
 *
 * @author Javierino
 */
public class MainMenu extends Operation {

    public List<Operation> operationList = new ArrayList<>();
    private final CinemaTicketDispenser cinemaTicketDispenser = super.getDispenser();
    final MovieTicketSales movieTicketSales;
    private final IdiomSelection idiomSelection;

    //Constructor de la clase MainMenu, ya que hereda de Operation uso super
    public MainMenu(CinemaTicketDispenser dispenser, Multiplex multi) throws FileNotFoundException, ClassNotFoundException, IOException {
        super(dispenser, multi);
        movieTicketSales = new MovieTicketSales(cinemaTicketDispenser, super.getMultiplex());
        idiomSelection = new IdiomSelection(cinemaTicketDispenser, super.getMultiplex());
    }

    //Metodo heredado de Operation, en este llamo al metodo encargado de presentar el menú principal
    @Override
    public void doOperation() {
        presentMenu();
    }

    //Método heredado de Operation, se define el título de la clase que se le pasará al dispensador
    @Override
    public String getTitle() {
        return java.util.ResourceBundle
                .getBundle("properties/" + super.getMultiplex().getIdiom())
                .getString("MENU PRINCIPAL");
    }

    //Método encargado de presntar el menú principal
    public void presentMenu() {
        cinemaTicketDispenser.setDescription("");
        cinemaTicketDispenser.setImage("");
        cinemaTicketDispenser.setMenuMode();
        cinemaTicketDispenser.expelCreditCard(10);
        cinemaTicketDispenser.setTitle(this.getTitle());
        cinemaTicketDispenser.setOption(0, movieTicketSales.getTitle());
        cinemaTicketDispenser.setOption(1, idiomSelection.getTitle());
        cinemaTicketDispenser.setOption(2, null);
        cinemaTicketDispenser.setOption(3, null);
        cinemaTicketDispenser.setOption(4, null);
        cinemaTicketDispenser.setOption(5, null);

        while (true) {
            char option = cinemaTicketDispenser.waitEvent(30);
            switch (option) {
                case 'A':
                    movieTicketSales.doOperation();
                    break;
                case 'B':
                    idiomSelection.doOperation();
                    break;
            }
        }
    }
}
