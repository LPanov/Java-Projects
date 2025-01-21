package entities.user;

import common.WalletStatus;

import java.util.Currency;
import java.util.UUID;

public interface User {
    UUID getId();
    String getUsername();

    String getPassword();
}
