package gameplay;

import gameplay.Player;
import mapparser.Map;

import java.util.List;

import gameplay.GameInformation;

public class ReinforcementPhase extends GamePhase {

    private Player pl;
    private void determining_countries(){
        String l_playerName = pl.getPlayerName();
        List<Map.Country> p = pl.getConqueredCountries();
    }
    @Override
    public void executePhase(GameInformation p_game_information) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'executePhase'");
    }


    
    
}
