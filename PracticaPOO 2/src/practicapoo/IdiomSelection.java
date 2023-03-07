/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicapoo;

import sienens.CinemaTicketDispenser;

/**
 *
 * @author Javierino
 */
public class IdiomSelection extends Operation {

    //Constructor de la clase IdiomSelection, uso super ya que esta hereda de Operation
    public IdiomSelection(CinemaTicketDispenser dispenser, Multiplex multi) {
        super(dispenser, multi);
    }

    //Metodo heredado de Operation, se define aqui la implementacion de la clase
    //En esta clase se establecen los idiomas, y que al seleccionarlos se cambie de idioma
    @Override
    public void doOperation() {
        String lenguaje = super.getMultiplex().getIdiom();
        CinemaTicketDispenser cinemaTicketDispenser = super.getDispenser();
        cinemaTicketDispenser.setMenuMode();
        cinemaTicketDispenser.setTitle(this.getTitle());
        cinemaTicketDispenser.setOption(0, java.util.ResourceBundle
                .getBundle("properties/" + lenguaje).getString("ESPAÑOL"));
        cinemaTicketDispenser.setOption(1, java.util.ResourceBundle
                .getBundle("properties/" + lenguaje).getString("INGLES"));
        cinemaTicketDispenser.setOption(2, java.util.ResourceBundle
                .getBundle("properties/" + lenguaje).getString("EUSKERA"));
        cinemaTicketDispenser.setOption(3, java.util.ResourceBundle
                .getBundle("properties/" + lenguaje).getString("CATALAN"));
        cinemaTicketDispenser.setOption(4, java.util.ResourceBundle
                .getBundle("properties/" + lenguaje).getString("CANCELAR"));
        cinemaTicketDispenser.setOption(5, null);

        char option = cinemaTicketDispenser.waitEvent(30);
        switch (option) {
            case '1' -> {
                cinemaTicketDispenser.retainCreditCard(false);
                cinemaTicketDispenser.expelCreditCard(1);
            }
            case 'A' -> {
                super.getMultiplex().setIdiom("Español");
                super.getMultiplex().operation.doOperation();
            }
            case 'B' -> {
                super.getMultiplex().setIdiom("English");
                super.getMultiplex().operation.doOperation();
            }
            case 'C' -> {
                super.getMultiplex().setIdiom("Euskera");
                super.getMultiplex().operation.doOperation();
            }
            case 'D' -> {
                super.getMultiplex().setIdiom("Catalan");
                super.getMultiplex().operation.doOperation();
            }
            case 'E' -> super.getMultiplex().operation.doOperation();

        }
    }

    //Método heredado de Operation, se define el título de la clase que se le pasará al dispensador
    @Override
    public String getTitle() {
        return java.util.ResourceBundle
                .getBundle("properties/" + super.getMultiplex().getIdiom())
                .getString("CAMBIAR IDIOMA");
    }

}
