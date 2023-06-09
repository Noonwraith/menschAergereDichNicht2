package Debuging;

import Controls.Control;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Debug {
    private JsonHandler jsonHandler = new JsonHandler();
    private Control control;

    public Debug(Control control){
        this.control = control;
        if(control.isDebugOn() == false){
            jsonHandler.clearJsonFile();
        }
    }


    public void simulateGame(){
        if (control.isDebugOn()) {
            List<MethodCall> methodCalls = jsonHandler.loadMethodCallsFromJson();
            for (MethodCall methodCall : methodCalls) {
                String methodName = methodCall.getMethodName();
                Object[] params = methodCall.getParams();
                simulateMethodCall(methodName, params);


                //Wait for an input with the next move
                Scanner scanner = new Scanner(System.in);
                int x = scanner.nextInt();

                //Waits 500 ms for the next move
                /*try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

            }
        }
    }

    public void simulateMethodCall(String methodName, Object... params){
        if(methodName.equals("throwDice")){
            int x = convertParamsToInt(params[0]);
            control.throwDice(x);
        }else if(methodName.equals("fieldSelected")){
            System.out.println("--------------------------------");
            int x = convertParamsToInt(params[0]);
            control.fieldSelected(x);
        }


    }


    public void addMethode(String methodName, Object... params){
        if(control.isDebugOn() == false) {
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
