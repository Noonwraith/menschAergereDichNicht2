package Logic;

import Controls.Control;

import java.util.concurrent.FutureTask;

/**
 * moves
 * gets kicked
 */
public class Piece {
    private Board board;
    private Control control;
    private int color;

    private static int zaehler = 0;
    private int nr = 0; // For Debugging


    private boolean isSelected = false;


    public Piece(Board board, Control control, int color){
        this.board = board;
        this.color = color;
        this.control = control;
        nr = zaehler;
        zaehler++;
    }

    /**
     * Move the Piece on the Board.
     * Returns true if it was moved
     * @param steps
     * @return
     */
    public boolean move(int steps) {
        int currentPosition = board.getPositionOfPiece(this);
        int futurePosition = futureMove(steps);
        Piece pieceOnFuturePosition = board.getField()[futurePosition];
        if(pieceOnFuturePosition == null){//Poition is free
            board.setPiecePosition(this, currentPosition, futurePosition);
            System.out.println("Piece: Piece move to: "+ futurePosition);
            System.out.println("Piece: Piece come from: "+currentPosition);
            //System.out.println(board.toString());
            control.movePiece(currentPosition, -1);
            control.movePiece(futurePosition, color);


            isSelected = false;//---------------------------------------------------------Zum Debuggen auskommentiern. Dann muss man es nicht zweimal anklicken.
            return true;
        }
        else{
            if(pieceOnFuturePosition.getColor() == this.color){ //On this Position stands Piece from the same Color
                return false;
            }
            else{//There stand another Player
                board.setPiecePosition(this, currentPosition, futurePosition);
                int cickPieceBoardPosition = board.setPieceToStart(pieceOnFuturePosition);

                System.out.println("Piece: Piece come from: "+currentPosition);
                System.out.println("Piece: Piece move to: "+ futurePosition);
                System.out.println("Piece: Kick Piece from: "+ futurePosition);



                control.movePiece(currentPosition, -1);
                control.movePiece(futurePosition, color);
                control.movePiece(cickPieceBoardPosition, pieceOnFuturePosition.getColor());

                return true;
            }
        }
        //System.out.println("Piece move from: "+ currentPosition);
        //System.out.println("Piece move to: "+futurePosition);

    }

    /**
     * calutlate the future Place and return it to Player.
     * @param steps
     * @return
     */
    public int futureMove(int steps){
        int currentPosition = board.getPositionOfPiece(this);
        int futurePosition = -1;
        if(currentPosition <= 39) {
            futurePosition = currentPosition + steps;
            if (futurePosition > board.getField().length-1) {
                futurePosition -= board.getField().length;
            }
            if(futurePosition > color*10-1 && currentPosition <= color*10-1){
                //(steps-(currentPosition-(color*10-1))) -> gibt an wie viele Felder in das Haus gegangen werden.
                int stepsInHouse = (steps-(currentPosition-(color*10-1)));
                if(stepsInHouse <= 4){
                    System.out.println("Piece: Piece can go in house");
                    futurePosition = color*4+56+stepsInHouse;
                    System.out.println("Piece: last Position : "+currentPosition);
                    System.out.println("Piece: nextPosition: "+futurePosition);
                }
                else{
                    futurePosition = -1;
                    System.out.println("Piece: The Piece cannot move. The number is too high.");
                }
            }
        }
        else if(currentPosition <= 56){
            futurePosition = 10*color;
        }
        return futurePosition;
    }

    /**
     * Return if it is selected. If it is selected it can move. Else it would be selected.
     * @return
     */
    public boolean isSelected() {
        return isSelected;
    }

    public void selected() {
        isSelected = true;
    }

    public int getColor() {
        return color;
    }

    public int getNr() {
        return nr;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "color=" + color +
                ", nr=" + nr +
                '}';
    }
}
