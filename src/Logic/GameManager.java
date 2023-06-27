package Logic;

import Controls.Control;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Who's turn is it?
 * Who wins?
 */

public class GameManager {
    private Control control;
    private Dice dice = new Dice();
    private Board board = new Board();
    private Human players[] = new Human[4];
    private int currentPlayer = 0;
    private int numbersOfPlayer;
    private int playerRoll3Times = 3;
    private boolean startGame = false;
    private int[] startNumbers = new int[4];
    private int[] winningSequence = new int[4];

    public GameManager(int numbersOfPlayer, Control control){
        this.numbersOfPlayer = numbersOfPlayer;
        this.control = control;
        for(int i=0; i<numbersOfPlayer; i++){
            players[i] = new Logic.Human(i, board, control);
        }
        control.playerTurn(currentPlayer);
        control.updateBoard(board);
    }

    /**
     * Fetches the piece from the board using the position and calls the clickOnPiece from Human.
     * Checks if the piece has been moved.
     * @param position
     */
    public boolean clickOnPiece(int position){
        Piece piece = board.getPieceOfPosition(position);
        if(piece != null && startGame) {
            int color = piece.getColor();
            if (color == currentPlayer && dice.isLock()) {
                int code = players[color].clickOnPiece(piece);
                if (code == 1) {
                    if(playerWin()){
                        int place = 0;
                        for(int i=0; i< winningSequence.length; i++){
                            if(winningSequence[i] == currentPlayer)
                                place = i+1;
                        }
                        control.playerWin(currentPlayer, place);
                    }
                    nextPlayer(false);
                    return true;
                } else if (code == -1) {
                    control.removeAllX();
                    System.out.println("Gamemanager: This place is already occupied by its own piece or the number is too high. Choose another piece");
                    sendMessageToPlayer("choose a different piece", currentPlayer);
                }
            }
            else {
                if(color == currentPlayer){
                    System.out.println("GM: Not yet rolled");
                    sendMessageToPlayer("you didn't throw yet", currentPlayer);
                }
                else {
                    players[currentPlayer].restAllSelects();
                    System.out.println("Gamemanager: Wrong color");
                    sendMessageToPlayer("wrong color", currentPlayer);
                }
            }
        }
        else {
            players[currentPlayer].restAllSelects();
            System.out.println("Gamemanager: No Piece selected");
            sendMessageToPlayer("no piece selected", currentPlayer);
        }
        return false;
    }

    /**
     * Comes from the GUI. Throw the Dice and select the current Player.
     * Checks if the player has thrown the dice before
     * Looks which player can start.
     */
    public int throwsDice(int debugSteps){
        int steps = dice.throwsDice(debugSteps, control);;
        System.out.println("Gamemanager: Dice throw: "+steps);
        if(steps != -1) {
            control.setLastDiceThrow(currentPlayer, steps);
            if(!startGame){ //Start: Looks which of the players rolls the highest number
                startNumbers[currentPlayer] = steps;
                if(currentPlayer == 3){
                    currentPlayer = 0;
                    System.out.println("GM: Start number: "+Arrays.toString(startNumbers));
                    for(int i=0; i<startNumbers.length; i++){
                        if(startNumbers[i] > startNumbers[currentPlayer])
                            currentPlayer = i;
                    }
                    System.out.println("Gamemanager: player "+(currentPlayer)+" starts");
                    sendMessageToPlayer("you have to start", currentPlayer);
                    control.playerTurn(currentPlayer);
                    //currentPlayer--;//-1 because at the end nextPlayer() is called
                    startGame = true;
                    dice.unlockDice();
                }
                else
                    nextPlayer(false);
                return steps;
            }//End: Looks which of the players rolls the highest number
            players[currentPlayer].setSteps(steps);
            int goOutPosition = playerCanGoOut();
            if(goOutPosition == -1) { //If no piece can leave the start and there is no piece in the field.
                //--------------------------------------For test with old Issues comment next out--------------------------------
                //playerRoll3Times = 1;
                System.out.println("GM: PlayerRoll3Times: "+playerRoll3Times);
                if(playerRoll3Times != 1){
                    dice.unlockDice();
                    control.removeAllX();
                    playerRoll3Times--;
                    System.out.println("Gamemanager: Throw again. You have "+playerRoll3Times+" left.");
                    sendMessageToPlayer("You have "+playerRoll3Times+" throws left", currentPlayer);
                }
                else if(playerRoll3Times == 1)
                    nextPlayer(false);
            }
            else if (goOutPosition != 0) {
                System.out.println("Gamemanager: Can go out of the house: " + goOutPosition);
                clickOnPiece(goOutPosition);//is called twice, so that it is marked once and then moved.
                if(clickOnPiece(goOutPosition))//Only if it could be moved
                    return -1;
            }
            else if(!playerCanMove()){ //When there is no player on the field. If a previous operation was true, it must not be true as well.
                System.out.println("Gamemanager: This player has no Piece that can be moved.");
                sendMessageToPlayer("you can't move a piece", currentPlayer);
                control.setDice(steps);
                waitTime(100);
                control.clearDice();
                nextPlayer(false);
                return -1;
            }
        }
        else {
            System.out.println("Gamemanager: This player has already rolled the dice");
            sendMessageToPlayer("you already threw the dice", currentPlayer);
        }
        return steps;
    }

    /**
     * Checks if a piece can leave the start.
     * If not, it checks if another player on the field can move it.
     * If a Piece can leave the start, it returns the position in the start.
     * If it has a Piece on the field, it returns 0.
     * If not, it returns -1.
     * @return
     */
    public int playerCanGoOut(){
        int code = -1;
        for(int i=0; i<4; i++){
            Piece piece = board.getStart()[currentPlayer*4+i];
            if(piece != null && dice.getSteps() == 6) //A Piece is in the start and it was rolled a 6
                return board.getPositionOfPiece(piece);
            if(piece == null)
                code = 0;//If no piece can go out, will end up giving back 0
        }
        return code;
    }

    /**
     * Reset the next player and the Dice.
     * inAnyCase is True when the player presses the next player button.
     * @param inAnyCase
     */
    public void nextPlayer(boolean inAnyCase){
        if(dice.getSteps() != 6 || !startGame || inAnyCase) {
            currentPlayer++;
            playerRoll3Times = 3;
            dice.unlockDice();
            if (currentPlayer == 4)
                currentPlayer = 0;
            //System.out.println(currentPlayer);
            control.playerTurn(currentPlayer);
        }
        else{
            dice.unlockDice();
            control.removeAllX();
            System.out.println("Gamemanager(nextplayer): Throw again");
            sendMessageToPlayer("throw again", currentPlayer);
        }
    }

    /**
     * See if a player has a Piece to move.
     * @return
     */
    public boolean playerCanMove(){
        Piece[] field = board.getField();
        for(int i=0; i<field.length; i++){
            if(field[i] != null) {
                if (field[i].getColor() == currentPlayer)
                    return true;
            }
        }
        return false;
    }
    public boolean playerWin(){
        for (int i=0; i<4;i++){
            if(board.getHouse()[currentPlayer*4+i] == null)
                return false;
        }
        for(int i=0;i<winningSequence.length;i++){
            if(winningSequence[i] == 0){
                winningSequence[i] = currentPlayer;
                return true;
            }
        }
        return false;
    }

    public void waitTime(int time){
        try {
            //Thread.sleep(time);
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToPlayer(String msg, int player){
        control.sendMessageToPlayer(msg, player);
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getBoard() {
        return board;
    }
}
