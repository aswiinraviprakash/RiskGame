package gameplay;

import common.Phase;

/**
 * Class deals with the End game phase, which is the final phase.
 */
public class EndGamePhase extends Phase {

    /**
     * Method is used to proceed to the next phase.
     * @return null.
     */
    @Override
    public Phase nextPhase() {
        return null;
    }

    /**
     * Method deals with the End game phase.
     */
    @Override
    public void executePhase() {
        System.out.println("----GAME TERMINATED----");

        if (GameInformation.getInstance().getGameMap() != null) {
            System.out.println("----Game Results----");
            GameInformation.getInstance().getGameMap().showMap(true);
        }
    }

}
