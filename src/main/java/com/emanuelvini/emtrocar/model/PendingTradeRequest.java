package com.emanuelvini.emtrocar.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
@Builder(builderMethodName = "create")
public class PendingTradeRequest {

    private ItemStack requestItem;

    private String target;

    private String sender;

    private boolean accepted;

}
