package com.evieclient.commands

open class CommandException : Exception {
    constructor() : super() {}
    constructor(message: String?) : super(message) {}
}

