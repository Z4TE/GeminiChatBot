package com.github.Z4TE.geminiChatBot;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    public static String API_KEY;

    @Override
    public void onEnable() {
        // Plugin startup logic
        API_KEY =  this.getConfig().getString("API_KEY");

        if (API_KEY == null) {
            this.getConfig().set("API_KEY", "Enter here your Gemini API key");
            saveConfig();
        }
        Objects.requireNonNull(getCommand("gemini")).setExecutor(new Commands());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main getInstance() {
        return getPlugin(Main.class);
    }
}
