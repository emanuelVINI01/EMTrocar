package com.emanuelvini.emtrocar.manager;

import com.emanuelvini.emtrocar.EmTrocar;
import com.emanuelvini.emtrocar.model.PendingTradeConfirmation;
import com.emanuelvini.emtrocar.model.PendingTradeRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.function.Consumer;

@AllArgsConstructor
public class TradeManager {

    private EmTrocar plugin;

    private final HashMap<String, PendingTradeRequest> requests = new HashMap<>();

    private final HashMap<String, PendingTradeConfirmation> confirmationRequest = new HashMap<>();

    @SneakyThrows
    public PendingTradeRequest getRequestPending(String name) {
        return requests.get(name);
    }

    @SneakyThrows
    public void addRequestPending(PendingTradeRequest request, Consumer<TradeManager> consumer) {
        requests.put(request.getSender(), request);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (!request.isAccepted()) consumer.accept(this);

        }, 20L * 30);
    }

    @SneakyThrows
    public PendingTradeConfirmation getRequestConfirmation(String name) {
        return confirmationRequest.get(name);
    }

    @SneakyThrows
    public void addRequestConfirmation(PendingTradeConfirmation request, Consumer<TradeManager> consumer) {
        confirmationRequest.put(request.getSender(), request);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (!request.isAccepted()) consumer.accept(this);

        }, 20L * 30);
    }

    @SneakyThrows
    public void removePending(String name) {
        requests.remove(name);
    }

    @SneakyThrows
    public void removeConfirmation(String name) {
        confirmationRequest.remove(name);
    }

}
