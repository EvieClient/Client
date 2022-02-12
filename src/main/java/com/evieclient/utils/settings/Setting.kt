package com.evieclient.utils.settings

import com.evieclient.modules.Module

open class Setting {
    var name: String? = null
    var focused = false
    var showInMenu: Boolean = false
    var module: Module? = null
}
