package entities.wallet;

import common.WalletStatus;

import java.util.Currency;
import java.util.UUID;

import static common.SystemErrors.INSUFFICIENT_FUNDS_IN_WALLET;
import static common.SystemErrors.NO_OPERATIONS_ALLOWED_FOR_NON_ACTIVE_WALLET;

// TODO: Implement the entity
public abstract class BaseWallet implements Wallet{
    private UUID id;
    private UUID ownerId;
    private String ownerUsername;
    private Currency currency;
    private double balance;
    private WalletStatus status;

    public BaseWallet(UUID ownerId, String ownerUsername, Currency currency, double initialBalance) {
        this.id = UUID.randomUUID();
        this.ownerId = ownerId;
        this.ownerUsername = ownerUsername;
        this.currency = currency;
        this.balance = initialBalance;
        this.status = WalletStatus.ACTIVE;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public UUID getOwnerId() {
        return ownerId;
    }

    @Override
    public String getOwnerUsername() {
        return ownerUsername;
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public WalletStatus getStatus() {
        return status;
    }

    @Override
    public void deposit(double amount) {
        if (this.status == WalletStatus.INACTIVE) {
            throw new IllegalStateException(NO_OPERATIONS_ALLOWED_FOR_NON_ACTIVE_WALLET);
        }

        if (amount > 0) {
            this.balance += amount;
        }
     }

    @Override
    public void withdraw(double amount) {
        if (this.status == WalletStatus.INACTIVE) {
            throw new IllegalStateException(NO_OPERATIONS_ALLOWED_FOR_NON_ACTIVE_WALLET);
        }

        if (this.balance < amount) {
            throw new IllegalArgumentException(INSUFFICIENT_FUNDS_IN_WALLET);
        }

        if (amount > 0) {
            this.balance -= amount;
        }
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public void setStatus(WalletStatus status) {
        this.status = status;
    }
}
