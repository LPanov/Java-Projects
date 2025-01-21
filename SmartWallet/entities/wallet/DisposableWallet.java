package entities.wallet;

import common.WalletStatus;

import java.util.Currency;
import java.util.UUID;

import static common.SystemErrors.INSUFFICIENT_FUNDS_IN_WALLET;
import static common.SystemErrors.WITHDRAWAL_LIMIT_REACHED_FOR_DISPOSABLE_WALLET;

public class DisposableWallet extends BaseWallet{
    private static final double INITIAL_BALANCE = 0;
    private int withdrawAttempts;

    public DisposableWallet(UUID ownerId, String ownerUsername, Currency currency) {
        super(ownerId, ownerUsername, currency, INITIAL_BALANCE);
        this.withdrawAttempts = 0;
    }

    @Override
    public void withdraw(double amount) {
        if (this.withdrawAttempts >= 2) {
            setStatus(WalletStatus.INACTIVE);
            throw new IllegalStateException(WITHDRAWAL_LIMIT_REACHED_FOR_DISPOSABLE_WALLET);
        }
        super.withdraw(amount);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Wallet [").append(getId()).append("] [DisposableWallet]:").append(System.lineSeparator());
        builder.append("Owner: ").append(getOwnerUsername()).append(System.lineSeparator());
        builder.append("Currency: ").append(getCurrency()).append(System.lineSeparator());
        builder.append("Balance: ").append(getBalance()).append(System.lineSeparator());
        builder.append("Status: ").append(getStatus()).append(System.lineSeparator());
        builder.append("Max withdrawals: ").append(2).append(System.lineSeparator());
        builder.append("Current withdrawals: ").append(this.withdrawAttempts).append(System.lineSeparator());

        return builder.toString();
    }
}
