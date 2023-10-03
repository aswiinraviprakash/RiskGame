/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gameutils;

/**
 * Custom exception that can be thrown to indicate issues related to map loading and parsing.
 */
public class MapException extends Exception {

    /**
     * @param p_message Error message describing the map-related issue.
     */
    public MapException(String p_message) {
        super(p_message);
    }
}
