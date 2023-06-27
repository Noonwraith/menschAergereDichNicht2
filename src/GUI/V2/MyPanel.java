package GUI.V2;

import Controls.Control;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class MyPanel extends JPanel {
    private Control control;
    private final GridBagConstraints gbc = new GridBagConstraints();
    private static final int rows = 11;
    private static final int cols = rows;
    private static final int buttonGab = 5;
    private static int buttonSize;
    private static int preferredSize;
    private final Color[] playerColors = {Color.RED.darker(), Color.BLUE.darker(), Color.GREEN.darker(), Color.YELLOW.darker()};
    private final Color backgroundColor = Color.GRAY;
    private final Color pathBoarderColor = Color.DARK_GRAY;
    private final int borderThickness = 8;
    private final JButton[] allButtons = new JButton[rows*cols];
    private static final int[] fieldsWithoutButton = {2,3,7,8,13,14,18,19,22,23,24,25,29,30,31,32,33,34,35,36,40,41,42,43,77,78,79,80,84,85,86,87,88,89,90,91,95,96,97,98,101,102,106,107,112,113,117,118};
    private static final int[][] startFields = {{0,1,11,12}, {9,10,20,21}, {108,109,119,120}, {99,100,110,111}};
    private static final int[][] homeFields = {{56,57,58,59},{16,27,38,49},{61,62,63,64},{71,82,93,104}};
    private static final int[][] entryFields = {{44},{6},{76},{114}};
    private static final int[] pathFields = {45,46,47,48,37,26,15,4,5,17,28,39,50,51,52,53,54,65,75,74,73,72,83,94,105,116,115,103,92,81,70,69,68,67,66,55};
    private static final int[] selectableFields = {56,57,58,59,16,27,38,49,61,62,63,64,71,82,93,104,44,6,76,114,45,46,47,48,37,26,15,4,5,17,28,39,50,51,52,53,54,65,75,74,73,72,83,94,105,116,115,103,92,81,70,69,68,67,66,55};
    private static final int dice = 60;
    private final JLabel[] lastDiceThrowJLabel = new JLabel[4];
    private final JLabel[] messageToPlayerLabel = new JLabel[4];

    public MyPanel(Control control){
        this.control = control;
        preferredSize = (int) (calculateScreenHeight()-(calculateScreenHeight()*0.15));
        buttonSize = (preferredSize/rows)-buttonGab;
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.LIGHT_GRAY);
        setUpButtons();
        setUpLastDiceThrowJLabel("Last Throw: 0");
        setUpMessageToPlayerLabel("no Message inbox");
        System.out.println(calculateScreenHeight());
    }

    /**
     * Overwrites getPreferredSize
     * @return
     */
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(preferredSize, preferredSize);
    }

    public double calculateScreenHeight(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return screenSize.getHeight();
    }

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
                if(!onlySetBorder) {
                    allButtons[homeFields[i][j]].setEnabled(true);
                    allButtons[homeFields[i][j]].setVisible(true);
                    allButtons[homeFields[i][j]].setBackground(backgroundColor);
                }
                allButtons[homeFields[i][j]].setBorder(new LineBorder(playerColors[i], borderThickness));
            }
        }
    }

    /**
     * customizes entry Fields
     * @param onlySetBorder
     */
    private void setUpEntryFields(boolean onlySetBorder){
        for (int i = 0; i< entryFields.length; i++){
            for (int j = 0; j< entryFields[i].length; j++){
                if(!onlySetBorder) {
                    allButtons[entryFields[i][j]].setEnabled(true);
                    allButtons[entryFields[i][j]].setVisible(true);
                    allButtons[entryFields[i][j]].setBackground(backgroundColor);
                }
                allButtons[entryFields[i][j]].setBorder(new LineBorder(playerColors[i], borderThickness));
            }
        }
    }

    /**
     * customizes path fields
     * @param onlySetBorder
     */
    private void setUpPath(boolean onlySetBorder){
        for (int pathField : pathFields) {
            if(!onlySetBorder) {
                allButtons[pathField].setEnabled(true);
                allButtons[pathField].setVisible(true);
                allButtons[pathField].setBackground(backgroundColor);
            }
            allButtons[pathField].setBorder(new LineBorder(pathBoarderColor, borderThickness));
        }
    }

    /**
     * customizes dice field
     * and adds action listener by calling following method {@link #addActionListenerToSelectableFields()}
     * @param borderColor
     */
    private void setUpDice(Color borderColor){
        allButtons[dice].setEnabled(true);
        allButtons[dice].setVisible(true);
        allButtons[dice].setBackground(backgroundColor);
        allButtons[dice].setBorder(new LineBorder(borderColor));
        allButtons[dice].setFont(new Font("Bodoni MT Black", Font.PLAIN, 40));
        ActionListener diceListener = e -> {
            control.throwDice(0); //0 stands for no Debug mode
            /*int steps = control.throwDice(-1);
            if(steps != -1)
                allFields[dice].setText(String.valueOf(steps));*/
        };
        allButtons[dice].addActionListener(diceListener);
    }

    /**
     * adds an action listener to {@link #selectableFields}
     * action listener calls {@link #removeAllX()} and {@link Control#fieldSelected(int)}
     * and sets the border of the selected button to black
     */
    public void addActionListenerToSelectableFields(){
        for(int i=0; i<selectableFields.length; i++){
            int finalI = i;
            ActionListener fieldListener = e -> {
                removeAllX();
                control.fieldSelected(selectableFields[finalI]); //Method for Controls
                allButtons[selectableFields[finalI]].setBorder(new LineBorder(Color.BLACK, borderThickness)); //select
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
            //lastDiceThrowJLabel[control.getCurrentPlayerFromGameManager()].setText("Last Throw: "+number);
        }
    }

    /**
     * adds a Label to each player that is 2 by 2 in size
     * @param firstLabelText
     */
    private void setUpLastDiceThrowJLabel(String firstLabelText){
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.weightx = buttonGab;
        gbc.weighty = buttonGab;

        gbc.gridx = 2;
        gbc.gridy = 0;
        lastDiceThrowJLabel[0] = createJLabel(firstLabelText, SwingConstants.CENTER);
        this.add(lastDiceThrowJLabel[0], gbc);

        gbc.gridx = 7;
        lastDiceThrowJLabel[1] = createJLabel(firstLabelText, SwingConstants.CENTER);
        this.add(lastDiceThrowJLabel[1], gbc);

        gbc.gridx = 2;
        gbc.gridy = 9;
        lastDiceThrowJLabel[3] = createJLabel(firstLabelText, SwingConstants.CENTER);
        this.add(lastDiceThrowJLabel[3], gbc);

        gbc.gridx = 7;
        lastDiceThrowJLabel[2] = createJLabel(firstLabelText, SwingConstants.CENTER);
        this.add(lastDiceThrowJLabel[2], gbc);
    }

    public void setLastDiceThrow(int player, int number){
        lastDiceThrowJLabel[player].setText("Last Throw: "+number);
    }

    /**
     * adds a Label to each player that is 4 by 2 in size
     * @param firstLabelMessage
     */
    private void setUpMessageToPlayerLabel(String firstLabelMessage){
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 4;
        gbc.gridheight = 2;
        gbc.weightx = buttonGab;
        gbc.weighty = buttonGab;

        gbc.gridx = 0;
        gbc.gridy = 2;
        messageToPlayerLabel[0] = createJLabel(firstLabelMessage, SwingConstants.CENTER);
        this.add(messageToPlayerLabel[0], gbc);

        gbc.gridx = 7;
        messageToPlayerLabel[1] = createJLabel(firstLabelMessage, SwingConstants.CENTER);
        this.add(messageToPlayerLabel[1], gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        messageToPlayerLabel[3] = createJLabel(firstLabelMessage, SwingConstants.CENTER);
        this.add(messageToPlayerLabel[3], gbc);

        gbc.gridx = 7;
        messageToPlayerLabel[2] = createJLabel(firstLabelMessage, SwingConstants.CENTER);
        this.add(messageToPlayerLabel[2], gbc);
    }

    /**
     * call method to write a message to a player that will appear in a Label
     * @param message
     * @param player
     */
    public void sendMessageToPlayer(String message, int player){
        if(player == -1)
            return;
        messageToPlayerLabel[player].setText(message);
    }

    /**
     * creates a JLabel
     * @param labelText
     * @param horizontalAlignment
     * @return
     */
    private JLabel createJLabel(String labelText, int horizontalAlignment){
        JLabel jLabel = new JLabel();
        jLabel.setHorizontalAlignment(horizontalAlignment);
        jLabel.setVerticalAlignment(SwingConstants.CENTER);
        jLabel.setFont(new Font("Bodoni MT Black", Font.PLAIN, (int) (calculateScreenHeight()/60)));
        jLabel.setText(labelText);
        return jLabel;
    }

    public void setControl(Control control){
        this.control = control;
    }
}