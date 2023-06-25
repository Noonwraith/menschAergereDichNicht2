package Controls;

import GUI.V2.MyPanel;
import Logic.Board;
import Logic.Piece;

import java.awt.*;
import java.util.Arrays;

public class Translate {
    private Control control;


    private int[] translateField = {
            40, 41, -1, -1, 8, 9, 10, -1, -1, 44, 45,
            42, 43, -1, -1, 7, 60, 11, -1, -1, 46, 47,
            -1, -1, -1, -1, 6, 61, 12, -1, -1, -1, -1,
            -1, -1, -1, -1, 5, 62, 13, -1, -1, -1, -1,
            0, 1, 2, 3, 4, 63, 14, 15, 16, 17, 18,
            39, 56, 57, 58, 59, -1, 67, 66, 65, 64, 19,
            38, 37, 36, 35, 34, 71, 24, 23, 22, 21, 20,
            -1, -1, -1, -1, 33, 70, 25, -1, -1, -1, -1,
            -1, -1, -1, -1, 32, 69, 26, -1, -1, -1, -1,
            52, 53, -1, -1, 31, 68, 27, -1, -1, 48, 49,
            54, 55, -1, -1, 30, 29, 28, -1, -1, 50, 51
    };

    private Piece[] field = new Piece[40];//positions between 0-39
    private Piece[] start = new Piece[16];//positions between 40-56 -> Team 0: 40-43 -> Team 1: 44-47 -> Team 2: 48-51 -> Team 3: 52-55
    private Piece[] house = new Piece[16];//positions between 56-72 -> Team 0: 56-59 -> Team 1: 60-63 -> Team 2: 64-67 -> Team 3: 68-71

    public Translate(Control control){
        this.control = control;
    }



    public void updateBoard(MyPanel panel, Board board){
        //System.out.println("Suche nach unterschieden im board");
        for(int i=0; i<field.length;i++){
            if(field[i] != board.getField()[i]){
                System.out.println("Feld wird an der Psoition: "+i);
                panel.updateField(boardPositionToGuiPosition(i), board.getField()[i].getColor());
            }
        }
        for(int i=0; i<start.length;i++){
            if(start[i] != board.getStart()[i]){
                System.out.println("Start wird an der Psoition: "+(i+40));
                panel.updateField(boardPositionToGuiPosition(i+40), board.getStart()[i].getColor());
            }
        }
        for(int i=0; i<house.length;i++){
            if(house[i] != board.getHouse()[i]){
                //System.out.println("Haus wird an der Psoition: "+(i+55));
                panel.updateField(boardPositionToGuiPosition(i+55), board.getHouse()[i].getColor());
            }
        }

        //System.out.println("start-----"+ Arrays.toString(start));
        //System.out.println("Board start"+Arrays.toString(board.getStart()));

        field = board.getField();
        start = board.getStart();
        house = board.getHouse();


        //System.out.println("start-----"+ Arrays.toString(start));
    }


    public int boardPositionToGuiPosition(int position){
        for(int i=0; i< translateField.length;i++){
            if(translateField[i] == position){
                return i;
            }
        }
        System.out.println("Error: Translate: This Position dosn't exist: "+position);
        return -1;
    }

    public int GuiPositionToBoardPosition(int position){
        //System.out.println("Translate: -----------------Click on Position"+ position);
        return translateField[position];
    }


}
