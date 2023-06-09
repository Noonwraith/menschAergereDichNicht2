package Logic;

import Controls.Control;
import Controls.Receive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private boolean startGame = false;
    private int[] startNumbers = new int[4];

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
    public void clickOnPiece(int position){
        Piece piece = board.getPieceOfPosition(position);
        if(piece != null && startGame) {
            int color = piece.getColor();
            if (color == currentPlayer && dice.isLock()) {

                int code = players[color].clickOnPiece(piece);
                if (code == 1) {
                    nextPlayer();
                } else if (code == -1) {
                    System.out.println("This place is already occupied by its own piece. Choose another piece");
                }
            }
            else {
                System.out.println("Falsche Farbe oder noch nicht gewürfelt.");
            }
        }
        else {
            System.out.println("No Piece selected");
        }

    }

    /**
     * Comes from the GUI. Throw the Dice and select the current Player.
     * Checks if the player has thrown the dice before
     */
    public int throwsDice(int debugSteps){
        int steps;
        if(debugSteps == -1) {
            steps = dice.throwsDice();
        }
        else {steps = debugSteps;}

        if(steps != -1) {
            //System.out.println("Dice throw: " + steps);

            if(!startGame){ //Looks which of the players rolls the highest number
                startNumbers[currentPlayer] = steps;
                if(currentPlayer == 3){
                    currentPlayer = 0;
                    System.out.println(Arrays.toString(startNumbers));
                    for(int i=0;i<startNumbers.length;i++){
                        if(startNumbers[i] > startNumbers[currentPlayer]){
                            currentPlayer = i;
                        }
                    }
                    System.out.println("player "+(currentPlayer)+" starts");
                    control.playerTurn(currentPlayer);
                    //currentPlayer--;//-1 because at the end nextPlayer() is called
                    startGame = true;
                    dice.unlockDice();
                }

                else {
                    nextPlayer();
                }
                return steps;
            }


            players[currentPlayer].setSteps(steps);

            int goOutPosition = playerCanGoOut();
            if(goOutPosition == -1) { //If no piece can leave the start and there is no piece in the field.
                nextPlayer();
            }
            else if (goOutPosition != 0) {
                System.out.println("Can go out of the house: " + goOutPosition);
                clickOnPiece(goOutPosition);
                clickOnPiece(goOutPosition);//is called twice, so that it is marked once and then moved.

            }
        }
        else {
            System.out.println("This player has already rolled the dice");
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
            if(piece != null && dice.getSteps() == 6){ //A Piece is in the start at it was rolled a 6
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
            System.out.println("Throw again");
        }
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }


    public ArrayList<Integer> getNumbers() {
        return dice.getNumbers();
    }

    public Board getBoard() {
        return board;
    }
}
