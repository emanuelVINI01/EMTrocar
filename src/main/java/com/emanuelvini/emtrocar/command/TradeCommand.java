package com.emanuelvini.emtrocar.command;

import com.emanuelvini.emtrocar.inventories.TradeInventory;
import com.emanuelvini.emtrocar.manager.TradeManager;
import lombok.AllArgsConstructor;
import lombok.var;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class TradeCommand {


    private TradeManager tradeManager;

    @Command(
            name = "trade",
            aliases = { "trocar" }
    )

    public void onTradeCommand(Context<Player> context, Player target) {
        var request = tradeManager.getRequestPending(context.getSender().getName());
        if (request != null) {
            context.sendMessage("§cVocê já tem um pedido de troca pendente, aguarde.");
            return;
        }

        TradeInventory.open(context.getSender(), target.getName());

    }

}
