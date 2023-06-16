package GUI.V2;

import Controls.Control;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyPanel extends JPanel {

    private Control control;
    private GridBagLayout gridBagLayout;
    GridBagConstraints gbc;

    private static final int rows = 11;
    private static final int cols = rows;
    private static final int buttonGab = 5;
    private static int buttonSize ;
    private static int preferredSize;

    private Color[] playerColors = {Color.RED.darker(), Color.BLUE.darker(), Color.GREEN.darker(), Color.YELLOW.darker()};
    private Color backgroundColor = Color.GRAY;
    private Color pathBoarderColor = Color.DARK_GRAY;
    private int borderThickness = 8;
    private JButton[] allButtons = new JButton[rows*cols];
    private static final int[] fieldsWithoutButton = {2,3,7,8,13,14,18,19,22,23,24,25,29,30,31,32,33,34,35,36,40,41,42,43,77,78,79,80,84,85,86,87,88,89,90,91,95,96,97,98,101,102,106,107,112,113,117,118};
    private static final int[][] startFields = {{0,1,11,12}, {9,10,20,21}, {108,109,119,120}, {99,100,110,111}};
    private static final int[][] homeFields = {{56,57,58,59},{16,27,38,49},{61,62,63,64},{71,82,93,104}};
    private static final int[][] entryFields = {{44},{6},{76},{114}};
    private static final int[] pathFields = {45,46,47,48,37,26,15,4,5,17,28,39,50,51,52,53,54,65,75,74,73,72,83,94,105,116,115,103,92,81,70,69,68,67,66,55};
    private static final int[] selectableFields = {56,57,58,59,16,27,38,49,61,62,63,64,71,82,93,104,44,6,76,114,45,46,47,48,37,26,15,4,5,17,28,39,50,51,52,53,54,65,75,74,73,72,83,94,105,116,115,103,92,81,70,69,68,67,66,55};
    private static final int dice = 60;

    public MyPanel(Control control){
        this.control = control;

        preferredSize = (int) (calculateScreenHeight()-(calculateScreenHeight()*0.15));
        buttonSize = (preferredSize/rows)-buttonGab;

        gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        gbc = new GridBagConstraints();
        this.setBackground(Color.LIGHT_GRAY);

        setUpButtons();
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(preferredSize, preferredSize);
    }

    public double calculateScreenHeight(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double height = screenSize.getHeight();
        return height;
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
            this.setSize(dA.width, d.width);
        } else {
            this.setSize(d.height, d.height);
        }
        super.paint(g);
    }
     */

    /**
     * Sets a Button on every field
     * Calls following methods afterwards to customise buttons:
     * {@link #removeButtons()}
     * {@link #setUpStarts()}
     * {@link #setUpHomes(boolean)}
     * {@link #setUpEntryFields(boolean)}
     * {@link #setUpPath(boolean)}
     * {@link #setUpDice(Color)}
     * {@link #addActionListenerToSelectableFields()}
     */
    private void setUpButtons(){
        gbc.weightx = buttonGab;
        gbc.weighty = buttonGab;
        int x = 0;
        for (int i=0; i<rows; i++){
            for (int j=0; j<cols; j++){
                gbc.gridx = j;
                gbc.gridy = i;
                allButtons[x] = new JButton();
                allButtons[x].setPreferredSize(new Dimension(buttonSize,buttonSize));
                this.add(allButtons[x],gbc);
                x++;
            }
        }
        removeButtons();
        setUpStarts();
        setUpHomes(false);
        setUpEntryFields(false);
        setUpPath(false);
        setUpDice(Color.BLACK);
        addActionListenerToSelectableFields();
    }

    /*
    private void fillGrid(){
        for(int i=0; i<allFields.length; i++){
            allFields[i] = new JButton();
            this.add(allFields[i]);
        }
    }
     */

    /**
     * removes Buttons form coordinates {@link #fieldsWithoutButton}
     */
    private void removeButtons(){
        for (int emptyField : fieldsWithoutButton) {
            this.remove(allButtons[emptyField]);
        }
    }

    /**
     * customizes starting fields
     */
    private void setUpStarts(){
        for (int i=0; i<startFields.length; i++){
            for (int j=0; j<startFields[i].length; j++){
                allButtons[startFields[i][j]].setEnabled(true);
                allButtons[startFields[i][j]].setVisible(true);
                allButtons[startFields[i][j]].setBackground(backgroundColor);
                allButtons[startFields[i][j]].setBorder(new LineBorder(playerColors[i], borderThickness));
            }
        }
    }

    /**
     * customizes home fields
     * @param onlySetBorder
     */
    private void setUpHomes(boolean onlySetBorder){
        for (int i=0; i<homeFields.length; i++){
            for (int j=0; j<homeFields[i].length; j++){
                if(onlySetBorder == false) {
                    allButtons[homeFields[i][j]].setEnabled(true);
                    allButtons[homeFields[i][j]].setVisible(true);
                    allButtons[homeFields[i][j]].setBackground(backgroundColor);
                }
                allButtons[homeFields[i][j]].setBorder(new LineBorder(playerColors[i], borderThickness));
            }
        }
    }

    /**
     *
     * @param onlySetBorder
     */
    private void setUpEntryFields(boolean onlySetBorder){
        for (int i = 0; i< entryFields.length; i++){
            for (int j = 0; j< entryFields[i].length; j++){
                if(onlySetBorder == false) {
                    allButtons[entryFields[i][j]].setEnabled(true);
                    allButtons[entryFields[i][j]].setVisible(true);
                    allButtons[entryFields[i][j]].setBackground(backgroundColor);
                }
                allButtons[entryFields[i][j]].setBorder(new LineBorder(playerColors[i], borderThickness));

            }
        }
    }

    private void setUpPath(boolean onlySetBorder){
        for (int pathField : pathFields) {
            if(onlySetBorder == false) {
                allButtons[pathField].setEnabled(true);
                allButtons[pathField].setVisible(true);
                allButtons[pathField].setBackground(backgroundColor);
            }
            allButtons[pathField].setBorder(new LineBorder(pathBoarderColor, borderThickness));
        }
    }

    private void setUpDice(Color borderColor){
        allButtons[dice].setEnabled(true);
        allButtons[dice].setVisible(true);
        allButtons[dice].setBackground(backgroundColor);
        allButtons[dice].setBorder(new LineBorder(borderColor));
        allButtons[dice].setFont(new Font("Bodoni MT Black", Font.PLAIN, 40));
        ActionListener diceListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.throwDice(0); //0 stands for no Debug mode
                /*int steps = control.throwDice(-1);
                if(steps != -1)
                    allFields[dice].setText(String.valueOf(steps));*/
            }
        };
        allButtons[dice].addActionListener(diceListener);
    }

    public void addActionListenerToSelectableFields(){
        for(int i=0; i<selectableFields.length; i++){
            int finalI = i;
            ActionListener fieldListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setUpHomes(true);
                    setUpEntryFields(true);
                    setUpPath(true);
                    for (int x = 0; x< allButtons.length; x++){
                        if(x != dice)
                        allButtons[x].setText("");
                    }
                    control.fieldSelected(selectableFields[finalI]); //Method for Controls
                    allButtons[selectableFields[finalI]].setBorder(new LineBorder(Color.BLACK, borderThickness)); //select
                }
            };
            allButtons[selectableFields[i]].addActionListener(fieldListener);
        }
    }

    /**
     * Changes a Fields background to either the playerColors 0-3 or to the default backgroundColor
     * @param fieldPosition
     * @param color
     */
    public void updateField(int fieldPosition, int color){
        if(color == -1)
            allButtons[fieldPosition].setBackground(backgroundColor);
        else
        allButtons[fieldPosition].setBackground(playerColors[color]);
    }

    /**
     * Sets Dice to the Color of a player
     * @param color
     */
    public void playerTurn(int color){
        if(color == -1)
            allButtons[dice].setBorder(new LineBorder(Color.BLACK, borderThickness));
        else
            allButtons[dice].setBorder(new LineBorder(playerColors[color], borderThickness));
    }

    /**
     * Writes an "X" on a selected Button
     * @param position
     */
    public void futureMovePosition(int position){
        allButtons[position].setFont(new Font("Bodoni MT Black", Font.PLAIN, 40));
        allButtons[position].setText("X");
    }

    /**
     * removes all "X" from all Buttons
     */
    public void removeAllX(){
        setUpHomes(true);
        setUpEntryFields(true);
        setUpPath(true);
        for (int x = 0; x< allButtons.length; x++){
            if(x != dice)
                allButtons[x].setText("");
        }
    }

    /**
     * Sets Dice to a given number
     * When the number is 0 the dice gets cleared
     * When the number is -1 the dice won't change
     * @param number
     */
    public void setDiceNumber(int number){
        if(number == 0) {
            //System.out.println("Panel: Rest Dice");
            allButtons[dice].setText("");
        }
        else if(number == -1){}
        else {
            //System.out.println("Panel: set Dice");
            allButtons[dice].setText(String.valueOf(number));
        }
    }

}
