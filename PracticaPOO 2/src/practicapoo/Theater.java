/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicapoo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.*;

/**
 *
 * @author Javierino
 */
public class Theater implements Serializable {

    private int number;
    private int price;
    private Set<Seat> seatSet = new HashSet<>();
    private Film film;
    private List<Session> listaSesiones = new ArrayList<>();

    private File fichero1, fichero2;

    private Pattern patronNumber;
    private Pattern patronImagen;
    private Pattern patronSesiones;
    private Pattern patronCompleto;
    private Pattern patronButacas;

    //Constructor de la clase Theater, en esta llamo a los métodos lecturaTeatro(), lecturaFilm() y loadSets()
    public Theater(File fileName1, File fileName2) {
        fichero1 = fileName1;
        fichero2 = fileName2;
        lecturaTeatro(fileName1);
        lecturaFilm(fileName1);
        loadSeats();
    }

    //Getter de la variable number
    public int getNumber() {
        return number;
    }

    //Getter de la variable price
    public int getPrice() {
        return price;
    }

    //Getter de la variable seatSet
    public Set<Seat> getSeatSet() {
        return seatSet;
    }

    //Getter de la variable film
    public Film getFilm() {
        return film;
    }

    //Getter de la variable listaSesiones
    public List<Session> getSessionList() {
        return listaSesiones;
    }

    //Método que encuentra el máximo de filas de un fichero Theatre
    public int getMaxRows() {
        int cont = 1;
        try {
            Scanner reader = new Scanner(fichero2);
            while (reader.hasNextLine()) {
                reader.nextLine();
                cont++;
            }

        } catch (FileNotFoundException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
        return cont - 1;
    }

    //Método que encuentra el máximo de columnas de un fichero Theatre
    public int getMaxCols() {
        int contCol = 0;
        int contMaxNumCol = 0;
        Scanner reader;
        try {
            reader = new Scanner(fichero2);
            while (reader.hasNextLine()) {
                String linea = reader.nextLine();
                char[] lineaC = linea.toCharArray();
                contCol = lineaC.length;
                if (contCol >= contMaxNumCol) {
                    contMaxNumCol = contCol;
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
        return contMaxNumCol;
    }

    //Método encargado de cargar los asientos en funcion del archivo Theatre
    private void loadSeats() {
        Scanner reader;
        try {
            reader = new Scanner(fichero2);
            for (int i = 1; i <= getMaxRows(); i++) {
                char[] arrayCaracteres = reader.nextLine().toCharArray();
                for (int j = 0; j < arrayCaracteres.length; j++) {
                    if (arrayCaracteres[j] == '*') {
                        Seat butaca = new Seat(i, j + 1);
                        seatSet.add(butaca);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Error: " + ex.getMessage());
        }

    }

    //Método encargado de leer los teatros
    private void lecturaTeatro(File fileName1) {
        // Empiezo a leer el fichero para guardar los atributos de Teatro
        Scanner reader;
        try {
            reader = new Scanner(fileName1);
            patronNumber = Pattern.compile("[0-9]+");
            patronSesiones = Pattern.compile("([0-9][0-9]:[0-9][0-9])");
            patronCompleto = Pattern.compile("\s[0-9 a-zA-Z-: -.,_áéíóúñü]+");
            patronImagen = Pattern.compile("[a-zA-Z]+/.[a-zA-Z]+");

            //Leo el numero del teatro
            String sala = reader.findInLine(patronNumber);
            number = (Integer) Integer.parseInt(sala);
            //Leo las sesiones saltando 5 lineas hasta encontrar la buscada
            for (int i = 1; i <= 6; i++) {
                reader.nextLine();
            }
            String sesionesEncontradas = reader.findInLine(patronCompleto);
            Matcher matcher = patronSesiones.matcher(sesionesEncontradas);
            while (matcher.find()) {
                String sesionS = matcher.group();
                Session sesion = new Session(sesionS);
                listaSesiones.add(sesion);
            }

            //Leo el precio por entrada
            for (int i = 1; i <= 4; i++) {
                reader.nextLine();
            }
            String precioEncontrado = reader.findInLine(patronNumber);
            price = (Integer) Integer.parseInt(precioEncontrado);

            reader.close();
        } catch (FileNotFoundException ex) {
            System.err.println("Error: " + ex.getMessage());
        }

    }

    //Método encargado de leer las peliculas
    private void lecturaFilm(File fileName1) {
        // Aqui empiezo a leer el fichero de nuevo para los atributos de Film
        //Leo el poster y creo la pelicula del teatro
        Scanner reader;
        try {
            reader = new Scanner(fileName1);
            patronNumber = Pattern.compile("[0-9]+");
            patronSesiones = Pattern.compile("([0-9][0-9]:[0-9][0-9])");
            patronCompleto = Pattern.compile("\s[0-9 a-zA-Z-: -.,_áéíóúñü]+");
            patronImagen = Pattern.compile("[a-zA-Z]+\\.[a-zA-Z]+");
            for (int i = 1; i <= 8; i++) {
                reader.nextLine();
            }
            String imagenEncontrada = reader.findInLine(patronImagen);
            File fileName3 = (File) new File("./src/recursos/" + imagenEncontrada);
            film = new Film(fileName3);
            reader.close();
            
            //Leo el titulo de la pelicula del teatro
            reader = new Scanner(fileName1);
            for (int i = 1; i <= 2; i++) {
                reader.nextLine();
            }
            String nombreEncontrado = reader.findInLine(patronCompleto);
            film.setName(nombreEncontrado);

            for (int i = 1; i <= 2; i++) {
                reader.nextLine();
            }

            String descripcionEncontrada = reader.findInLine(patronCompleto);
            film.setDescription(descripcionEncontrada);
        } catch (FileNotFoundException ex) {
            System.err.println("Error: " + ex.getMessage());
        }

    }
}
