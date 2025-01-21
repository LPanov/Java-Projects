package entities.wallet;

import java.util.Currency;
import java.util.UUID;

public class StandardWallet extends BaseWallet{
    private static final double INITIAL_BALANCE = 20;

    public StandardWallet(UUID ownerId, String ownerUsername, Currency currency) {
        super(ownerId, ownerUsername, currency, INITIAL_BALANCE);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Wallet [").append(getId()).append("] [DisposableWallet]:").append(System.lineSeparator());
        builder.append("Owner: ").append(getOwnerUsername()).append(System.lineSeparator());
        builder.append("Currency: ").append(getCurrency()).append(System.lineSeparator());
        builder.append("Balance: ").append(getBalance()).append(System.lineSeparator());
        builder.append("Status: ").append(getStatus()).append(System.lineSeparator());

        return builder.toString();
    }
}
