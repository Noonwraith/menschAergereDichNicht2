package Logic;


import Controls.Control;
import Controls.Receive;

/**
 * requests to show future piece position
 * tells piece to move
 */
public class Human extends Player{


    public Human(int color, Board board, Control control) {
        super(color, board, control);
    }


    /**
     * Come from the GameManager.
     * select the right Piece. Change between Move and Future Place.
     * Returns a 1 if it moved a piece.
     * Returns a 0 if it has selected a piece.
     * Returns a -1 if it could not move a piece.
     * @param piece
     */

    public int clickOnPiece(Piece piece){
        if(piece.isSelected()){
            if(piece.move(this.getSteps())){
                this.getControl().updateBoard(this.getBoard());
                return 1;
            }
            return -1;
        }
        else {
            piece.selected();
            this.getControl().displayFuturePiecePosition(this, piece.futureMove(this.getSteps()));
            return 0;

        }
    }

}
