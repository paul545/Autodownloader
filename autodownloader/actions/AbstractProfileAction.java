package org.jdownloader.extensions.autodownloader.actions;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import org.appwork.storage.JSonStorage;
import org.appwork.swing.MigPanel;
import org.appwork.utils.logging2.LogInterface;

public abstract class AbstractProfileAction<T extends IProfileActionConfig> {
    private final T                                   config;
    protected final LinkedHashMap<JComponent, String> panel = new LinkedHashMap<JComponent, String>();

    public AbstractProfileAction(String configJson) {
        T config = null;
        try {
            Class<? extends IProfileActionConfig> configClass = getConfigClass();
            IProfileActionConfig defaultConfig = configClass.newInstance();
            try {
                if (configJson != null) {
                    config = (T) JSonStorage.restoreFromString(configJson, configClass);
                }
            } catch (final Throwable e) {
                e.printStackTrace();
            }
            if (config == null) {
                config = (T) defaultConfig;
            }
        } catch (final Throwable e) {
            e.printStackTrace();
        }
        this.config = config;
    }

    @SuppressWarnings("unchecked")
    public Class<? extends IProfileActionConfig> getConfigClass() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            return (Class<? extends IProfileActionConfig>) ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            throw new RuntimeException("Bad extension definition. Please add Generic ConfigCLass: class " + getClass().getSimpleName() + " extends AbstractExtension<" + getClass().getSimpleName() + "Config>) {... with 'public interface " + getClass().getSimpleName() + "Config extends ExtensionConfigInterface{...");
        }
    }

    public String getActionID() {
        final ProfileActionIDAnnotation actionID = getClass().getAnnotation(ProfileActionIDAnnotation.class);
        return actionID != null ? actionID.value() : null;
    }

    public abstract String getReadableName();

    public final void drawOnPanel(MigPanel realPanel) {
        if (panel.size() == 0) {
            createPanel();
        }
        for (JComponent comp : panel.keySet()) {
            realPanel.add(comp, panel.get(comp) + " hidemode 3");
        }
    }

    public void setVisible(boolean aFlag) {
        for (JComponent component : panel.keySet()) {
            component.setVisible(aFlag);
        }
    }

    /**
     * Adds all JComponents to KeySet panel, with MigLayout constraints as value. For instance, to add a Label with full width, write:
     * panel.put(new JLabel("Caption"),"spanx,");
     */
    protected void createPanel() {
        JLabel lblCaption = new JLabel(org.jdownloader.extensions.autodownloader.translate.T.T.addProfileEntryDialog_no_parameter_caption() + ":");
        lblCaption.setEnabled(false);
        panel.put(lblCaption, "gapleft 10,");
        JLabel lbl = new JLabel(org.jdownloader.extensions.autodownloader.translate.T.T.addProfileEntryDialog_no_parameter());
        lbl.setEnabled(false);
        panel.put(lbl, "");
    }

    public abstract void execute(LogInterface logger);

    public T getConfig() {
        return config;
    }

    public String getReadableParameter() {
        // default: no parameter
        return "";
    }

    public Icon getParameterIcon() {
        // default : no icon
        return null;
    }
}
