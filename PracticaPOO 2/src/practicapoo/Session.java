/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicapoo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Javierino
 */
public class Session implements Serializable{
    private String hour;
    private Set<Seat> occupiedSeatSet = new HashSet<>(); 
    private Seat asiento;
    
    //Constructor vacio de la clase Sesion
    public Session() { 
    }

    //Constructor de la clase Sesion con hora
    public Session(String hour) {
        this.hour = hour;
    }

    //Getter de la variable hour
    public String getHour() {
        return hour;
    }

    //Getter de la variable ocuppiedSeatSet
    public Set<Seat> getOccupiedSeatSet() {
        return occupiedSeatSet;
    }

    //Redefino equals y lo reeimplemento
    @Override
    public boolean equals(Object obj) {
        Seat seat = (Seat) obj; 
        return (asiento.getRow() == seat.getRow()) && (asiento.getCol() == seat.getCol());
    }

    //Hago override de hashCode aunque no lo reeimplemento
    @Override
    public int hashCode() {
        return super.hashCode(); 
    }
    
    
    
}
