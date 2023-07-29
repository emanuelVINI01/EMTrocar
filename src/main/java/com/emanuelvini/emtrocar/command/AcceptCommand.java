package com.emanuelvini.emtrocar.command;

import com.emanuelvini.emtrocar.inventories.TradeSecondInventory;
import com.emanuelvini.emtrocar.manager.TradeManager;
import lombok.AllArgsConstructor;
import lombok.val;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class AcceptCommand {

    private TradeManager tradeManager;

    @Command(
            name = "accept",
            aliases = {"aceitar"}
    )

    public void onAccept(Context<Player> context, Player sender) {
        val request = tradeManager.getRequestPending(sender.getName());
        if (request == null) {
            context.sendMessage("§cEsse pedido não existe.");
            return;
        }

        TradeSecondInventory.open(context.getSender(), request);
    }

}
