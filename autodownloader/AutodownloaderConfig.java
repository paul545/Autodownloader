package org.jdownloader.extensions.autodownloader;

import java.util.List;

import org.appwork.storage.config.annotations.AboutConfig;
import org.appwork.storage.config.annotations.DefaultJsonObject;
import org.appwork.storage.config.annotations.DescriptionForConfigEntry;
import org.jdownloader.extensions.autodownloader.model.ProfileEntryStorable;

import jd.plugins.ExtensionConfigInterface;;

public interface AutodownloaderConfig extends ExtensionConfigInterface {
    @AboutConfig
    @DescriptionForConfigEntry("Contains all bandwidth profiles")
    @DefaultJsonObject("[]")
    List<ProfileEntryStorable> getEntryList();

    void setEntryList(List<ProfileEntryStorable> profileStorables);

    @AboutConfig
    @DescriptionForConfigEntry("Adds a Debug Action (Message). Requires JD restart.")
    @DefaultJsonObject("false")
    boolean isDebugMode();

    void setDebugMode(boolean debug);
}
