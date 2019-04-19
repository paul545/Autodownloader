package org.jdownloader.extensions.autodownloader.gui.actions;

import java.awt.event.ActionEvent;
import java.util.List;

import org.appwork.utils.swing.dialog.Dialog;
import org.appwork.utils.swing.dialog.DialogNoAnswerException;
import org.jdownloader.extensions.autodownloader.gui.AddProfileEntryDialog;
import org.jdownloader.extensions.autodownloader.gui.AutodownloaderTable;
import org.jdownloader.extensions.autodownloader.model.ProfileEntry;
import org.jdownloader.gui.IconKey;
import org.jdownloader.gui.translate._GUI;
import org.jdownloader.gui.views.components.AbstractAddAction;

public class EditAction extends AbstractAddAction {
    /**
     *
     */
    private static final long         serialVersionUID = 4206686293819288546L;
    private final AutodownloaderTable table;

    public EditAction(AutodownloaderTable table) {
        super();
        this.table = table;
        setName(_GUI.T.literally_edit());
        setIconKey(IconKey.ICON_EDIT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<ProfileEntry> selected = table.getModel().getSelectedObjects();
        if (selected.size() != 1) {
            return;
        }
        ProfileEntry toBeEdited = selected.get(0);
        long old = toBeEdited.getID();
        try {
            toBeEdited = new ProfileEntry(toBeEdited.getStorable());
            final AddProfileEntryDialog dialog = new AddProfileEntryDialog(toBeEdited);
            ProfileEntry entry;
            entry = Dialog.getInstance().showDialog(dialog);
            if (entry != null) {
                table.getExtension().replaceProfileEntry(old, entry);
            }
        } catch (DialogNoAnswerException e1) {
            e1.printStackTrace();
        } catch (Exception e1) {
            org.appwork.utils.logging2.extmanager.LoggerFactory.getDefaultLogger().log(e1);
        }
    }
}
