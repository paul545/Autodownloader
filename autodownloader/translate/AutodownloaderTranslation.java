package org.jdownloader.extensions.autodownloader.translate;

import org.appwork.txtresource.Default;
import org.appwork.txtresource.TranslateInterface;

public interface AutodownloaderTranslation extends TranslateInterface {
    @Default(lngs = { "en" }, values = { "Parameter" })
    String addProfileEntryDialog_no_parameter_caption();

    @Default(lngs = { "en" }, values = { "No parameters to set" })
    String addProfileEntryDialog_no_parameter();

    @Default(lngs = { "en" }, values = { "Enable" })
    String profileTable_column_enable();

    @Default(lngs = { "en" }, values = { "Profile Name" })
    String profileTable_column_name();

    @Default(lngs = { "en" }, values = { "Action" })
    String profileTable_column_action();

    @Default(lngs = { "en" }, values = { "Minimum Chunks" })
    String profileTable_column_min_chunks();

    @Default(lngs = { "en" }, values = { "Maximum Chunks" })
    String profileTable_column_max_chunks();

    @Default(lngs = { "en" }, values = { "Minimum Downloads" })
    String profileTable_column_min_downloads();

    @Default(lngs = { "en" }, values = { "Maximum Downloads" })
    String profileTable_column_max_downloads();

    @Default(lngs = { "en" }, values = { "Min Bandwidth (kbps)" })
    String profileTable_column_min_bandwidth();

    @Default(lngs = { "en" }, values = { "Max Bandwidth (kbps)" })
    String profileTable_column_max_bandwidth();

    @Default(lngs = { "en" }, values = { "Really remove %s1 bandwidth profile(s)?" })
    String entry_remove_action_title(int size);

    @Default(lngs = { "en" }, values = { "Really remove %s1" })
    String entry_remove_action_msg(String string);

    @Default(lngs = { "en" }, values = { "Create new bandwidth profile" })
    String addProfileEntryDialog_title();

    @Default(lngs = { "en" }, values = { "Edit bandwidth profile" })
    String addProfileEntryDialog_title_edit();

    @Default(lngs = { "en" }, values = { "Bandwidth Profile" })
    String addProfileEntryDialog_defaultProfileName();

    @Default(lngs = { "en" }, values = { "Parameters" })
    String addProfileEntryDialog_header_parameters();

    @Default(lngs = { "en" }, values = { "Min Downloads" })
    String addProfileEntryDialog_minDownloads();

    @Default(lngs = { "en" }, values = { "Max Downloads" })
    String addProfileEntryDialog_maxDownloads();

    @Default(lngs = { "en" }, values = { "Min Chunks" })
    String addProfileEntryDialog_minChunks();

    @Default(lngs = { "en" }, values = { "Max Chunks" })
    String addProfileEntryDialog_maxChunks();

    @Default(lngs = { "en" }, values = { "Min Bandwidth" })
    String addProfileEntryDialog_minBandwidth();

    @Default(lngs = { "en" }, values = { "Max Bandwidth" })
    String addProfileEntryDialog_maxBandwidth();

    @Default(lngs = { "en" }, values = { "Autodownloader" })
    String title();

    @Default(lngs = { "en" }, values = { "Create and edit bandwith profiles" })
    String description();

    @Default(lngs = { "en" }, values = { "Copy" })
    String lit_copy();

    @Default(lngs = { "en" }, values = { "Set Chunks" })
    String action_setChunks();

    @Default(lngs = { "en" }, values = { "Change bandwidth profile number of chunks" })
    String addProfileEntryDialog_chunks();

    @Default(lngs = { "en" }, values = { "Change number of downloads" })
    String action_setConnections();

    @Default(lngs = { "en" }, values = { "Change downloads" })
    String addScheduleEntryDialog_downloads();
}
