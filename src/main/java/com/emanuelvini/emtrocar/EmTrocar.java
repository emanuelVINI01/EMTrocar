package com.emanuelvini.emtrocar;

import com.emanuelvini.emtrocar.command.registry.CommandRegistry;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class EmTrocar extends JavaPlugin {

    public void log(String message) {
        Bukkit.getConsoleSender().sendMessage("§e[EmTrocar] " + message);
    }
    @Override
    public void onEnable() {
        super.onEnable();
        log("Inicializando plugin...");

        val commandRegistry = new CommandRegistry(this);
        commandRegistry.register();

        log("§aPlugin inicializado com sucesso.");
    }
}
