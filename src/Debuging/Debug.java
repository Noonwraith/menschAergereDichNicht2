package Debuging;

import Controls.Control;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Debug {
    private final JsonHandler jsonHandler = new JsonHandler();
    private final Control control;
    boolean manuel = false;

    public Debug(Control control){
        this.control = control;
        if(!control.isDebugOn())
            jsonHandler.clearJsonFile();
    }

    public void simulateGame(){
        if (control.isDebugOn()) {
            List<MethodCall> methodCalls = jsonHandler.loadMethodCallsFromJson();
            System.out.println(methodCalls);
            if(methodCalls != null) {
                for (MethodCall methodCall : methodCalls) {
                    String methodName = methodCall.getMethodName();
                    Object[] params = methodCall.getParams();
                    simulateMethodCall(methodName, params);
                    //Wait for an input with the next move
                    if (manuel) {
                        System.out.println("wait on input...");
                        Scanner scanner = new Scanner(System.in);
                        scanner.nextInt();
                    } else {
                        //Waits 10 ms for the next move
                        try {
                            TimeUnit.MILLISECONDS.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            System.out.println("End of simulation!!!");
            control.setDebugOn(false);
        }
    }

    public void simulateMethodCall(String methodName, Object... params){
        switch (methodName) {
            case "throwDice" -> {
                int x = convertParamsToInt(params[0]);
                control.throwDice(x);
                break;
            }
            case "fieldSelected" -> {
                int x = convertParamsToInt(params[0]);
                control.fieldSelected(x);
                break;
            }
            case "nextPlayer" -> control.nextPlayer();
        }
    }

    public void addMethode(String methodName, Object... params){
        if(!control.isDebugOn())
            jsonHandler.addMethod(methodName, params);
    }

    private int convertParamsToInt(Object param) {
        if (param instanceof Double)
            param = ((Double) param).intValue();
        return (int) param;
    }
}