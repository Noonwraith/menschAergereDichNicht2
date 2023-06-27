package Debuging;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonHandler {
    private List<MethodCall> methodCalls;
    private String fileName = "Debug.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public JsonHandler() {
        methodCalls = loadMethodCallsFromJson();
        //gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void addMethod(String methodName, Object... params) {
        MethodCall methodCall = new MethodCall(methodName, params);
        methodCalls.add(methodCall);
        saveMethodCallsToJson();
    }

    public void saveMethodCallsToJson() {
        try (Writer writer = new FileWriter(fileName)) {
            gson.toJson(methodCalls, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<MethodCall> loadMethodCallsFromJson() {
        List<MethodCall> loadedMethodCalls = new ArrayList<>();
        try (Reader reader = new FileReader(fileName)) {
            Type listType = new TypeToken<List<MethodCall>>() {}.getType();
            loadedMethodCalls = gson.fromJson(reader, listType);
        } catch (FileNotFoundException e) {
            // The file does not exist yet (first start)
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedMethodCalls;
    }

    public void clearJsonFile() {
        File file = new File(fileName);
        if (file.exists())
            file.delete();
        methodCalls.clear();
    }
}