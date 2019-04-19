package org.jdownloader.extensions.autodownloader;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.appwork.swing.MigPanel;
import org.appwork.swing.components.ExtButton;
import org.appwork.swing.exttable.utils.MinimumSelectionObserver;
import org.appwork.utils.swing.SwingUtils;
import org.jdownloader.extensions.ExtensionConfigPanel;
import org.jdownloader.extensions.autodownloader.gui.AutodownloaderTable;
import org.jdownloader.extensions.autodownloader.gui.ProfileTableModel;
import org.jdownloader.extensions.autodownloader.gui.actions.CopyAction;
import org.jdownloader.extensions.autodownloader.gui.actions.EditAction;
import org.jdownloader.extensions.autodownloader.gui.actions.NewAction;
import org.jdownloader.extensions.autodownloader.gui.actions.RemoveAction;
import org.jdownloader.gui.IconKey;
import org.jdownloader.images.AbstractIcon;
import org.jdownloader.updatev2.gui.LAFOptions;

import jd.gui.swing.components.linkbutton.JLink;

public class AutodownloaderConfigPanel extends ExtensionConfigPanel<AutodownloaderExtension> {
    /**
     *
     * @param plg
     */
    private static final long   serialVersionUID = 1L;
    private AutodownloaderTable table;
    private MigPanel            myContainer;
    private ProfileTableModel   tableModel;

    public AutodownloaderConfigPanel(AutodownloaderExtension extension) {
        super(extension);
        myContainer = new MigPanel("ins 0, wrap 1", "[grow]", "[][]");
        SwingUtils.setOpaque(myContainer, false);
        add(myContainer, "pushx,pushy,growx,growy,spanx,spany");
        initPanel();
    }

    @Override
    public void save() {
    }

    @Override
    public void updateContents() {
    }

    public ProfileTableModel getTableModel() {
        return tableModel;
    }

    private void initPanel() {
        myContainer.removeAll();
        myContainer.setLayout("ins 0, wrap 1", "[grow]", "[][]");
        JLink lnk;
        try {
            lnk = new JLink("Extension under development. Click for Github.", new AbstractIcon(IconKey.ICON_URL, 16), new URL("https://github.com/paul545/Autodownloader"));
        } catch (MalformedURLException e1) {
            lnk = null;
        }
        myContainer.add(lnk);
        lnk.setForeground(LAFOptions.getInstance().getColorForErrorForeground());
        table = new AutodownloaderTable(extension, tableModel = new ProfileTableModel(this.extension));
        myContainer.add(new JScrollPane(table), "grow");
        MigPanel bottomMenu = new MigPanel("ins 0", "[]", "[]");
        bottomMenu.setLayout("ins 0", "[][][][fill]", "[]");
        bottomMenu.setOpaque(false);
        myContainer.add(bottomMenu);
        NewAction na;
        bottomMenu.add(new ExtButton(na = new NewAction(table)), "sg 1, height 26!");
        na.putValue(AbstractAction.SMALL_ICON, new AbstractIcon(IconKey.ICON_ADD, 20));
        RemoveAction ra;
        bottomMenu.add(new ExtButton(ra = new RemoveAction(table)), "sg 1, height 26!");
        table.getSelectionModel().addListSelectionListener(new MinimumSelectionObserver(table, ra, 1));
        CopyAction ca;
        bottomMenu.add(new ExtButton(ca = new CopyAction(table)), "sg 1, height 26!");
        table.getSelectionModel().addListSelectionListener(new MinimumSelectionObserver(table, ca, 1));
        final EditAction ea;
        bottomMenu.add(new ExtButton(ea = new EditAction(table)), "sg 1, height 26!");
        ea.setEnabled(table.getSelectedRowCount() == 1);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ea.setEnabled(table.getSelectedRowCount() == 1);
            }
        });
        tableModel.updateDataModel();
    }

    public void updateLayout() {
        initPanel();
    }
}
