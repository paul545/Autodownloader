package org.jdownloader.extensions.autodownloader.gui;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.appwork.swing.exttable.ExtTableHeaderRenderer;
import org.appwork.swing.exttable.ExtTableModel;
import org.appwork.swing.exttable.columns.ExtCheckColumn;
import org.appwork.swing.exttable.columns.ExtTextColumn;
import org.jdownloader.extensions.autodownloader.AutodownloaderExtension;
import org.jdownloader.extensions.autodownloader.model.ProfileEntry;
import org.jdownloader.extensions.autodownloader.translate.T;
import org.jdownloader.gui.IconKey;
import org.jdownloader.images.NewTheme;

public class ProfileTableModel extends ExtTableModel<ProfileEntry> {
    private final AutodownloaderExtension extension;

    public ProfileTableModel(AutodownloaderExtension extension) {
        super("ProfileTableModel");
        this.extension = extension;
        updateDataModel();
    }

    public void updateDataModel() {
        _fireTableStructureChanged(new ArrayList<ProfileEntry>(extension.getProfileEntries()), true);
    }

    /**
    *
    */
    private static final long serialVersionUID = 7555957789201764308L;

    @Override
    protected void initColumns() {
        this.addColumn(new ExtCheckColumn<ProfileEntry>(T.T.profileTable_column_enable()) {
            private static final long serialVersionUID = 15;

            public ExtTableHeaderRenderer getHeaderRenderer(final JTableHeader jTableHeader) {
                final ExtTableHeaderRenderer ret = new ExtTableHeaderRenderer(this, jTableHeader) {
                    private final Icon        ok               = NewTheme.I().getIcon(IconKey.ICON_OK, 14);
                    private static final long serialVersionUID = 16L;

                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                        setIcon(ok);
                        setHorizontalAlignment(CENTER);
                        setText(null);
                        return this;
                    }
                };
                return ret;
            }

            @Override
            public int getMaxWidth() {
                return 30;
            }

            @Override
            public boolean isHidable() {
                return false;
            }

            @Override
            public boolean isEditable(ProfileEntry obj) {
                return true;
            }

            @Override
            protected boolean getBooleanValue(ProfileEntry value) {
                return value.isEnabled();
            }

            @Override
            protected void setBooleanValue(boolean value, ProfileEntry object) {
                object.setEnabled(value);
            }
        });
        this.addColumn(new ExtTextColumn<ProfileEntry>(T.T.profileTable_column_name()) {
            @Override
            public String getStringValue(ProfileEntry value) {
                return value.getName();
            }

            @Override
            protected void setStringValue(String value, ProfileEntry object) {
                object.setName(value);
            }

            @Override
            public boolean isEditable(ProfileEntry obj) {
                return true;
            }
        });
        this.addColumn(new ExtTextColumn<ProfileEntry>(T.T.profileTable_column_min_downloads()) {
            @Override
            public String getStringValue(ProfileEntry value) {
                return String.valueOf(value.getMinNumDownloads());
            }
        });
        this.addColumn(new ExtTextColumn<ProfileEntry>(T.T.profileTable_column_max_downloads()) {
            @Override
            public String getStringValue(ProfileEntry value) {
                return String.valueOf(value.getMaxNumDownloads());
            }
        });
        this.addColumn(new ExtTextColumn<ProfileEntry>(T.T.profileTable_column_min_chunks()) {
            @Override
            public String getStringValue(ProfileEntry value) {
                return String.valueOf(value.getMinNumChunks());
            }
        });
        this.addColumn(new ExtTextColumn<ProfileEntry>(T.T.profileTable_column_max_chunks()) {
            @Override
            public String getStringValue(ProfileEntry value) {
                return String.valueOf(value.getMaxNumChunks());
            }
        });
        this.addColumn(new ExtTextColumn<ProfileEntry>(T.T.profileTable_column_min_bandwidth()) {
            @Override
            public String getStringValue(ProfileEntry value) {
                return String.valueOf(value.getMinBandwidth());
            }
        });
        this.addColumn(new ExtTextColumn<ProfileEntry>(T.T.profileTable_column_max_bandwidth()) {
            @Override
            public String getStringValue(ProfileEntry value) {
                return String.valueOf(value.getMaxBandwidth());
            }
        });
    }
}
