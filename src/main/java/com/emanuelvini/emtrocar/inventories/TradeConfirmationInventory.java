package com.emanuelvini.emtrocar.inventories;

import com.emanuelvini.emtrocar.EmTrocar;
import com.emanuelvini.emtrocar.manager.TradeManager;
import com.emanuelvini.emtrocar.model.PendingTradeConfirmation;
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
public class TradeConfirmationInventory implements InventoryProvider {

    private PendingTradeConfirmation confirmation;

    public static void open(Player player, PendingTradeConfirmation confirmation) {
        val provider = new TradeConfirmationInventory(confirmation);

        val inventory = SmartInventory.builder().provider(provider).title("&aMenu de Troca").build();

        inventory.open(player);
    }

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        fillRandomGlasses(inventoryContents);
        inventoryContents.set(1, 3, ClickableItem.empty(confirmation.getRequestItem()));
        inventoryContents.set(1, 5, ClickableItem.empty(confirmation.getAcceptItem()));

        inventoryContents.set(2, 4, ClickableItem.of(
                new ItemStackBuilder(Material.STAINED_GLASS).withData(5).buildStack(),
                e -> {
                    val target = Bukkit.getPlayer(confirmation.getTarget());
                    player.getInventory().addItem(confirmation.getAcceptItem());
                    target.getInventory().addItem(confirmation.getRequestItem());
                    EmTrocar.getInstance().getTradeManager().removeConfirmation(player.getName());
                    e.getWhoClicked().closeInventory();
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
