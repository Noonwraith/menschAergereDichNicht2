package Logic;

import Controls.Control;

/**
 * moves
 * gets kicked
 */
public class Piece {
    private Board board;
    private Control control;
    private int color;
    private static int zaehler = 0;
    private int nr = 0;
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
        Piece pieceOnFuturePosition;
        if(futurePosition == -1)
            return false;
        else if(futurePosition <= 39)
            pieceOnFuturePosition = board.getField()[futurePosition];
        else//Piece is in House
            pieceOnFuturePosition = board.getHouse()[futurePosition-56];
        if(pieceOnFuturePosition == null){//Poition is free
            board.setPiecePosition(this, currentPosition, futurePosition);
            control.movePiece(currentPosition, -1);
            control.movePiece(futurePosition, color);
        }
        else{
            if(pieceOnFuturePosition.getColor() == this.color)//On this Position stands Piece from the same Color
                return false;
            else{//There stand another Player
                board.setPiecePosition(this, currentPosition, futurePosition);
                int cickPieceBoardPosition = board.setPieceToStart(pieceOnFuturePosition);
                control.movePiece(currentPosition, -1);
                control.movePiece(futurePosition, color);
                control.movePiece(cickPieceBoardPosition, pieceOnFuturePosition.getColor());
                //return true;
            }
        }
        isSelected = false;
        return true;
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
            if (futurePosition > board.getField().length-1)
                futurePosition -= board.getField().length;
            if(color != 0 && futurePosition > color*10-1 && currentPosition <= color*10-1 || color == 0 && futurePosition <= 10 && currentPosition >= 32){
                int stepsInHouse = (steps-((color*10-1)-currentPosition));
                if(color == 0)
                    stepsInHouse -= 40;
                System.out.println("Piece: steps in House: "+stepsInHouse);
                if(stepsInHouse <= 4){
                    futurePosition = color*4+55+stepsInHouse;
                }
                else{
                    futurePosition = -1;
                    System.out.println("Piece: The Piece cannot move. The number is too high to go in the house.");
                }
            }
        }
        else if(currentPosition < 56)//Can leave the start
            futurePosition = 10*color;
        else if(currentPosition >= 56){ //Moves in the house
            futurePosition = currentPosition+steps;
            if(futurePosition > ((color*4+56)+3)){//When the futurePosition is higher than the house((color*4+56)+3)
                System.out.println("Piece: The futurePosition is higher than the house.");
                futurePosition = -1;
            }
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

    public void setSelected(boolean selected) {
        isSelected = selected;
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
