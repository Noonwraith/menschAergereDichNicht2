package Debuging;

import Controls.Control;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Debug {
    private JsonHandler jsonHandler = new JsonHandler();
    private Control control;

    boolean manuel = false;

    public Debug(Control control){
        this.control = control;
        if(!control.isDebugOn()){
            jsonHandler.clearJsonFile();
        }
    }


    public void simulateGame(){
        if (control.isDebugOn()) {
            List<MethodCall> methodCalls = jsonHandler.loadMethodCallsFromJson();
            System.out.println(methodCalls);
            for (MethodCall methodCall : methodCalls) {
                String methodName = methodCall.getMethodName();
                Object[] params = methodCall.getParams();
                simulateMethodCall(methodName, params);

                //Wait for an input with the next move

                if(manuel) {
                    System.out.println("wait on input...");
                    Scanner scanner = new Scanner(System.in);
                    int x = scanner.nextInt();
                }
                else {
                    //Waits 500 ms for the next move
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("End of simulation!!!");
            control.setDebugOn(false);
        }
    }

    public void simulateMethodCall(String methodName, Object... params){
        if(methodName.equals("throwDice")){
            int x = convertParamsToInt(params[0]);
            control.throwDice(x);
        }else if(methodName.equals("fieldSelected")){
            int x = convertParamsToInt(params[0]);
            control.fieldSelected(x);
        }


    }


    public void addMethode(String methodName, Object... params){
        if(!control.isDebugOn()) {
            jsonHandler.addMethod(methodName, params);
        }
    }

    private int convertParamsToInt(Object param) {
        if (param instanceof Double) {
            param = ((Double) param).intValue();
        }
        return (int) param;
    }

}
