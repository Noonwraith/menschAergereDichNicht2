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
    private MyFrame gui;
    private GameManager gameManager;
    private final MyPanel panel;
    private final Translate translate = new Translate(this);

    public Control(boolean debugOn){
        System.out.println("Start game");
        this.debugOn = debugOn;
        this.gui = new MyFrame(this);
        panel = gui.getPanel();
        debug  = new Debug(this);
        startGame(4);
        if(debugOn) {
            System.out.println("Control: Game is simulated");
            debug.simulateGame();
        }
    }

    public Control(boolean debugOn, MyFrame gui){
        System.out.println("Start new game");
        this.debugOn = debugOn;
        this.gui = gui;
        panel = gui.getPanel();
        gui.setControl(this);
        panel.setControl(this);

        debug  = new Debug(this);
        startGame(4);
        if(debugOn) {
            System.out.println("Control: Game is simulated");
            debug.simulateGame();
        }
        resetGuiBoard();


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
        panel.setDiceNumber(steps);
    }

    public void fieldSelected(int field){
        System.out.println("Control: Click on Field: "+field+" position: "+translate.GuiPositionToBoardPosition(field));
        debug.addMethode("fieldSelected", field);
        gameManager.clickOnPiece(translate.GuiPositionToBoardPosition(field));
    }


    public void playerWin(int player, int place){
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Control: Player "+ player +" has the "+place+" place!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        sendMessageToPlayer("You got "+place+" place", player);
    }

    public void saveDiceNumberInDebug(int number){
        debug.addMethode("throwDice", number);
    }

    public void newGame(){
        Control control = new Control(debugOn, gui);
    }

    public void clearDice(){
        panel.setDiceNumber(0);
    }

    public void setDice(int steps){
        panel.setDiceNumber(steps);
    }

    public void setLastDiceThrow(int player, int number){
        panel.setLastDiceThrow(player, number);
    }

    public void removeAllX(){
        panel.removeAllX();
    }

    public void sendMessageToPlayer(String msg, int player){
        panel.sendMessageToPlayer(msg, player);
    }

    public boolean isDebugOn(){
        return debugOn;
    }

    public void setDebugOn(boolean debugOn) {
        this.debugOn = debugOn;
    }

    public void nextPlayer(){
        debug.addMethode("nextPlayer");
        gameManager.nextPlayer(true);
    }

    public void resetGuiBoard(){
        for(int i=0;i<gameManager.getBoard().getField().length;i++){
            panel.updateField(translate.boardPositionToGuiPosition(i), -1);
        }
        for(int i=0;i<gameManager.getBoard().getHouse().length;i++){
            panel.updateField(translate.boardPositionToGuiPosition(i), -1);
        }
        for(int i=0; i<4; i++){
            panel.sendMessageToPlayer("no Message inbox", i);
            panel.setLastDiceThrow(i, 0);
        }

    }

    public int getCurrentPlayerFromGameManager(){
        return gameManager.getCurrentPlayer();
    }
}