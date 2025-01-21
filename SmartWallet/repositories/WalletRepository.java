package repositories;

import entities.wallet.Wallet;

import java.util.*;

public class WalletRepository implements Repository<Wallet, UUID>{
    private Map<UUID, Wallet> wallets;

    public WalletRepository() {
        this.wallets = new HashMap<>();
    }

    @Override
    public void save(UUID uuid, Wallet entity) {
        wallets.put(uuid, entity);
    }

    @Override
    public Wallet getById(UUID uuid) {
        return wallets.get(uuid);
    }

    @Override
    public List<Wallet> getAll() {
        return this.wallets.values().stream().toList();
    }
}
