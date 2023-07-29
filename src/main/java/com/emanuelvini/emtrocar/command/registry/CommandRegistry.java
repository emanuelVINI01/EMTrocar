package com.emanuelvini.emtrocar.command.registry;

import com.emanuelvini.emtrocar.EmTrocar;
import com.emanuelvini.emtrocar.command.AcceptCommand;
import com.emanuelvini.emtrocar.command.TradeCommand;
import lombok.AllArgsConstructor;
import lombok.val;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.saiintbrisson.minecraft.command.message.MessageType;

@AllArgsConstructor
public class CommandRegistry {

    private EmTrocar plugin;

    public void register() {

        val bukkitFrame = new BukkitFrame(plugin);

        bukkitFrame.registerCommands(new TradeCommand(plugin.getTradeManager()), new AcceptCommand(plugin.getTradeManager()));

        bukkitFrame.getMessageHolder().setMessage(MessageType.INCORRECT_USAGE, "Uso: /{}");
    }

}
