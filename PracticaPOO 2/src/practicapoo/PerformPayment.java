/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicapoo;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.CommunicationException;
import sienens.CinemaTicketDispenser;
import urjc.UrjcBankServer;

/**
 *
 * @author Javierino
 */
public class PerformPayment extends Operation implements Serializable {

    private UrjcBankServer bank = new UrjcBankServer();
    private CinemaTicketDispenser cinemaTicketDispenser = super.getDispenser();
    private int contador = super.getMultiplex().operation.movieTicketSales.getCont();
    private String nombrePelicula = super.getMultiplex().operation.movieTicketSales.getTheater().getFilm().getName();
    private int precioPelicula = super.getMultiplex().operation.movieTicketSales.getPrecio();

    //Constructor de la clase PerformPayment, usa super ya que hereda de Operation
    public PerformPayment(CinemaTicketDispenser dispenser, Multiplex multi) {
        super(dispenser, multi);
    }

    //Método heredado de Operation, se define el título de la clase que se le pasará al dispensador
    @Override
    public String getTitle() {
        return java.util.ResourceBundle
                .getBundle("properties/" + super.getMultiplex().getIdiom())
                .getString("INSERTE LA TARJETA DE CRÉDITO");
    }

    //Metodo heredado de Operation, se define aqui la implementacion de la clase
    @Override
    public void doOperation() {
        cinemaTicketDispenser.setMessageMode();
        cinemaTicketDispenser.setTitle(this.getTitle());
        cinemaTicketDispenser.setDescription(contador + java.util.ResourceBundle
                .getBundle("properties/" + super.getMultiplex().getIdiom())
                .getString("ENTRADAS PARA") + nombrePelicula
                + ": " + precioPelicula + "€ ");
        cinemaTicketDispenser.setOption(0, null);
        cinemaTicketDispenser.setOption(1, java.util.ResourceBundle
                .getBundle("properties/" + super.getMultiplex().getIdiom())
                .getString("CANCELAR"));

        char tarjeta = cinemaTicketDispenser.waitEvent(30);
        if (tarjeta == '1') {
            cinemaTicketDispenser.retainCreditCard(false);
            cinemaTicketDispenser.setDescription(contador + java.util.ResourceBundle
                    .getBundle("properties/" + super.getMultiplex().getIdiom())
                    .getString("ENTRADAS PARA") + nombrePelicula
                    + ": " + precioPelicula + "€ ");
            cinemaTicketDispenser.setOption(0, java.util.ResourceBundle
                    .getBundle("properties/" + super.getMultiplex().getIdiom())
                    .getString("ACEPTAR"));
            cinemaTicketDispenser.setOption(1, java.util.ResourceBundle
                    .getBundle("properties/" + super.getMultiplex().getIdiom())
                    .getString("CANCELAR"));
            char option = cinemaTicketDispenser.waitEvent(30);
            switch (option) {
                case 'A':
                    if (bank.comunicationAvaiable()) {
                        try {
                            bank.doOperation(cinemaTicketDispenser.getCardNumber(), precioPelicula);
                            for (Seat s : super.getMultiplex().operation.movieTicketSales.getConjuntoAsientos()) {
                                super.getMultiplex().operation.movieTicketSales.getSession().getOccupiedSeatSet().add(s);
                            }
                            for (int i = 0; i < super.getMultiplex().operation.movieTicketSales.getListaAImprimir().size(); i++) {
                                cinemaTicketDispenser.print(super.getMultiplex().operation.movieTicketSales.getListaAImprimir().get(i));
                            }
                            cinemaTicketDispenser.expelCreditCard(40);
                        } catch (CommunicationException ex) {
                            System.err.println("Error:" + ex.getMessage());
                        }
                    }
                    break;
                case 'B':
                    if (cinemaTicketDispenser.expelCreditCard(10)) {
                        cinemaTicketDispenser.retainCreditCard(true);
                        for (Seat s : super.getMultiplex().operation.movieTicketSales.getConjuntoAsientos()) {
                            cinemaTicketDispenser.markSeat(s.getRow(), s.getCol(), 2);
                        }
                        super.getMultiplex().operation.doOperation();
                    } else {
                        cinemaTicketDispenser.setTitle(java.util.ResourceBundle
                                .getBundle("properties/" + super.getMultiplex()
                                        .getIdiom()).getString("RETIRE LA TARJETA"));
                        cinemaTicketDispenser.setOption(0, null);
                        cinemaTicketDispenser.setOption(1, null);
                        cinemaTicketDispenser.setOption(2, null);
                        cinemaTicketDispenser.setOption(3, null);
                        cinemaTicketDispenser.setOption(4, null);
                        cinemaTicketDispenser.setOption(5, null);
                        cinemaTicketDispenser.retainCreditCard(true);
                    }

                    break;
            }
        } else {
            super.getMultiplex().operation.doOperation();
        }
    }

}
