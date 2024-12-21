package com.github.Z4TE.geminiChatBot;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PythonScriptCaller extends BukkitRunnable {

    public static String callPython (String pythonScriptPath, List<String> args) {

        List<String> command = new ArrayList<>();
        command.add("python3");
        command.add(pythonScriptPath);
        command.addAll(args);

        StringBuilder output = new StringBuilder();

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> {
            BufferedReader reader;
            try {
                ProcessBuilder processBuilder = new ProcessBuilder(command);
                Process process = processBuilder.start();
                reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

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
        });
        Bukkit.broadcastMessage(output.toString());

        executor.shutdown();
        return output.toString().trim();
    }

    @Override
    public void run() {

    }
}
