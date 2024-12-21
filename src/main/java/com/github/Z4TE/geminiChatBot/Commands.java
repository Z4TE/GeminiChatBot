package com.github.Z4TE.geminiChatBot;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.github.Z4TE.geminiChatBot.Main.API_KEY;

public class Commands implements CommandExecutor {

    public static String getResourcePath(String resourceName) {
        // リソースの絶対パスを取得
        return Objects.requireNonNull(PythonScriptCaller.class.getClassLoader().getResource(resourceName)).getPath();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (command.getName().equalsIgnoreCase("gemini")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(ChatColor.RED + "This command can only be executed by players");
                return true;
            }

            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "Too few arguments");
                return true;
            }
            String pythonScriptPath = "callGeminiAPI.py";

            List<String> commandToRun = new ArrayList<>();
            String messageToSend = String.join(" ", args);
            commandToRun.add(API_KEY);
            commandToRun.add(messageToSend);

            String output = PythonScriptCaller.callPython(pythonScriptPath, commandToRun);

            player.sendMessage("Working...");
            player.sendMessage(output);
            return true;
        }

        return false;
    }
}
