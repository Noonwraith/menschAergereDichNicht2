package Controls;

import GUI.V2.MyFrame;
import GUI.V2.MyPanel;
import Logic.Board;
import Logic.GameManager;
import Logic.Player;

public class Control {

    private final MyFrame gui = new MyFrame(this);
    private GameManager gameManager;
    private final MyPanel panel = gui.getPanel();
    private final Translate translate = new Translate(this);


    public Control(){
        //panel.playerTurn(3);
        startGame(4);

    }

    /**
     * Called by Human to give the GUI the future position.
     * @param player
     * @param futurePosition
     */
    public void displayFuturePiecePosition(Player player, int futurePosition) {
        panel.futureMovePosition(futurePosition);
    }

    public void updateBoard(Board board){
        translate.updateBoard(panel, board);
    }


    public void playerTurn(int color){
        panel.playerTurn(color);
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

    public int throwDice(){
        int steps = gameManager.throwsDice();
        return steps;
    }

    public void fieldSelected(int field){
        gameManager.clickOnPiece(translate.GuiPositionToBoardPosition(field));

    }


}
