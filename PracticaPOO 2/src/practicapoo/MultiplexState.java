/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicapoo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import sienens.CinemaTicketDispenser;
import urjc.UrjcBankServer;

/**
 *
 * @author Javierino
 */
public class MultiplexState implements Serializable {

    private List<Theater> theaterList = new ArrayList<>();

    //Constructor ce la clase MultiplexState, en esta se hace la llamada para 
    //cargar los archivo de Peliculas y Sesiones
    public MultiplexState(CinemaTicketDispenser dispenser, Multiplex bankServer) throws FileNotFoundException {
        loadMoviesAndSessions();
    }

    //Método encargado de cargar los archivos de Peliculas y Sesiones
    private void loadMoviesAndSessions() throws FileNotFoundException {
        for (int i = 1; i <= 4; i++) {
            int v;
            File f1 = new File("./src/recursos/Movie" + i + ".txt");
            try ( Scanner lector = new Scanner(f1)) {
                Pattern p = Pattern.compile("[0-9]+");
                String encontradoS = lector.findInLine(p);
                v = (Integer) Integer.parseInt(encontradoS);
            }
            File f2 = new File("./src/recursos/Theater" + v + ".txt");
            Theater t = new Theater(f1, f2);
            theaterList.add(t);
        }
    }

    //Getter que recibe cada teatro de la lista
    public Theater getTheather(int i) {
        return getTheatherList().get(i);
    }

    //Getter que dice el número de teatros
    public int getNumberOfThetahers() {
        return theaterList.size();
    }

    //Getter de la variable theatherList
    public List<Theater> getTheatherList() {
        return theaterList;
    }

}
