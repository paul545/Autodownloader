package org.jdownloader.extensions.autodownloader;

import org.appwork.storage.config.ConfigUtils;
import org.appwork.storage.config.JsonConfig;
import org.appwork.storage.config.handler.BooleanKeyHandler;
import org.appwork.storage.config.handler.ObjectKeyHandler;
import org.appwork.storage.config.handler.StorageHandler;
import org.appwork.utils.Application;

public class CFG_AUTODOWNLOADER {
    public static void main(String[] args) {
        ConfigUtils.printStaticMappings(AutodownloaderConfig.class, "Application.getResource(\"cfg/\" +" + AutodownloaderExtension.class.getSimpleName() + ".class.getName())");
    }

    // Static Mappings for interface org.jdownloader.extensions.autodownloader.AutodownloaderConfig
    public static final AutodownloaderConfig                 CFG           = JsonConfig.create(Application.getResource("cfg/" + AutodownloaderExtension.class.getName()), AutodownloaderConfig.class);
    public static final StorageHandler<AutodownloaderConfig> SH            = (StorageHandler<AutodownloaderConfig>) CFG._getStorageHandler();
    // Let's do this mapping here. If we map all methods to static handlers, access is faster, and we get an error
    // on init if mappings are wrong
    public static final BooleanKeyHandler                    DEBUG_MODE    = SH.getKeyHandler("DebugMode", BooleanKeyHandler.class);
    public static final ObjectKeyHandler                     ENTRY_LIST    = SH.getKeyHandler("EntryList", ObjectKeyHandler.class);
    public static final BooleanKeyHandler                    FRESH_INSTALL = SH.getKeyHandler("FreshInstall", BooleanKeyHandler.class);
    public static final BooleanKeyHandler                    ENABLED       = SH.getKeyHandler("Enabled", BooleanKeyHandler.class);
    public static final BooleanKeyHandler                    GUI_ENABLED   = SH.getKeyHandler("GuiEnabled", BooleanKeyHandler.class);
}
