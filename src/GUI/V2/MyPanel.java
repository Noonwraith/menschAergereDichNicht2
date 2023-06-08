package GUI.V2;

import Controls.Control;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyPanel extends JPanel {

    //Fische

    private Control control;
    private GridBagLayout gridBagLayout;

    private int boardSize = 1000;
    private static final int rows = 11;
    private static final int cols = 11;
    private static final int buttonGab = 5;
    GridBagConstraints gbc;

    private Color[] playerColors = {Color.RED.darker(), Color.BLUE.darker(), Color.GREEN.darker(), Color.YELLOW.darker()};
    private Color backgroundColor = Color.GRAY;
    private int borderThickness = 8;
    private JButton[] allFields = new JButton[rows*cols];
    private static final int[] emptyFields = {2,3,7,8,13,14,18,19,22,23,24,25,29,30,31,32,33,34,35,36,40,41,42,43,77,78,79,80,84,85,86,87,88,89,90,91,95,96,97,98,101,102,106,107,112,113,117,118};
    private static final int[][] startFields = {{0,1,11,12}, {9,10,20,21}, {108,109,119,120}, {99,100,110,111} };
    private static final int[][] homeFields = {{56,57,58,59},{16,27,38,49},{61,62,63,64},{71,82,93,104}};
    private static final int[][] entryFields = {{44},{6},{76},{114}};
    private static final int[] pathFields = {45,46,47,48,37,26,15,4,5,17,28,39,50,51,52,53,54,65,75,74,73,72,83,94,105,116,115,103,92,81,70,69,68,67,66,55};
    private static final int dice = 60;

    public MyPanel(Control control){
        this.control = control;

        gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        gbc = new GridBagConstraints();
        this.setBackground(Color.LIGHT_GRAY);

        fillGrid();

        setUpEmptyFields();
        setUpStarts(borderThickness);
        setUpHomes(borderThickness);
        setUpEntryFields(borderThickness);
        setUpPath(borderThickness, Color.DARK_GRAY);
        setUpDice(borderThickness, Color.BLACK);
    }

    /*
    @Override
    public final Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        Dimension prefSize = null;
        Component c = getParent();
        if (c == null) {
            prefSize = new Dimension(
                    (int)d.getWidth(),(int)d.getHeight());
        } else if (c!=null &&
                c.getWidth()>d.getWidth() &&
                c.getHeight()>d.getHeight()) {
            prefSize = c.getSize();
        } else {
            prefSize = d;
        }
        int w = (int) prefSize.getWidth();
        int h = (int) prefSize.getHeight();
        // the smaller of the two sizes
        int s = (w>h ? h : w);
        return new Dimension(s,s);
    }

    /*
    @Override
    public void paint(Graphics g) {
        Dimension d = this.getSize();
        if(d.height > d.width){
            this.setSize(d.width, d.width);
        } else {
            this.setSize(d.height, d.height);
        }
        super.paint(g);
    }
     */

    private void fillGrid(){
        gbc.weightx = buttonGab;
        gbc.weighty = buttonGab;
        int x = 0;
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                gbc.gridx = j;
                gbc.gridy = i;
                allFields[x] = new JButton();
                allFields[x].setPreferredSize(new Dimension(boardSize/(rows+buttonGab/2),boardSize/(cols+buttonGab/2)));
                this.add(allFields[x],gbc);
                x++;
            }
        }
    }

    /*
    private void fillGrid(){
        for(int i=0; i<allFields.length; i++){
            allFields[i] = new JButton();
            this.add(allFields[i]);
        }
    }
     */

    private void setUpEmptyFields(){
        for (int emptyField : emptyFields) {
            allFields[emptyField].setEnabled(false);
            allFields[emptyField].setVisible(false);
        }
    }

    private void setUpStarts(int borderThickness){
        for(int i=0; i<startFields.length; i++){
            for(int j=0; j<startFields[i].length; j++){
                allFields[startFields[i][j]].setEnabled(true);
                allFields[startFields[i][j]].setVisible(true);
                allFields[startFields[i][j]].setBackground(backgroundColor);
                allFields[startFields[i][j]].setBorder(new LineBorder(playerColors[i], borderThickness));
            }
        }
    }

    private void setUpHomes(int borderThickness){
        for(int i=0; i<homeFields.length; i++){
            for(int j=0; j<homeFields[i].length; j++){
                allFields[homeFields[i][j]].setEnabled(true);
                allFields[homeFields[i][j]].setVisible(true);
                allFields[homeFields[i][j]].setBackground(backgroundColor);
                allFields[homeFields[i][j]].setBorder(new LineBorder(playerColors[i], borderThickness));
            }
        }
    }
    private void setUpEntryFields(int borderThickness){
        for(int i = 0; i< entryFields.length; i++){
            for(int j = 0; j< entryFields[i].length; j++){
                allFields[entryFields[i][j]].setEnabled(true);
                allFields[entryFields[i][j]].setVisible(true);
                allFields[entryFields[i][j]].setBackground(backgroundColor);
                allFields[entryFields[i][j]].setBorder(new LineBorder(playerColors[i], borderThickness));
            }
        }
    }

    private void setUpPath(int borderThickness, Color borderColor){
        for (int pathField : pathFields) {
            allFields[pathField].setEnabled(true);
            allFields[pathField].setVisible(true);
            allFields[pathField].setBackground(backgroundColor);
            allFields[pathField].setBorder(new LineBorder(borderColor, borderThickness));
        }
    }

    private void setUpDice(int borderThickness, Color borderColor){
        allFields[dice].setEnabled(true);
        allFields[dice].setVisible(true);
        allFields[dice].setBackground(backgroundColor);
        allFields[dice].setBorder(new LineBorder(borderColor, borderThickness));
        allFields[dice].setFont(new Font("Bodoni MT Black", Font.PLAIN, 40));
        ActionListener diceListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int steps = control.throwDice();
                if(steps != -1)
                    allFields[dice].setText(String.valueOf(steps));
            }
        };
        allFields[dice].addActionListener(diceListener);
    }

    /**
     * Changes a Fields background to either the playerColors 0-3 or to the default backgroundColor
     * @param fieldPosition
     * @param color
     */
    public void updateField(int fieldPosition, int color){
        if(color == -1)
            allFields[fieldPosition].setBackground(backgroundColor);
        else
        allFields[fieldPosition].setBackground(playerColors[color]);
    }

    public void playerTurn(int color){
        if(color == -1)
            allFields[dice].setBorder(new LineBorder(Color.BLACK, borderThickness));
        else
            allFields[dice].setBorder(new LineBorder(playerColors[color], borderThickness));
    }

}
