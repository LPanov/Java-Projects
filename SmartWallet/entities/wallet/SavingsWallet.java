package entities.wallet;

import common.WalletStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;

import static common.SystemErrors.SAVINGS_PERIOD_NOT_CONCLUDED_YET;

public class SavingsWallet extends BaseWallet{
    private static final double INITIAL_BALANCE = 10;
    private LocalDateTime savingPeriod;

    public SavingsWallet(UUID ownerId, String ownerUsername, Currency currency) {
        super(ownerId, ownerUsername, currency, INITIAL_BALANCE);
        this.savingPeriod = LocalDateTime.now().plusMinutes(2);
    }

    @Override
    public void withdraw(double amount) {
        if (LocalDateTime.now().isBefore(savingPeriod)) {
            Duration duration = Duration.between(savingPeriod, LocalDateTime.now());
            long secondsToEnd = duration.toSeconds();
            throw new IllegalStateException(SAVINGS_PERIOD_NOT_CONCLUDED_YET.formatted(secondsToEnd));
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
        Duration duration = Duration.between(savingPeriod, LocalDateTime.now());
        long secondsToEnd = duration.toSeconds();
        builder.append("Saving period ends within: ").append(secondsToEnd).append(System.lineSeparator());

        return builder.toString();
    }
}
