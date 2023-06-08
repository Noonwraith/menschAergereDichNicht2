package Controls;

import GUI.V2.MyFrame;
import GUI.V2.MyPanel;
import Logic.GameManager;
import Logic.Human;
import Logic.Player;

public class Control {

    private MyFrame gui = new MyFrame(this);
    private GameManager gameManager;
    private MyPanel panel = gui.getPanel();
    private Translate translate = new Translate();


    public Control(){
        startGame(4);
    }

    /**
     * Called by Human to give the GUI the future position.
     * @param player
     * @param futurePosition
     */
    public void displayFuturePiecePosition(Player player, int futurePosition) {
    }

    /**
     * Called by the GUI when the game is started.
     * ----------------------------Only if there is a button to restart the game. Otherwise the method is unnecessary.
     * ----------------------------If something has to happen to the GUI at game start, it can also be called here
     * @param numbersOfPlayer
     */
    public void startGame(int numbersOfPlayer){
        gameManager = new GameManager(numbersOfPlayer, this);

    }

    public void throwDice(){
        int steps = gameManager.throwsDice();
        if(steps != -1){
            //Gui Aufrufe
        }
    }

}
