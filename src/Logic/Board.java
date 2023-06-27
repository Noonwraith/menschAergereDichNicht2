package Logic;

import java.util.Arrays;

/**
 * Saves Pieces Positions
 */
public class Board {
    private Piece[] field = new Piece[40];//positions between 0-39
    private Piece[] start = new Piece[16];//positions between 40-56 -> Team 0: 40-43 -> Team 1: 44-47 -> Team 2: 48-51 -> Team 3: 52-55
    private Piece[] house = new Piece[16];//positions between 56-72 -> Team 0: 56-59 -> Team 1: 60-63 -> Team 2: 64-67 -> Team 3: 68-71

    public Board(){
    }

    public int setPieceToStart(Piece piece){
        int color = piece.getColor();
        for(int i=color*4; i<color*4+4; i++){
            if(start[i] == null){
                start[i] = piece;
                return i+40;
            }
        }
        return -1;
    }

    public void setPiecePosition(Piece piece, int currentPosition, int futurePosition){
        if(futurePosition < 40)
            field[futurePosition] = piece;
        else if(futurePosition < 56)
            start[futurePosition-40] = piece;//---------------------------------Hier stand mal 39, Muss noch gestest werden was richtig ist.
        else if(futurePosition < 72)
            house[futurePosition-56] = piece;
        if(currentPosition < 40)
            field[currentPosition] = null;
        else if(currentPosition < 56)
            start[currentPosition-40] = null;
        else if(currentPosition < 72)
            house[currentPosition-56] = null;
    }

    public int getPositionOfPiece(Piece piece) {
        //System.out.println("Borad: field Array: "+ Arrays.toString(field));
        //System.out.println("Borad: Startd Array: "+ Arrays.toString(start));
        //System.out.println("Borad: haous Array: "+ Arrays.toString(house));
        for (int i = 0; i < field.length; i++) {
            if (field[i] == piece)
                return i;
        }
        for(int i=0; i<start.length; i++){
            //System.out.println("i: "+i);
            if(start[i] == piece)
                return i+40;
        }
        for(int i=0; i< house.length;i++){
            if(house[i] == piece)
                return i+56;
        }
        return -1;
    }

    public Piece getPieceOfPosition(int position){
        if(position <= 39)
            return field[position];
        else if(position <= 55)
            return start[position-40];
        else if(position <= 71)
            return house[position-56];
        return null;
    }

    public Piece[] getField() {
        return field;
    }

    public Piece[] getStart() {
        return start;
    }

    public Piece[] getHouse() {
        return house;
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
