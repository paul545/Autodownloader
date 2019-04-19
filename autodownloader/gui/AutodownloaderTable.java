package org.jdownloader.extensions.autodownloader.gui;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPopupMenu;

import org.appwork.swing.exttable.ExtColumn;
import org.appwork.swing.exttable.ExtTableModel;
import org.jdownloader.extensions.autodownloader.AutodownloaderExtension;
import org.jdownloader.extensions.autodownloader.gui.actions.EditAction;
import org.jdownloader.extensions.autodownloader.gui.actions.NewAction;
import org.jdownloader.extensions.autodownloader.gui.actions.RemoveAction;
import org.jdownloader.extensions.autodownloader.model.ProfileEntry;

import jd.gui.swing.jdgui.BasicJDTable;

public class AutodownloaderTable extends BasicJDTable<ProfileEntry> {
    private final AutodownloaderExtension extension;

    public AutodownloaderExtension getExtension() {
        return extension;
    }

    public AutodownloaderTable(AutodownloaderExtension extension, ExtTableModel<ProfileEntry> tableModel) {
        super(tableModel);
        this.extension = extension;
    }

    /**
     *
     */
    private static final long serialVersionUID = -555555L;

    @Override
    protected boolean onShortcutDelete(List<ProfileEntry> selectedObjects, KeyEvent event, boolean direct) {
        new RemoveAction(this).actionPerformed(null);
        return true;
    }

    @Override
    protected boolean onDoubleClick(MouseEvent e, ProfileEntry obj) {
        new EditAction(this).actionPerformed(null);
        return true;
    }

    @Override
    protected JPopupMenu onContextMenu(JPopupMenu popup, ProfileEntry contextObject, List<ProfileEntry> selection, ExtColumn<ProfileEntry> column, MouseEvent mouseEvent) {
        if (popup != null) {
            popup.add(new NewAction(this));
            if (selection != null && selection.size() >= 1) {
                popup.add(new RemoveAction(this));
            }
            if (selection != null && selection.size() == 1) {
                popup.add(new EditAction(this));
            }
        }
        return popup;
    }
}