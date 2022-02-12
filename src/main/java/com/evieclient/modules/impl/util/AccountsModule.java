package com.evieclient.modules.impl.util;

import com.evieclient.modules.Category;
import com.evieclient.modules.Module;
import com.evieclient.utils.SessionChanger;


public class AccountsModule extends Module {

    public AccountsModule() {
        super("Accounts", "Changes the current logged-in account.", Category.UTIL, true);
    }

    @Override
    public void setupModule() {
        try {
            SessionChanger.getInstance().setUserOffline("billy", "c98187a9-e17b-3f48-8a33-882e62d18d19");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}