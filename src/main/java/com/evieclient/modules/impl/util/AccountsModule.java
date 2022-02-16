package com.evieclient.modules.impl.util;

import com.evieclient.modules.Category;
import com.evieclient.modules.Module;


public class AccountsModule extends Module {

    public AccountsModule() {
        super("Accounts", "Changes the current logged-in account.", Category.UTIL, true);
    }
}