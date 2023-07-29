package com.emanuelvini.emtrocar.inventories;

import com.emanuelvini.emtrocar.EmTrocar;
import com.emanuelvini.emtrocar.model.PendingTradeConfirmation;
import com.emanuelvini.emtrocar.model.PendingTradeRequest;
import com.emanuelvini.emtrocar.util.ItemStackBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import lombok.AllArgsConstructor;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Random;

@AllArgsConstructor
public class TradeSecondInventory implements InventoryProvider {
    private PendingTradeRequest request;

    public static void open(Player player, PendingTradeRequest request) {
        val provider = new TradeSecondInventory(request);

        val inventory = SmartInventory.builder().provider(provider).title("&aMenu de Troca").build();

        inventory.open(player);
    }

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        fillRandomGlasses(inventoryContents);
        inventoryContents.set(1, 3, ClickableItem.empty(request.getRequestItem()));
        inventoryContents.set(1, 5, ClickableItem.of(
                new ItemStackBuilder(Material.GLASS).withName("&aClique para definir o item").buildStack(),
                e -> {

                    if (player.getItemInHand().getType() != Material.AIR) {
                        inventoryContents.set(1, 3,
                                ClickableItem.empty(
                                        player.getItemInHand()
                                )
                        );
                        val pendingTradeConfirmation = PendingTradeConfirmation
                                .create()
                                .acceptItem(player.getItemInHand())
                                .requestItem(request.getRequestItem())
                                .sender(request.getSender())
                                .target(request.getTarget())
                                .build();
                        EmTrocar.getInstance().getTradeManager().addRequestConfirmation(
                                pendingTradeConfirmation,
                                (tradeManager) -> {
                                    tradeManager.removeConfirmation(player.getName());
                                    player.sendMessage("§cSeu pedido de troca expirou.");
                                    val sender = Bukkit.getPlayer(request.getSender());

                                    sender.getInventory().addItem(pendingTradeConfirmation.getRequestItem());
                                    player.getInventory().addItem(pendingTradeConfirmation.getAcceptItem());
                                }
                        );
                        player.getInventory().remove(player.getItemInHand());
                        player.closeInventory();
                        val sender = Bukkit.getPlayer(request.getSender());
                        TradeConfirmationInventory.open(sender, pendingTradeConfirmation);
                    }

                }
        ));
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {
        fillRandomGlasses(inventoryContents);
    }

    private void fillRandomGlasses(InventoryContents inventoryContents) {
        val random = new Random();
        val glass = new ItemStackBuilder(Material.STAINED_GLASS_PANE).
                withData(random.nextInt(15)).buildStack();

        inventoryContents.fillBorders(ClickableItem.empty(glass));
    }
}
