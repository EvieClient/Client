package com.evieclient.utils.settings

import com.evieclient.modules.Module
import java.util.*


class MultiChoice(name: String, module: Module, showInMenu: Boolean, defaultMode: String?, vararg modes: String?) : Setting() {
    var index = 0

    var modes: List<String>? = null

    init {
        this.name = name
        this.module = module
        this.modes = Arrays.asList<String>(*modes)
        index = (this.modes as MutableList<String>).indexOf(defaultMode)
        this.showInMenu = showInMenu
    }

    fun getMode(): String? {
        return modes!![index]
    }

    fun setMode(mode: String?) {
        index = modes!!.indexOf(mode)
    }

    fun `is`(mode: String?): Boolean {
        return index == modes!!.indexOf(mode)
    }

    fun cycle() {
        if (index < modes!!.size - 1) {
            index++
        } else {
            index = 0
        }
    }

    fun getValueName(): String? {
        return modes!![index]
    }

    fun increment() {
        if (index < modes!!.size - 1) {
            index++
        } else {
            index = 0
        }
    }

}
