package org.jdownloader.extensions.autodownloader.gui.actions;

import java.awt.event.ActionEvent;

import org.appwork.utils.swing.dialog.Dialog;
import org.appwork.utils.swing.dialog.DialogNoAnswerException;
import org.jdownloader.extensions.autodownloader.gui.AddProfileEntryDialog;
import org.jdownloader.extensions.autodownloader.gui.AutodownloaderTable;
import org.jdownloader.extensions.autodownloader.model.ProfileEntry;
import org.jdownloader.gui.views.components.AbstractAddAction;

public class NewAction extends AbstractAddAction {
    /**
     *
     */
    private static final long         serialVersionUID = -8412992614620250165L;
    private final AutodownloaderTable table;

    public NewAction(AutodownloaderTable table) {
        super();
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final AddProfileEntryDialog dialog = new AddProfileEntryDialog();
        try {
            ProfileEntry entry = Dialog.getInstance().showDialog(dialog);
            if (entry != null) {
                table.getExtension().addProfileEntry(entry);
            }
        } catch (DialogNoAnswerException e2) {
            e2.printStackTrace();
        }
    }
}
