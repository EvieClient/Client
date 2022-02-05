package com.evieclient.settings;

/**
 * Basic setting class.
 * <p>All setting types will be extended from this.</p>
 *
 * @since 1.0.0
 **/
public abstract class Setting {

    public final String name;
    public final String description;

    /**
     * Create Setting.
     *
     * @param name        name of setting
     * @param description description displayed in tooltip
     **/
    public Setting(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
