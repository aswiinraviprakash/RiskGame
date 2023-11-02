package gameplay;

public class EndGamePhase extends Phase {

    @Override
    public Phase nextPhase() {
        return null;
    }

    @Override
    public void executePhase(GameInformation p_game_information) {
        System.out.println("----GAME TERMINATED----");
    }

}
