package com.github.Z4TE.geminiChatBot;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PythonScriptCaller extends BukkitRunnable {

    private final String pythonScriptPath;
    private final List<String> args;

    public PythonScriptCaller(String pythonScriptPath, List<String> args) {
        this.pythonScriptPath = pythonScriptPath;
        this.args = args;
    }

    @Override
    public void run() {
        List<String> command = new ArrayList<>();
        command.add("python3");
        command.add(pythonScriptPath);
        command.addAll(args);

        StringBuilder output = new StringBuilder();

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
            output.append("Error: ").append(e.getMessage());
        }

        String finalOutput = output.toString().trim();
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            Bukkit.broadcastMessage(finalOutput);
        });
    }

    public static void callPythonAsync(String pythonScriptPath, List<String> args) {
        new PythonScriptCaller(pythonScriptPath, args).runTaskAsynchronously(Main.getInstance());
    }
}
