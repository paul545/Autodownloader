package org.jdownloader.extensions.autodownloader.gui.actions;

import java.awt.event.ActionEvent;
import java.util.List;

import org.appwork.utils.swing.dialog.Dialog;
import org.appwork.utils.swing.dialog.DialogNoAnswerException;
import org.jdownloader.actions.AppAction;
import org.jdownloader.extensions.autodownloader.gui.AutodownloaderTable;
import org.jdownloader.extensions.autodownloader.model.ProfileEntry;
import org.jdownloader.extensions.autodownloader.translate.T;
import org.jdownloader.gui.IconKey;
import org.jdownloader.gui.translate._GUI;

public class RemoveAction extends AppAction {
    /**
     *
     */
    private static final long   serialVersionUID = 2L;
    private AutodownloaderTable table;
    private boolean             force            = false;
    private List<ProfileEntry>  selection        = null;

    public RemoveAction(AutodownloaderTable table) {
        this.table = table;
        setName(_GUI.T.literally_remove());
        setIconKey(IconKey.ICON_REMOVE);
    }

    public RemoveAction(List<ProfileEntry> selection2, boolean force) {
        this.force = force;
        this.selection = selection2;
        setName(_GUI.T.literally_remove());
        setIconKey(IconKey.ICON_REMOVE);
    }

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
            StringBuilder sb = new StringBuilder();
            for (ProfileEntry rule : finalSelection) {
                if (sb.length() > 0) {
                    sb.append("\r\n");
                }
                sb.append("\"");
                sb.append(rule.getName());
                sb.append("\"");
            }
            try {
                if (!force) {
                    Dialog.getInstance().showConfirmDialog(Dialog.STYLE_LARGE, T.T.entry_remove_action_title(finalSelection.size()), T.T.entry_remove_action_msg(finalSelection.size() <= 1 ? sb.toString() : "\r\n" + sb.toString()));
                }
                for (ProfileEntry entry : finalSelection) {
                    table.getExtension().removeProfileEntry(entry);
                }
            } catch (DialogNoAnswerException e1) {
                e1.printStackTrace();
            }
        }
    }

    public boolean isEnabled() {
        if (this.table != null) {
            return super.isEnabled();
        }
        return (selection != null && selection.size() > 0);
    }
}
