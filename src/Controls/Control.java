package Controls;

import Debuging.Debug;
import GUI.V2.MyFrame;
import GUI.V2.MyPanel;
import Logic.Board;
import Logic.GameManager;
import Logic.Player;

public class Control {

    private boolean debugOn;
    private Debug debug;



    private final MyFrame gui = new MyFrame(this);
    private GameManager gameManager;
    private final MyPanel panel = gui.getPanel();
    private final Translate translate = new Translate(this);


    public Control(boolean debugOn){
        this.debugOn = debugOn;
        debug  = new Debug(this);
        //panel.playerTurn(3);
        startGame(4);
        if(debugOn) {
            System.out.println("Control: Game is simulated\n");
            debug.simulateGame();
        }

    }

    /**
     * Called by Human to give the GUI the future position.
     * @param player
     * @param futurePosition
     */
    public void displayFuturePiecePosition(Player player, int futurePosition) {
        panel.futureMovePosition(translate.boardPositionToGuiPosition(futurePosition));
    }

    public void updateBoard(Board board){
        translate.updateBoard(panel, board);
    }

    public void movePiece(int position, int color){
        System.out.println("Control: move to position: "+position+" with color: "+color);
        panel.updateField(translate.boardPositionToGuiPosition(position), color);

        panel.setDiceNumber(0);

        if(debugOn) {
            panel.removeAllX();
        }

    }

    public void playerTurn(int color){
        System.out.println("Control: --------------------------------------next Player------------------");
        System.out.println("Control: currentPlayer: "+color);
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

    public void throwDice(int debugSteps){
        int steps = gameManager.throwsDice(debugSteps);
        //System.out.println("Control: Dice throw: "+steps);

        //debug.addMethode("throwDice", steps);

        panel.setDiceNumber(steps);

    }

    public void fieldSelected(int field){
        System.out.println("Control: Click on Field: "+field+" position: "+translate.GuiPositionToBoardPosition(field));
        debug.addMethode("fieldSelected", field);
        gameManager.clickOnPiece(translate.GuiPositionToBoardPosition(field));
    }

    public void saveDiceNumberinDebug(int number){
        debug.addMethode("throwDice", number);
    }


    public void clearDice(){
        panel.setDiceNumber(0);
    }

    public void setDice(int steps){
        System.out.println("Control: setDice: "+steps);
        panel.setDiceNumber(steps);
    }


    public void removeAllX(){
        panel.removeAllX();
    }

    public boolean isDebugOn(){
        return debugOn;
    }

    public void setDebugOn(boolean debugOn) {
        this.debugOn = debugOn;
    }
}
