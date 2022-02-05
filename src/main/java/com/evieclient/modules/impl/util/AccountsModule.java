package com.evieclient.modules.impl.util;

import com.evieclient.modules.Category;
import com.evieclient.modules.Module;
import com.evieclient.utils.SessionChanger;
import io.sentry.Sentry;


public class AccountsModule extends Module {

    public AccountsModule() {
        super("Accounts", "Changes the current logged-in account.", Category.UTIL, true);
    }

    @Override
    public void setupModule() {
        try {
            SessionChanger.getInstance().setUserOffline("evieclient");
        } catch (IllegalAccessException e) {
            Sentry.captureException(e);
        }

    }
}