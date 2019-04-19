package org.jdownloader.extensions.autodownloader.gui.actions;

import java.awt.event.ActionEvent;
import java.util.List;

import org.jdownloader.actions.AppAction;
import org.jdownloader.extensions.autodownloader.gui.AutodownloaderTable;
import org.jdownloader.extensions.autodownloader.model.ProfileEntry;
import org.jdownloader.extensions.autodownloader.model.ProfileEntryStorable;
import org.jdownloader.extensions.autodownloader.translate.T;
import org.jdownloader.gui.IconKey;

public class CopyAction extends AppAction {
    private AutodownloaderTable table;
    private List<ProfileEntry>  selection = null;

    public CopyAction(AutodownloaderTable table) {
        this.table = table;
        setName(T.T.lit_copy());
        setIconKey(IconKey.ICON_COPY);
    }

    public CopyAction(List<ProfileEntry> selection2) {
        this.selection = selection2;
        setName(T.T.lit_copy());
        setIconKey(IconKey.ICON_COPY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isEnabled()) {
            return;
        }
        final List<ProfileEntry> finalSelection;
        if (selection != null) {
            finalSelection = selection;
        } else if (table != null) {
            finalSelection = table.getModel().getSelectedObjects();
        } else {
            finalSelection = null;
        }
        if (finalSelection != null && finalSelection.size() > 0) {
            for (ProfileEntry profile : finalSelection) {
                table.getExtension().addProfileEntry(copy(profile));
            }
        }
    }

    private ProfileEntry copy(ProfileEntry profile) {
        ProfileEntryStorable copyStorable = new ProfileEntryStorable();
        copyStorable.setEnabled(profile.isEnabled());
        copyStorable.setName(profile.getName());
        copyStorable.setMinNumChunks(profile.getMinNumChunks());
        copyStorable.setMaxNumChunks(profile.getMaxNumChunks());
        copyStorable.setMinNumDownloads(profile.getMinNumDownloads());
        copyStorable.setMaxNumDownloads(profile.getMaxNumDownloads());
        copyStorable.setMinBandwidth(profile.getMinBandwidth());
        copyStorable.setMaxBandwidth(profile.getMaxBandwidth());
        // copyStorable.setActionId(profile.getAction().getActionID());
        // copyStorable.setActionConfig(profile.getStorable().getActionConfig());
        try {
            ProfileEntry copy = new ProfileEntry(copyStorable);
            return copy;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isEnabled() {
        if (this.table != null) {
            return super.isEnabled();
        }
        return (selection != null && selection.size() > 0);
    }
}
