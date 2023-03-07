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
public abstract class Operation {
    
    Multiplex multip;
    CinemaTicketDispenser cTD;
    
    //Constructor de la clase Operation, la cual es abstracta ya que varias 
    //clases heredaran de esta y definirán sus métodos
    public Operation(CinemaTicketDispenser dispenser,Multiplex multi) {
        this.cTD = dispenser;
        this.multip = multi;
    }
    
    //Método que se implementara en los hijos de la clase
    public abstract void doOperation();     
    
    //Método que se implementara en los hijos de la clase
    public abstract String getTitle();
    
    //Getter de la variable multip
    public Multiplex getMultiplex(){
     return multip;
    }
    
    //Getter de la variable cTD
    public CinemaTicketDispenser getDispenser(){        
     return cTD;
    }

    
}
