/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicapoo;

import java.io.Serializable;

/**
 *
 * @author Javierino
 */
public class Seat implements Serializable{

    private int row;
    private int col;

    //Constructor de la clase Seat
    public Seat(int row, int col) {
        this.row = row;
        this.col = col;
    }

    //Getter de la variable row
    public int getRow() {
        return row;
    }

    //Getter de la variable col
    public int getCol() {
        return col;
    }

    //toString de la clase Seat
    @Override
    public String toString() {
        return "Fila: " + row + " Asiento: " + col;
    }

    //Redefino equals y lo reeimplemento
    @Override
    public boolean equals(Object o) {
        Seat seat = (Seat) o;
        return ((this.row == seat.getRow()) && (this.col == seat.getCol()));
    }

    //Hago override de hashCode aunque no lo reeimplemento
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.row;
        hash = 97 * hash + this.col;
        return hash;
    }
}
