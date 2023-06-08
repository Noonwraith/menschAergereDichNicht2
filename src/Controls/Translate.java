package Controls;

import GUI.V2.MyPanel;

public class Translate {
    private int[] field = {
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



    public void boardPositionToGuiPosition(MyPanel panel, int color, int position){
        for(int i=0; i< field.length;i++){
            if(field[i] == position){
                panel.updateField(i, color);
            }
        }
    }

}
