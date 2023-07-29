package com.emanuelvini.emtrocar.inventories;

import com.emanuelvini.emtrocar.EmTrocar;
import com.emanuelvini.emtrocar.model.PendingTradeRequest;
import com.emanuelvini.emtrocar.util.ItemStackBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import lombok.AllArgsConstructor;
import lombok.val;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Random;

@AllArgsConstructor
public class TradeInventory implements InventoryProvider {

    private String target;

    public static void open(Player player, String target) {
        val provider = new TradeInventory(target);

        val inventory = SmartInventory.builder().provider(provider).title("&aMenu de Troca").build();

        inventory.open(player);
    }

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        fillRandomGlasses(inventoryContents);
        inventoryContents.set(1, 3, ClickableItem.of(
                new ItemStackBuilder(Material.GLASS).withName("&aClique para definir o item").buildStack(),
                e -> {

                    if (player.getItemInHand().getType() != Material.AIR) {
                        inventoryContents.set(1, 3,
                                ClickableItem.empty(
                                        player.getItemInHand()
                                )
                        );
                        EmTrocar.getInstance().getTradeManager().addRequestPending(
                                PendingTradeRequest
                                        .create()
                                        .sender(player.getName())
                                        .target(target)
                                        .requestItem(player.getItemInHand())
                                        .build(),
                                (tradeManager) -> {
                                    tradeManager.removePending(player.getName());
                                    player.sendMessage("§cSeu pedido de troca expirou!");
                                }
                        );
                        player.getInventory().remove(player.getItemInHand());
                        player.closeInventory();
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
