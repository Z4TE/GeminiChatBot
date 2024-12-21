package com.github.Z4TE.geminiChatBot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PythonScriptCaller {

    public static String callPython (String pythonScriptPath, List<String> args){

        List<String> command = new ArrayList<>();
        command.add("python3");
        command.add(pythonScriptPath);
        command.addAll(args);

        StringBuilder output = new StringBuilder();

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();

            if (exitCode != 0) {
                throw new RuntimeException("Python script exited with error code: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return output.toString().trim();
    }
}
