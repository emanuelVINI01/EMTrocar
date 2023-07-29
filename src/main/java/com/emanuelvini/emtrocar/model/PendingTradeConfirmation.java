package com.emanuelvini.emtrocar.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
@Builder(builderMethodName = "create")
public class PendingTradeConfirmation {

    private ItemStack requestItem;

    private ItemStack acceptItem;

    private String target;

    private String sender;

    private boolean accepted;

}