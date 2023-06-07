package Logic;

import Controls.Receive;

/**
 * Calculates best move
 * Moves the piece
 */
public class Computer extends Player{


    public Computer(int color, Board board, Receive guiReceive) {
        super(color, board, guiReceive);
    }
}
