package gameplay;

import common.Phase;

public class EndGamePhase extends Phase {

    @Override
    public Phase nextPhase() {
        return null;
    }

    @Override
    public void executePhase() {
        System.out.println("----GAME TERMINATED----");

        if (GameInformation.getInstance().getGameMap() != null) {
            System.out.println("----Game Results----");
            GameInformation.getInstance().getGameMap().showMap(true);
        }
    }

}
