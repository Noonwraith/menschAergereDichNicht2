package Logic;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Saves Pieces Positions
 */
public class Board {
    private Piece[] field = new Piece[40];//positions between 0-39
    private Piece[] start = new Piece[16];//positions between 40-56 -> Team 0: 40-43 -> Team 1: 44-47 -> Team 2: 48-51 -> Team 3: 52-56
    private Piece[] house = new Piece[16];//positions between 57-73 -> Team 0: 57-60 -> Team 1: 61-64 -> Team 2: 65-69 -> Team 3: 70-73


    public Board(){
    }

    public void setPieceToStart(Piece piece){
        int color = piece.getColor();
        for(int i=color*4; i<color*4+4; i++){
            if(start[i] == null){
                start[i] = piece;
                return;
            }
        }
    }

    public void setPiecePosition(Piece piece, int currentPosition, int futurePosition){
        if(futurePosition < 40){
            field[futurePosition] = piece;
        }
        else if(futurePosition < 57){
            start[futurePosition-39] = piece;
        }
        else if(futurePosition < 74){
            house[futurePosition-56] = piece;
        }
        if(currentPosition < 40){
            field[currentPosition] = null;
        }
        else if(currentPosition < 57){
            start[currentPosition-40] = null;
        }
        else if(currentPosition < 74){
            house[currentPosition-57] = null;
        }

    }

    public int getPositionOfPiece(Piece piece) {
        for (int i = 0; i < field.length; i++) {
            if (field[i] == piece) {
                return i;
            }
        }
        for(int i=0; i<start.length;i++){
            //System.out.println("Durch dasn Feld Start: "+i);
            if(start[i] == piece){
                return i+40;
            }
        }
        for(int i=0; i< house.length;i++){
            if(house[i] == piece){
                return i+57;
            }
        }
        return -1;
    }

    public Piece getPieceOfPosition(int position){
        if(position <= 39){
            return field[position];
        }
        else if(position <= 56){
            return start[position-40];
        }
        else if(position <= 73){
            return start[position-56];
        }
        return null;
    }

    public Piece[] getField() {
        return field;
    }

    public Piece[] getStart() {
        return start;
    }

    @Override
    public String toString() {
        return "Board{" +
                "field=" + Arrays.toString(field) +
                ", start=" + Arrays.toString(start) +
                ", house=" + Arrays.toString(house) +
                '}';
    }
}
