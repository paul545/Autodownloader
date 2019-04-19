package org.jdownloader.extensions.autodownloader.gui;

import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.appwork.swing.MigPanel;
import org.appwork.swing.components.ExtTextField;
import org.appwork.uio.CloseReason;
import org.appwork.utils.swing.SwingUtils;
import org.appwork.utils.swing.dialog.AbstractDialog;
import org.jdownloader.extensions.autodownloader.model.ProfileEntry;
import org.jdownloader.extensions.autodownloader.model.ProfileEntryStorable;
import org.jdownloader.extensions.autodownloader.translate.T;
import org.jdownloader.gui.translate._GUI;

import jd.gui.UserIO;

public class AddProfileEntryDialog extends AbstractDialog<ProfileEntry> {
    private JPanel             content;
    private ExtTextField       profileName;
    private ProfileEntry       editEntry = null;
    private JComboBox<Integer> minDownloadsBox;
    private JComboBox<Integer> maxDownloadsBox;
    private JComboBox<Integer> minChunksBox;
    private JComboBox<Integer> maxChunksBox;
    private JSpinner           minBandwidthSpinner;
    private JSpinner           maxBandwidthSpinner;
    private final Integer[]    nums      = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };

    public AddProfileEntryDialog(int flag, String title, Icon icon, String okOption, String cancelOption) {
        super(flag, title, icon, okOption, cancelOption);
    }

    public AddProfileEntryDialog() {
        super(UserIO.NO_ICON, T.T.addProfileEntryDialog_title(), null, _GUI.T.lit_save(), null);
    }

    public AddProfileEntryDialog(ProfileEntry entry) {
        super(UserIO.NO_ICON, T.T.addProfileEntryDialog_title_edit(), null, _GUI.T.lit_save(), null);
        this.editEntry = entry;
    }

    @Override
    protected ProfileEntry createReturnValue() {
        if (!getCloseReason().equals(CloseReason.OK)) {
            return null;
        }
        ProfileEntryStorable actionStorable = new ProfileEntryStorable();
        actionStorable.setEnabled(true);
        actionStorable.setName(profileName.getText());
        actionStorable.setMinNumChunks((int) minChunksBox.getSelectedItem());
        actionStorable.setMaxNumChunks((int) maxChunksBox.getSelectedItem());
        actionStorable.setMinNumDownloads((int) minDownloadsBox.getSelectedItem());
        actionStorable.setMaxNumDownloads((int) maxDownloadsBox.getSelectedItem());
        actionStorable.setMinBandwidth((int) minBandwidthSpinner.getValue());
        actionStorable.setMaxBandwidth((int) maxBandwidthSpinner.getValue());
        // actionStorable.setMinNumChunks(minDownloadsBox.get);
        try {
            return new ProfileEntry(actionStorable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getOrDefault(HashMap<JComponent, String> constraints, JComponent component, String defaultValue) {
        final String ret = constraints.get(component);
        if (ret == null) {
            return defaultValue;
        } else {
            return ret;
        }
    }

    @Override
    public JComponent layoutDialogContent() {
        MigPanel migPanel = new MigPanel("ins 0 0 5 0, wrap 2", "[95::][grow, fill]", "[sg name][sg header][sg repeat][sg parameter][sg parameter][sg header][sg action][sg parameter2, 26]");
        migPanel.setOpaque(false);
        migPanel.add(new JLabel(T.T.profileTable_column_name() + ":"));
        profileName = new ExtTextField();
        profileName.setText(editEntry != null ? editEntry.getName() : T.T.addProfileEntryDialog_defaultProfileName());
        migPanel.add(profileName, "");
        minDownloadsBox = new JComboBox<Integer>(nums);
        maxDownloadsBox = new JComboBox<Integer>(nums);
        minChunksBox = new JComboBox<Integer>(nums);
        maxChunksBox = new JComboBox<Integer>(nums);
        //
        if (editEntry != null) {
            minDownloadsBox.setSelectedItem(editEntry.getMinNumDownloads());
            maxDownloadsBox.setSelectedItem(editEntry.getMaxNumDownloads());
            minChunksBox.setSelectedItem(editEntry.getMinNumChunks());
            maxChunksBox.setSelectedItem(editEntry.getMaxNumChunks());
        }
        minBandwidthSpinner = new JSpinner(new SpinnerNumberModel(editEntry != null ? editEntry.getMinBandwidth() : 100, 1, 1000000, 1));
        maxBandwidthSpinner = new JSpinner(new SpinnerNumberModel(editEntry != null ? editEntry.getMaxBandwidth() : 100, 1, 1000000, 1));
        //
        migPanel.add(header(T.T.addProfileEntryDialog_header_parameters()), "spanx, growx, pushx, newline 15");
        migPanel.add(new JLabel(T.T.addProfileEntryDialog_minDownloads() + ":"), "gapleft 10");
        migPanel.add(minDownloadsBox, "");
        migPanel.add(new JLabel(T.T.addProfileEntryDialog_maxDownloads() + ":"), "gapleft 10");
        migPanel.add(maxDownloadsBox);
        migPanel.add(new JLabel(T.T.addProfileEntryDialog_minChunks() + ":"), "gapleft 10");
        migPanel.add(minChunksBox);
        migPanel.add(new JLabel(T.T.addProfileEntryDialog_maxChunks() + ":"), "gapleft 10");
        migPanel.add(maxChunksBox);
        migPanel.add(new JLabel(T.T.addProfileEntryDialog_minBandwidth() + ":"), "gapleft 10");
        migPanel.add(minBandwidthSpinner);
        migPanel.add(new JLabel(T.T.addProfileEntryDialog_maxBandwidth() + ":"), "gapleft 10");
        migPanel.add(maxBandwidthSpinner);
        //
        content = migPanel;
        updatePanel();
        loadEntry(editEntry);
        return content;
    }

    private void loadEntry(ProfileEntry editEntry) {
        if (editEntry == null) {
            profileName.setText(T.T.addProfileEntryDialog_defaultProfileName());
        } else {
            profileName.setText(editEntry.getName());
        }
    }

    @Override
    protected int getPreferredWidth() {
        if (content == null) {
            return 455;
        } else {
            return Math.max((int) content.getMinimumSize().getWidth() + 50, 455);
        }
    }

    protected void updatePanel() {
        if (content != null) {
            getDialog().pack();
        }
    }

    @Override
    protected void packed() {
        super.packed();
    }

    private JComponent header(String caption) {
        JLabel ret = SwingUtils.toBold(new JLabel(caption));
        ret.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, ret.getForeground()));
        return ret;
    }
}
