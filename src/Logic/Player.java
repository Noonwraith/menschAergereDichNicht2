package Logic;

import Controls.Receive;

/**
 * knows his color
 * knows dice throw
 */
public abstract class Player{
    private Receive guiReceive;
    private Piece pieces[] = new Piece[4];


    private int color;
    private int steps;

    public Player(int color,Board board, Receive guiReceive){
        this.guiReceive = guiReceive;
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

    public Receive getGuiReceive() {
        return guiReceive;
    }
}
