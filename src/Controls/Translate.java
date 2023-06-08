package Controls;

import GUI.V2.MyPanel;
import Logic.Board;
import Logic.Piece;

import java.awt.*;

public class Translate {
    private Control control;


    private int[] translateField = {
            56, 57, -1, -1, 8, 9, 10, -1, -1, 60, 61,
            58, 59, -1, -1, 7, 44, 11, -1, -1, 62, 63,
            -1, -1, -1, -1, 6, 45, 12, -1, -1, -1, -1,
            -1, -1, -1, -1, 5, 46, 13, -1, -1, -1, -1,
            0, 1, 2, 3, 4, 47, 14, 15, 16, 17, 18,
            39, 40, 41, 42, 43, 60, 51, 50, 49, 48, 19,
            38, 37, 36, 35, 34, 55, 24, 23, 22, 21, 20,
            -1, -1, -1, -1, 33, 54, 25, -1, -1, -1, -1,
            -1, -1, -1, -1, 32, 53, 26, -1, -1, -1, -1,
            68, 69, -1, -1, 31, 52, 27, -1, -1, 64, 65,
            70, 71, -1, -1, 30, 29, 28, -1, -1, 66, 67
    };

    private Piece[] field = new Piece[40];//positions between 0-39
    private Piece[] start = new Piece[16];//positions between 40-56 -> Team 0: 40-43 -> Team 1: 44-47 -> Team 2: 48-51 -> Team 3: 52-55
    private Piece[] house = new Piece[16];//positions between 56-72 -> Team 0: 56-59 -> Team 1: 60-63 -> Team 2: 64-67 -> Team 3: 68-71

    public Translate(Control control){
        this.control = control;
    }



    public void updateBoard(MyPanel panel, Board board){
        for(int i=0; i<field.length;i++){
            if(field[i] != board.getField()[i]){
                panel.updateField(i, board.getField()[i].getColor());
            }
        }
        for(int i=0; i<start.length;i++){
            if(start[i] != board.getStart()[i]){
                panel.updateField(boardPositionToGuiPosition(i+56), board.getStart()[i].getColor());
            }
        }
        for(int i=0; i<house.length;i++){
            if(house[i] != board.getHouse()[i]){
                panel.updateField(i, board.getHouse()[i].getColor());
            }
        }
        field = board.getField();
        start = board.getStart();
        house = board.getHouse();
    }


    public int boardPositionToGuiPosition(int position){
        for(int i=0; i< translateField.length;i++){
            if(translateField[i] == position){
                return i;
            }
        }
        System.out.println("Error: THis Position dosn't exist: "+position);
        return -1;
    }



}
