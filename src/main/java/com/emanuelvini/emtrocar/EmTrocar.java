package com.emanuelvini.emtrocar;

import com.emanuelvini.emtrocar.manager.TradeManager;
import com.emanuelvini.emtrocar.command.registry.CommandRegistry;
import fr.minuskube.inv.InventoryManager;
import lombok.Getter;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class EmTrocar extends JavaPlugin {

    @Getter
    private static EmTrocar instance;

    @Getter
    private TradeManager tradeManager;

    public void log(String message) {
        Bukkit.getConsoleSender().sendMessage("§e[EmTrocar] " + message);
    }
    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;
        new InventoryManager(this).init();
        log("Inicializando plugin...");

        tradeManager = new TradeManager(this);

        val commandRegistry = new CommandRegistry(this);
        commandRegistry.register();



        log("§aPlugin inicializado com sucesso.");
    }
}
