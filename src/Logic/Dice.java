package Logic;

import Controls.Control;
import java.util.Random;

/**
 *  How many steps must a piece move?
 **/
public class Dice {
    int steps;
    boolean lock;

    public int throwsDice(int debugSteps, Control control){
        if(!lock) {
            lock = true;
            Random random = new Random();
            steps = random.nextInt(6) + 1;
            if(debugSteps != 0)// If it does not come from the GUI
                steps = debugSteps;
            control.saveDiceNumberInDebug(steps);
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
