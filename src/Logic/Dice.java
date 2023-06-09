package Logic;

import java.util.ArrayList;
import java.util.Random;

/**
 *  How many steps must a piece move?
 **/
public class Dice {
    int steps;
    boolean lock;



    public int throwsDice(int debugSteps){
        if(!lock) {
            lock = true;
            Random random = new Random();
            steps = random.nextInt(6) + 1;
            if(debugSteps != -1){
                steps = debugSteps;
            }

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


}
