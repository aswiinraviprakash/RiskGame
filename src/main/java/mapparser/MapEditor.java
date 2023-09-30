/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package mapparser;

import constants.GameMessageConstants;
import gameplay.GameEngine;
import gameutils.GameException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author USER
 */
public class MapEditor {

    public Map initialiseMapEditingPhase(Map p_map) {
        try {

            BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.printf("Map Editor Menu!!!");

            
            int l_menu_option = Integer.parseInt(l_reader.readLine());
            do {

                switch (l_menu_option) {
                    case 1:
                        p_map = this.editContinent(p_map);
                        break;
                    case 2:
                        p_map = this.editCountry(p_map);
                        break;
                    case 3:
                        p_map = this.editBorders(p_map);
                        break;
                    case 4:
                        p_map.showMap();
                        break;
                    case 5:

                        break;

                    default:
                        System.out.println("Enter Valid Input!! Type 1 - Edit Continent - 2 - Edit Country - 3 - Edit Borders - 4 - Show Map  - 5 - Upload a map file - 6 - Exit");
                }

                System.out.printf("Type 1 - Edit Continent - 2 - Edit Country - 3 - Edit Borders - 4 - Show Map  - 5 - Upload a map file - 6 - Exit");
                l_menu_option = Integer.parseInt(l_reader.readLine());

            } while (l_menu_option != 6);

        //} catch (GameException e) {
           // System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(GameMessageConstants.D_INTERNAL_ERROR);
        }
         return p_map;
    }
    
    public Map editCountry(Map p_map){
        
        return p_map;
    }
    
    public Map editContinent(Map p_map){
        return p_map;
    }
    
    public Map editBorders(Map p_map){
        return p_map;
    }

}
