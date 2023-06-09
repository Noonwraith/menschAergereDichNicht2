package Debuging;

import java.util.Arrays;

public class MethodCall {


    private String methodName;
    private Object[] params;

    public MethodCall(String methodName, Object[] params) {
        this.methodName = methodName;
        this.params = params;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object[] getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "MethodCall{" +
                "methodName='" + methodName + '\'' +
                ", params=" + Arrays.toString(params) +
                '}';
    }


}
