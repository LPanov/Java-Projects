package services.imp;

import common.WalletStatus;
import core.UserSessionManager;
import entities.user.User;
import entities.wallet.DisposableWallet;
import entities.wallet.SavingsWallet;
import entities.wallet.StandardWallet;
import entities.wallet.Wallet;
import repositories.UserRepository;
import repositories.WalletRepository;
import services.WalletService;

import java.util.Currency;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static common.LogMessages.*;
import static common.SystemErrors.*;

// TODO:
// 1. Implement all methods
// 2. Make sure this service implementation has dependency a SessionManager
// so you can determine which is the currently logged in user.
public class WalletServiceImpl implements WalletService {
    private WalletRepository wallets;
    private UserSessionManager sessionManager;
    private UserRepository users;

    public WalletServiceImpl(UserSessionManager sessionManager) {
        this.wallets = new WalletRepository();
        this.users = new UserRepository();
        this.sessionManager = sessionManager;
    }

    @Override
    public String createNewWallet(Currency currency, String walletType) {
        if (!sessionManager.hasActiveSession()) {
            throw new IllegalStateException(NO_ACTIVE_USER_SESSION_FOUND);
        }

        User currentUser = sessionManager.getActiveSession();
        Wallet wallet;
        if (walletType.equals("Standard")) {
            boolean standardWalletExist = wallets.getAll().stream().anyMatch(w -> w instanceof StandardWallet);
            if (standardWalletExist) {
                throw new IllegalStateException(STANDARD_WALLET_COUNT_LIMIT_REACHED);
            }

            wallet = new StandardWallet(currentUser.getId(), currentUser.getUsername(), currency);
        }
        else if (walletType.equals("Disposable")) {
            wallet = new DisposableWallet(currentUser.getId(), currentUser.getUsername(), currency);
        }
        else if (walletType.equals("Savings")) {
            wallet = new SavingsWallet(currentUser.getId(), currentUser.getUsername(), currency);
        }
        else {
            throw new IllegalArgumentException(INCORRECT_WALLET_TYPE);
        }

        wallets.save(wallet.getId(), wallet);
        return wallet.toString();
    }

    @Override
    public String getMyWallets() {
        User currentUser = sessionManager.getActiveSession();

        StringBuilder builder = new StringBuilder();
        List<Wallet> userWallets = wallets.getAll().stream().filter(w -> w.getOwnerId().equals(currentUser.getId())).collect(Collectors.toList());

        if (userWallets.isEmpty()) {
            builder.append(ZERO_WALLETS).append(System.lineSeparator());
        }
        else {
            userWallets.forEach(wallet -> builder.append(wallet.toString()).append(System.lineSeparator()));
        }

        return builder.toString();
    }

    @Override
    public String deposit(UUID walletId, double amount) {
        if (!sessionManager.hasActiveSession()) {
            throw new IllegalStateException(NO_ACTIVE_USER_SESSION_FOUND);
        }

        User currentUser = sessionManager.getActiveSession();
        Wallet wallet = wallets.getById(walletId);

        if (wallet == null || wallet.getOwnerId() != currentUser.getId()) {
            throw new IllegalStateException(WALLET_NOT_ASSOCIATED_WITH_THIS_USER.formatted(currentUser.getUsername()));
        }

        wallet.deposit(amount);
        return SUCCESSFULLY_DEPOSITED_AMOUNT.formatted(wallet.getBalance(), wallet.getCurrency());
    }

    @Override
    public String transfer(UUID walletId, String receiverUsername, double amount) {
        if (!sessionManager.hasActiveSession()) {
            throw new IllegalStateException(NO_ACTIVE_USER_SESSION_FOUND);
        }

        User currentUser = sessionManager.getActiveSession();
        Wallet wallet = wallets.getById(walletId);

        if (wallet == null || wallet.getOwnerId() != currentUser.getId()) {
            throw new IllegalStateException(WALLET_NOT_ASSOCIATED_WITH_THIS_USER.formatted(currentUser.getUsername()));
        }

        List<Wallet> ownerWallets = wallets.getAll().stream()
                .filter(w -> w.getOwnerUsername().equals(receiverUsername))
                .collect(Collectors.toList());
        boolean hasStandard = ownerWallets.stream()
                .anyMatch(w -> w instanceof StandardWallet);

        if (!hasStandard) {
            throw new IllegalStateException(NO_WALLET_FOUND_FOR_RECEIVER.formatted(receiverUsername));
        }

        Wallet receiverWallet = ownerWallets.stream()
                .filter(w -> w instanceof StandardWallet).findFirst().get();
        if (wallet.getStatus() != WalletStatus.ACTIVE ||
            receiverWallet.getStatus() != WalletStatus.ACTIVE ||
            wallet.getCurrency() != receiverWallet.getCurrency()) {

            throw new IllegalStateException(TRANSFER_CRITERIA_NOT_MET);
        }

        wallet.withdraw(amount);
        receiverWallet.deposit(amount);

        return SUCCESSFUL_FUNDS_TRANSFER.formatted(currentUser.getUsername(), amount, receiverUsername, wallet.getBalance());
    }

    @Override
    public String changeWalletStatus(UUID walletId, String newStatus) {
        if (!sessionManager.hasActiveSession()) {
            throw new IllegalStateException(NO_ACTIVE_USER_SESSION_FOUND);
        }

        User currentUser = sessionManager.getActiveSession();
        Wallet wallet = wallets.getById(walletId);

        if (wallet == null || wallet.getOwnerId() != currentUser.getId()) {
            throw new IllegalStateException(WALLET_NOT_ASSOCIATED_WITH_THIS_USER.formatted(currentUser.getUsername()));
        }

        if (newStatus.equals("ACTIVE")) {
            wallet.setStatus(WalletStatus.ACTIVE);
        }
        else if (newStatus.equals("INACTIVE")) {
            wallet.setStatus(WalletStatus.INACTIVE);
        }
        else {
            throw new IllegalArgumentException(INCORRECT_WALLET_STATUS);
        }

        return SUCCESSFULLY_CHANGED_WALLET_STATUS.formatted(newStatus);
    }
}
