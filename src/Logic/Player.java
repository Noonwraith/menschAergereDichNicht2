package Logic;

import Controls.Control;
import Controls.Receive;

/**
 * knows his color
 * knows dice throw
 */
public abstract class Player{
    private Control control;
    private Piece pieces[] = new Piece[4];


    private int color;
    private int steps;

    public Player(int color,Board board, Control control){
        this.control = control;
        this.color = color;
        for(int i=0; i<4;i++){
            pieces[i] = new Piece(board, color);
            board.setPieceToStart(pieces[i]);
        }
    }

    /**
     * set the steps to the value of the Dive. It gets the Parameter from the GameManager.
     * @param steps
     */
    public void setSteps(int steps) {
        this.steps = steps;
    }


    public Piece[] getPieces() {
        return pieces;
    }

    public int getSteps() {
        return steps;
    }

    public Control getControl() {
        return control;
    }

    public int getColor() {
        return color;
    }
}
