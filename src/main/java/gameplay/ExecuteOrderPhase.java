package gameplay;

import common.Phase;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Responsible for executing the orders issued by the players during the game.
 */
public class ExecuteOrderPhase extends Phase {

    /**
     * Contains number of armies.
     */
    private GameInformation d_current_game_info;

    /**
     * Method makes the game proceed to the next phase.
     * @return Reinforcement phase.
     * @throws Exception If there is an error in the execution or validation.
     */
    @Override
    public Phase nextPhase() throws Exception {
        return new ReinforcementPhase();
    }

    /**
     * Iterates over the player list and executes each player's orders one by one.
     * @throws Exception Throws exception if an error occurs.
     */
    @Override
    public void executePhase() throws Exception {
        System.out.printf("%nExecuting orders issued....%n");
        d_current_game_info = GameInformation.getInstance();

        LinkedHashMap<String, Player> l_player_list = d_current_game_info.getPlayerList();

        for (Map.Entry<String, Player> l_player : l_player_list.entrySet()) {
            try {
                Player l_player_obj = l_player.getValue();
                Order l_next_order = l_player_obj.next_order();

                while (l_next_order != null) {
                    l_next_order.execute(l_player_obj);
                    l_next_order = l_player_obj.next_order();
                }

            } catch (Exception e) {
                throw e;
            }
        }

        for (Map.Entry<String, Player> l_player : l_player_list.entrySet()) {
            int l_total_countries = d_current_game_info.getGameMap().getCountryObjects().size();
            Player l_current_player = l_player.getValue();
            if (l_total_countries == l_current_player.getConqueredCountries().size()) {
                System.out.println(l_current_player.getPlayerName() + " Won the Game !!!\n");
                d_current_game_info.setCurrentPhase(new EndGamePhase());
                return;
            }
        }

        d_current_game_info.resetCardIssued();

        d_current_game_info.setCurrentPhase(this.nextPhase());
    }
}
