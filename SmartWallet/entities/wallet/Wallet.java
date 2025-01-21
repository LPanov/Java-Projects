package entities.wallet;

import common.WalletStatus;

import java.util.Currency;
import java.util.UUID;

public interface Wallet {
    UUID getId();
    UUID getOwnerId();
    String getOwnerUsername();
    Currency getCurrency();
    double getBalance();
    WalletStatus getStatus();
    void setStatus(WalletStatus newStatus);

    void deposit(double amount);
    void withdraw(double amount);

}
