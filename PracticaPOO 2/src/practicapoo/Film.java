/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicapoo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author Javierino
 */
public class Film implements Serializable {
    
    private String name;
    private File poster;
    private String description;

    //Constructor de la clase film
    public Film(File fileName) {
        this.poster =  fileName;
    }
    
    //Setter de la variable name
    public void setName(String name) {
        this.name = name;
    }
    
    //Getter de la variable name
    public String getName() {
        return name;
    }
    
    //Getter de la variable poster
    public File getPoster() {
        return poster;
    }
    
    //Getter de la variable description
    public String getDescription() {
        return description;
    }

    //Setter de la variable description
    public void setDescription(String description) {
        this.description = description;
    }
    
}
