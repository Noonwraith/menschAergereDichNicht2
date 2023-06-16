package Logic;

import Controls.Control;
import Controls.Receive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        for(int i=0; i<numbersOfPlayer;i++){
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
        //System.out.println("Gamemanager -> click on Piece: Piece: "+ piece);
        if(piece != null && startGame) {
            int color = piece.getColor();
            if (color == currentPlayer && dice.isLock()) {

                int code = players[color].clickOnPiece(piece);
                if (code == 1) {
                    if(playerWin()){
                        int place = 0;
                        System.out.println("Control: winningSequence: "+ Arrays.toString(winningSequence));
                        for(int i=0;i< winningSequence.length;i++){
                            if(winningSequence[i] == currentPlayer){
                                place = i+1;
                            }
                        }
                        control.playerWin(currentPlayer, place);
                    }
                    nextPlayer();
                    return true;
                } else if (code == -1) {


                    control.removeAllX();
                    System.out.println("Gamemanager: This place is already occupied by its own piece or the number is too high. Choose another piece");

                    /*if(playerCanMove()) {
                        control.removeAllX();
                        System.out.println("Gamemanager: This place is already occupied by its own piece or the number is too high. Choose another piece");
                    }
                    else{
                        System.out.println("Gamemanager: This player has no Piece that can be moved.");
                        control.clearDice();
                        nextPlayer();
                    }*/
                }
            }
            else {
                System.out.println("Gamemanager: Wrong color or not yet rolled.");
            }
        }
        else {
            System.out.println("Gamemanager: No Piece selected");
        }

        return false;
    }

    /**
     * Comes from the GUI. Throw the Dice and select the current Player.
     * Checks if the player has thrown the dice before
     */
    public int throwsDice(int debugSteps){
        int steps = dice.throwsDice(debugSteps, control);;
        System.out.println("Gamemanager: Dice throw: "+steps);

        if(steps != -1) {
            //System.out.println("Dice throw: " + steps);

            if(!startGame){ //Start: Looks which of the players rolls the highest number
                startNumbers[currentPlayer] = steps;
                if(currentPlayer == 3){
                    currentPlayer = 0;
                    System.out.println(Arrays.toString(startNumbers));
                    for(int i=0;i<startNumbers.length;i++){
                        if(startNumbers[i] > startNumbers[currentPlayer]){
                            currentPlayer = i;
                        }
                    }
                    System.out.println("Gamemanager: player "+(currentPlayer)+" starts");
                    control.playerTurn(currentPlayer);
                    //currentPlayer--;//-1 because at the end nextPlayer() is called
                    startGame = true;
                    dice.unlockDice();
                }

                else {
                    nextPlayer();
                }
                return steps;
            }//End: Looks which of the players rolls the highest number





            players[currentPlayer].setSteps(steps);

            int goOutPosition = playerCanGoOut();
            if(goOutPosition == -1) { //If no piece can leave the start and there is no piece in the field.

                /*control.clearDice();
                System.out.println("Wait");
                waitTime(1000);*/
                //--------------------------------------For test with old Issues comment--------------------------------
                if(playerRoll3Times == 1) {
                    playerRoll3Times = 3;
                    nextPlayer();
                }
                else{
                    dice.unlockDice();
                    control.removeAllX();
                    playerRoll3Times--;
                    System.out.println("Gamemanager: Throw again. You have "+playerRoll3Times+" left.");
                }
                //---------------------------------------^^^^^^^^^------------------------------------------------------


            }
            else if (goOutPosition != 0) {
                System.out.println("Gamemanager: Can go out of the house: " + goOutPosition);
                clickOnPiece(goOutPosition);//is called twice, so that it is marked once and then moved.
                if(clickOnPiece(goOutPosition)) {//Only if it could be moved
                    return -1;
                }

            }



            else if(!playerCanMove()){ //When there is no player on the field. If a previous operation was true, it must not be true as well.
                System.out.println("Gamemanager: This player has no Piece that can be moved.");
                control.setDice(steps);
                waitTime(100);
                control.clearDice();
                nextPlayer();
                return -1;
            }

        }
        else {
            System.out.println("Gamemanager: This player has already rolled the dice");
        }

        return steps;

    }


    /**
     * Checks if a piece can leave the start.
     * If not, it checks if another player on the field can move it.
     *
     * If a Piece can leave the start, it returns the position in the start.
     * If it has a Piece on the field, it returns 0.
     * If not, it returns -1.
     * @return
     */
    public int playerCanGoOut(){
        int code = -1;

        for(int i=0;i<4;i++){
            Piece piece = board.getStart()[currentPlayer*4+i];
            if(piece != null && dice.getSteps() == 6){ //A Piece is in the start and it was rolled a 6
                return board.getPositionOfPiece(piece);
            }
            if(piece == null){
                code = 0;//If no piece can go out, will end up giving back 0
            }
        }
        return code;
    }

    public void nextPlayer(){
        if(dice.getSteps() != 6 || !startGame) {
            currentPlayer++;
            dice.unlockDice();
            if (currentPlayer == 4) {
                currentPlayer = 0;
            }

            //System.out.println(currentPlayer);
            control.playerTurn(currentPlayer);
        }
        else{
            dice.unlockDice();
            control.removeAllX();
            System.out.println("Gamemanager(nextplayer): Throw again");
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
                if (field[i].getColor() == currentPlayer) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean playerWin(){
        for (int i=0; i<4;i++){
            //System.out.println("GM: Piece: "+board.getHouse()[currentPlayer*4+i]);
            if(board.getHouse()[currentPlayer*4+i] == null){
                return false;
            }
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

    public int getCurrentPlayer() {
        return currentPlayer;
    }



    public Board getBoard() {
        return board;
    }
}
