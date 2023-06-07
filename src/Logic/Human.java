package Logic;


import Controls.Receive;

/**
 * requests to show future piece position
 * tells piece to move
 */
public class Human extends Player{


    public Human(int color, Board board, Receive guiReceive) {
        super(color, board, guiReceive);
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

                return 1;
            }
            return -1;
        }
        else {
            piece.selected();
            this.getGuiReceive().displayFuturePiecePosition(this, piece.futureMove(this.getSteps()), "");
            return 0;

        }
    }

}
