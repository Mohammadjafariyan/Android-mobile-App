package service;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Service;
import android.content.Context;

/**
 * A login screen that offers login via email/password.
 */

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.NetworkErrorException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class AccountUtils {

    public static final String ACCOUNTS_TYPE = "com.bulus";

    public static Account getSingleAccount(Context context) {
        String accountType = ACCOUNTS_TYPE;  // Replace with your actual account type

        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccountsByType(accountType);

        if (accounts.length > 0) {
            return accounts[0];
        } else {
            return null;  // No accounts found
        }
    }


    public static String getPassword(Context context) {
        String accountType = ACCOUNTS_TYPE;  // Replace with your actual account type

        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccountsByType(accountType);

        if (accounts.length > 0) {
            String password = accountManager.getPassword(accounts[0]);
            return password;
        } else {
            return null;  // No accounts found
        }
    }

    // Other methods...
}
