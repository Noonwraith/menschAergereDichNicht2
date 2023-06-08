package Logic;

import java.util.ArrayList;
import java.util.Random;

/**
 *  How many steps must a piece move?
 **/
public class Dice {
    int steps;
    boolean lock;


    private ArrayList<Integer> numbers = new ArrayList<>();
    private int[] numbersArray = {3, 5, 2, 1, 3, 2, 4, 5, 2, 4, 1, 5, 5, 5, 4, 4, 3, 6, 5, 4, 4, 6, 1, 4, 2, 1, 1, 4, 1, 6, 6, 6, 5, 6, 2, 5, 5, 4, 5, 5, 4, 5, 2, 3, 3, 1, 5, 2, 4, 2, 3, 4, 2, 4, 1, 2, 6, 2, 6, 5, 1, 3, 2, 4, 5, 1, 6, 1, 6, 1, 1, 1};
    private int moves = 0;
    private boolean debug = false;


    public int throwsDice(){
        if(!lock) {
            lock = true;
            Random random = new Random();
            steps = random.nextInt(6) + 1;
            //steps = 6;
            if(debug && numbersArray.length > moves) {
                steps = numbersArray[moves];
                moves++;
            }
            numbers.add(steps);

            return steps;
        }
        return -1;
    }

    public void unlockDice(){
        lock = false;
    }

    public boolean isLock(){
        return lock;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }


    public ArrayList<Integer> getNumbers() {
        return numbers;
    }
}
