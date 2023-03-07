/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicapoo;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import sienens.CinemaTicketDispenser;

/**
 *
 * @author Javierino
 */
public class MovieTicketSales extends Operation implements Serializable {

    private final MultiplexState multiplexState;
    private char option, option2;
    private char option3, option4;
    private char option5;
    private int cont = 0;
    private int precio;
    private byte row, col;
    private final CinemaTicketDispenser cinemaTicketDispenser = super.getDispenser();
    private Theater theater;
    private Session session;
    private Seat seat;
    private Set<Seat> conjuntoAsientos = new HashSet<>();
    private List<String> impresion;
    private ArrayList<List<String>> listaAImprimir;

    //Constructor de la clase MovieTicketSales, ya que hereda de Operation uso super
    //En esta clase se deserializa es multiplexState si esta creado, si no se inicializa
    public MovieTicketSales(CinemaTicketDispenser dispenser, Multiplex multi) throws FileNotFoundException, ClassNotFoundException, IOException {
        super(dispenser, multi);
        File backup = new File("./src/recursos/backup.dat");
        if (backup.canRead()) {
            multiplexState = deserializeMultiplexState();
        } else {
            this.multiplexState = new MultiplexState(super.getDispenser(), super.getMultiplex());
        }
    }

    //Metodo heredado de Operation, se define aqui la implementacion de la clase
    @Override
    public void doOperation() {
        cinemaTicketDispenser.setImage("");
        cinemaTicketDispenser.setDescription("");
        cinemaTicketDispenser.setMenuMode();
        cinemaTicketDispenser.setTitle(java.util.ResourceBundle
                .getBundle("properties/" + super.getMultiplex().getIdiom())
                .getString("MENU DE COMPRAR ENTRADAS"));
        cinemaTicketDispenser.setOption(0, java.util.ResourceBundle
                .getBundle("properties/" + super.getMultiplex().getIdiom())
                .getString("ELEGIR SALA"));
        cinemaTicketDispenser.setOption(1, java.util.ResourceBundle
                .getBundle("properties/" + super.getMultiplex().getIdiom())
                .getString("VOLVER AL MENÚ PRINCIPAL"));
        cinemaTicketDispenser.setOption(2, null);
        cinemaTicketDispenser.setOption(3, null);
        cinemaTicketDispenser.setOption(4, null);
        option = cinemaTicketDispenser.waitEvent(30);
        switch (option) {
            case 'A' -> {
                theater = selectTheather();
                session = selectSession(theater);
                try {
                    selectSeats(session);
                } catch (IOException ex) {
                    System.err.println("Error: " + ex.getMessage());
                }
            }
            case 'B' ->
                super.getMultiplex().operation.doOperation();
        }
    }

    //Método heredado de Operation, se define el título de la clase que se le pasará al dispensador
    @Override
    public String getTitle() {
        return java.util.ResourceBundle
                .getBundle("properties/" + getMultiplex().getIdiom())
                .getString("COMPRAR ENTRADAS");
    }

    //Método encargado de seleccionar la sala elegida
    private Theater selectTheather() {
        List<Theater> listaSalas = multiplexState.getTheatherList();
        cinemaTicketDispenser.setTitle(java.util.ResourceBundle
                .getBundle("properties/" + super.getMultiplex().getIdiom())
                .getString("ELIGE PELICULA"));

        for (int i = 0; i < listaSalas.size(); i++) {
            theater = listaSalas.get(i);
            Film film = theater.getFilm();
            cinemaTicketDispenser.setOption(i, film.getName());
        }

        cinemaTicketDispenser.setOption(4, java.util.ResourceBundle
                .getBundle("properties/" + super.getMultiplex()
                        .getIdiom()).getString("VOLVER A COMPRAR ENTRADAS"));

        option2 = cinemaTicketDispenser.waitEvent(30);
        switch (option2) {
            case 'A' ->
                theater = (Theater) listaSalas.get(0);
            case 'B' ->
                theater = (Theater) listaSalas.get(1);
            case 'C' ->
                theater = (Theater) listaSalas.get(2);
            case 'D' ->
                theater = (Theater) listaSalas.get(3);
            case 'E' ->
                doOperation();

        }
        cinemaTicketDispenser.setImage(String.valueOf(theater.getFilm().getPoster()));
        cinemaTicketDispenser.setDescription(theater.getFilm().getDescription());
        return theater;
    }

    //Método encargado de seleccionar las butacas elegidas
    private Set<Seat> selectSeats(Session s) throws IOException {
        conjuntoAsientos = new HashSet<Seat>();
        listaAImprimir = new ArrayList<>();
        cont = 0;
        cinemaTicketDispenser.setTitle(java.util.ResourceBundle
                .getBundle("properties/" + super.getMultiplex().getIdiom())
                .getString("SELECCIONE BUTACAS"));
        cinemaTicketDispenser.setTheaterMode(theater.getMaxRows(),
                theater.getMaxCols());
        cinemaTicketDispenser.setOption(0, java.util.ResourceBundle
                .getBundle("properties/" + super.getMultiplex().getIdiom())
                .getString("CONFIRMAR"));
        cinemaTicketDispenser.setOption(1, java.util.ResourceBundle
                .getBundle("properties/" + super.getMultiplex().getIdiom())
                .getString("CANCELAR BUTACAS"));
        presentSeats();
        while (cont < 4) {
            option5 = cinemaTicketDispenser.waitEvent(30);
            if (option5 == '1'){
             cinemaTicketDispenser.retainCreditCard(false);
            }
            else if (option5 == 'A') {
                if (cont > 0) {
                   performPayment();
                   this.conjuntoAsientos = conjuntoAsientos;
                   super.getMultiplex().operation.doOperation();
                } else {
                    cinemaTicketDispenser.setTitle(java.util.ResourceBundle
                            .getBundle("properties/" + super.getMultiplex().getIdiom())
                            .getString("SELECCIONE UN ASIENTO"));
                }
                
            } else if (option5 == 'B') {
                session.getOccupiedSeatSet().clear();
                conjuntoAsientos.clear();
                doOperation();
            } else if (option5 != 0) {
                col = (byte) (option5 & 0xFF);
                row = (byte) ((option5 & 0xFF00) >> 8);
                cinemaTicketDispenser.setTitle(java.util.ResourceBundle
                        .getBundle("properties/" + super.getMultiplex().getIdiom())
                        .getString("FILA: ") + row + " " + java.util.ResourceBundle
                                .getBundle("properties/" + super.getMultiplex()
                                        .getIdiom()).getString("ASIENTO: ") + col);
                cinemaTicketDispenser.markSeat(row, col, 1);
                seat = new Seat(row, col);
                if (conjuntoAsientos.contains(seat) == false && (session.getOccupiedSeatSet().contains(seat)) == false) {
                    rellenarTickets(theater, s, seat);
                    conjuntoAsientos.add(seat);
                    cont++;
                } else {
                    cinemaTicketDispenser.setTitle(java.util.ResourceBundle
                            .getBundle("properties/" + super.getMultiplex().getIdiom())
                            .getString("DEBE SELECCIONAR UNA BUTACA LIBRE"));
                }
            }
        }

        while (cont < 5) {
            if (cont == 4) {
                cinemaTicketDispenser.setTitle(java.util.ResourceBundle.getBundle("properties/" + super.getMultiplex().getIdiom()).getString("TODAS OCUPADAS"));
            }
            char option6 = cinemaTicketDispenser.waitEvent(30);
            switch (option6) {
                case 'A' -> {
                    this.conjuntoAsientos = conjuntoAsientos;
                    performPayment();
                    super.getMultiplex().operation.doOperation();
                }

                case 'B' -> {
                    session.getOccupiedSeatSet().clear();
                    conjuntoAsientos.clear();
                    doOperation();
                }
            }
        }
        return conjuntoAsientos;
    }

    //Método encargado de seleccionar la sesion elegida
    private Session selectSession(Theater t) {
        List<Session> listaSesiones = t.getSessionList();

        cinemaTicketDispenser.setTitle(java.util.ResourceBundle
                .getBundle("properties/" + super.getMultiplex().getIdiom())
                .getString("ELIGE UNA SESIÓN"));

        for (int i = 0; i < listaSesiones.size(); i++) {
            session = listaSesiones.get(i);
            cinemaTicketDispenser.setOption(i, session.getHour());
        }

        if (listaSesiones.size() == 3) {
            cinemaTicketDispenser.setOption(3, java.util.ResourceBundle
                    .getBundle("properties/" + super.getMultiplex()
                            .getIdiom()).getString("VOLVER A COMPRAR ENTRADAS"));
            cinemaTicketDispenser.setOption(4, null);

            option3 = cinemaTicketDispenser.waitEvent(30);
            switch (option3) {
                case 'A' ->
                    session = listaSesiones.get(0);
                case 'B' ->
                    session = listaSesiones.get(1);
                case 'C' ->
                    session = listaSesiones.get(2);
                case 'D' -> {
                    cinemaTicketDispenser.setImage("");
                    cinemaTicketDispenser.setDescription("");
                    doOperation();
                }
            }
        } else if (listaSesiones.size() == 4) {
            cinemaTicketDispenser.setOption(4, java.util.ResourceBundle
                    .getBundle("properties/" + super.getMultiplex()
                            .getIdiom()).getString("VOLVER A COMPRAR ENTRADAS"));

            option4 = cinemaTicketDispenser.waitEvent(30);
            switch (option4) {
                case 'A' ->
                    session = listaSesiones.get(0);
                case 'B' ->
                    session = listaSesiones.get(1);
                case 'C' ->
                    session = listaSesiones.get(2);
                case 'D' ->
                    session = listaSesiones.get(2);
                case 'E' -> {
                    cinemaTicketDispenser.setImage("");
                    cinemaTicketDispenser.setDescription("");
                    doOperation();
                }
            }
        }
        return session;
    }

    //Método encargado de llamar a la clase PerformPayment, también se encarga
    //de hacer la llamada a serializar el MultiplexState
    private boolean performPayment() throws IOException {
        precio = computePrice();
        PerformPayment performPayment
                = new PerformPayment(super.getDispenser(), super.getMultiplex());
        performPayment.doOperation();
        serializeMuktiplexState();
        return false;
    }

    //Método encargado de presentar los asientos en fúncion de la sala elegida
    private void presentSeats() {
        for (int i = 1; i <= theater.getMaxRows(); i++) {
            for (int j = 1; j <= theater.getMaxCols(); j++) {
                cinemaTicketDispenser.markSeat(i, j, 0);
            }
        }

        for (Seat butaca : theater.getSeatSet()) {
            cinemaTicketDispenser.markSeat(butaca.getRow(), butaca.getCol(), 2);
        }

        for (Seat butacaElegidas : session.getOccupiedSeatSet()) {
            cinemaTicketDispenser.markSeat(butacaElegidas.getRow(), butacaElegidas.getCol(), 1);
        }
    }

    //Método encargado de calcular el precio de los tickets comprados
    private int computePrice() {
        return theater.getPrice() * cont;
    }

    //Método encargado de serializar el MultiplexState
    public void serializeMuktiplexState() throws FileNotFoundException, IOException {
        File backup = new File("./src/recursos/backup.dat");
        FileOutputStream fileOutputStream = new FileOutputStream(backup);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(multiplexState);
    }

    //Método encargado de deserializar el MultiplexState
    public MultiplexState deserializeMultiplexState() throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        fileInputStream = new FileInputStream("./src/recursos/backup.dat");
        objectInputStream = new ObjectInputStream(fileInputStream);
        MultiplexState backup = (MultiplexState) objectInputStream.readObject();
        return backup;

    }

    //Método encargado de rellenar cada ticket comprado
    public void rellenarTickets(Theater t, Session ses, Seat s) {
        impresion = new ArrayList<>();
        impresion.add(java.util.ResourceBundle
                .getBundle("properties/" + super.getMultiplex().getIdiom())
                .getString(" ENTRADAS PARA ") + theater.getFilm().getName());
        impresion.add(" ===================");
        impresion.add(java.util.ResourceBundle.getBundle("properties/" + getMultiplex().getIdiom()).getString("   SALA ") + theater.getNumber());
        impresion.add("   " + session.getHour());
        impresion.add(java.util.ResourceBundle.getBundle("properties/" + getMultiplex().getIdiom()).getString("   FILA ") + seat.getRow());
        impresion.add(java.util.ResourceBundle.getBundle("properties/" + getMultiplex().getIdiom()).getString("   ASIENTO ") + seat.getCol());
        impresion.add(java.util.ResourceBundle.getBundle("properties/" + getMultiplex().getIdiom()).getString("   PRECIO ") + theater.getPrice() + "€");
        listaAImprimir.add(impresion);
    }

    
    //Getter de la variable cont
    public int getCont() {
        return cont;
    }

    //Getter de la variable theater
    public Theater getTheater() {
        return theater;
    }

    //Getter de la variable precio
    public int getPrecio() {
        return precio;
    }

    //Getter de la variable seat
    public Seat getSeat() {
        return seat;
    }

    //Getter de la variable conjuntoAsientos
    public Set<Seat> getConjuntoAsientos() {
        return conjuntoAsientos;
    }

    //Getter de la variable session
    public Session getSession() {
        return session;
    }

    //Getter de la variable listaAImprimir
    public ArrayList<List<String>> getListaAImprimir() {
        return listaAImprimir;
    }

}
