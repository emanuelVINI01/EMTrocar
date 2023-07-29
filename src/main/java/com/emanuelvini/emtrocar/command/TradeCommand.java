package com.emanuelvini.emtrocar.command;

import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.entity.Player;

public class TradeCommand {

    @Command(
            name = "trade",
            aliases = { "trocar" }
    )

    public void onTradeCommand(Context<Player> context, Player target) {
        
    }

}
