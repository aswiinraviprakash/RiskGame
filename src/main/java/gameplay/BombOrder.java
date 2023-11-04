package gameplay;

import java.util.List;

import mapparser.GameMap;

public class BombOrder extends Order {


    
    private String d_target_country_name;


    private GameInformation d_current_game_info;

    public BombOrder(String p_target_country_name){

        this.d_target_country_name = p_target_country_name;
    }
    
        @Override

        public void execute(Player p_player) throws Exception {

            int l_armies_count = -1;

            List<GameMap.Country> l_countries_conquered = p_player.d_conquered_countries;

            if(p_player.checkIfCountryConquered(d_target_country_name)==false){
                for(int l_index = 0;l_index<l_countries_conquered.size();l_index++){
                    
                    l_armies_count = l_countries_conquered.get(l_index).getArmyCount();
                }
            } else{
                System.out.println("Player can't bomb their own Country!!");
                return;
            }


            for(int l_index = 0;l_index<d_current_game_info.getGameMap().getCountryObjects().size();l_index++){

                if(d_current_game_info.getGameMap().getCountryObjects().get(l_index).getCountryName().compareTo(d_target_country_name)==0){

                    d_current_game_info.getGameMap().getCountryObjects().get(l_index).setArmyCount(l_armies_count/2);
                }
            }
                
            }


        }

