package org.jdownloader.extensions.autodownloader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.appwork.shutdown.ShutdownController;
import org.appwork.shutdown.ShutdownEvent;
import org.appwork.shutdown.ShutdownRequest;
import org.appwork.storage.config.ValidationException;
import org.appwork.storage.config.events.GenericConfigEventListener;
import org.appwork.storage.config.handler.KeyHandler;
import org.appwork.utils.Application;
import org.appwork.utils.event.queue.QueueAction;
import org.appwork.utils.swing.EDTRunner;
import org.jdownloader.controlling.contextmenu.ContextMenuManager;
import org.jdownloader.controlling.contextmenu.MenuContainerRoot;
import org.jdownloader.controlling.contextmenu.MenuExtenderHandler;
import org.jdownloader.controlling.contextmenu.MenuItemData;
import org.jdownloader.extensions.AbstractExtension;
import org.jdownloader.extensions.StartException;
import org.jdownloader.extensions.StopException;
import org.jdownloader.extensions.autodownloader.model.ProfileEntry;
import org.jdownloader.extensions.autodownloader.model.ProfileEntryStorable;
import org.jdownloader.extensions.autodownloader.translate.AutodownloaderTranslation;
import org.jdownloader.gui.IconKey;
import org.jdownloader.gui.mainmenu.MenuManagerMainmenu;
import org.jdownloader.gui.toolbar.MenuManagerMainToolbar;

import jd.controlling.TaskQueue;
import jd.plugins.AddonPanel;

public class AutodownloaderExtension extends AbstractExtension<AutodownloaderConfig, AutodownloaderTranslation> implements MenuExtenderHandler, Runnable, GenericConfigEventListener<Object> {
    private AutodownloaderConfigPanel          configPanel;
    private ScheduledExecutorService           scheduler;
    private final Object                       lock           = new Object();
    private CopyOnWriteArrayList<ProfileEntry> profileEntries = new CopyOnWriteArrayList<ProfileEntry>();
    private final ShutdownEvent                shutDownEvent  = new ShutdownEvent() {
                                                                  @Override
                                                                  public void onShutdown(ShutdownRequest shutdownRequest) {
                                                                      CFG_AUTODOWNLOADER.ENTRY_LIST.getEventSender().removeListener(AutodownloaderExtension.this);
                                                                      saveProfileEntries(false);
                                                                  }
                                                              };

    @Override
    public boolean isHeadlessRunnable() {
        return true;
    }

    public void saveProfileEntries(final boolean async) {
        if (async) {
            TaskQueue.getQueue().addAsynch(new QueueAction<Void, RuntimeException>() {
                @Override
                protected Void run() throws RuntimeException {
                    saveProfileEntries(false);
                    return null;
                }
            });
        } else {
            final List<ProfileEntryStorable> profileStorables = new ArrayList<ProfileEntryStorable>();
            for (ProfileEntry profile : getProfileEntries()) {
                profileStorables.add(profile.getStorable());
            }
            CFG_AUTODOWNLOADER.CFG.setEntryList(profileStorables);
        }
    }

    public List<ProfileEntry> getProfileEntries() {
        return profileEntries;
    }

    /**
     * private boolean needsRun(ProfileEntry plan) { if (!plan.isEnabled()) { return false; }
     * org.jdownloader.settings.staticreferences.CFG_GENERAL.AUTO_MAX_DOWNLOADS_SPEED_LIMIT.setValue(plan.getMinBandwidth()); }
     **/
    @Override
    public void run() {
        for (ProfileEntry entry : getProfileEntries()) {
            getLogger().info("Available profile:  " + entry.getName() + entry.isEnabled());
            if (entry.isEnabled()) {
                getLogger().info("Run action: " + entry.getAction().getReadableName());
                try {
                    // entry.getAction().execute(getLogger());
                    org.jdownloader.settings.staticreferences.CFG_GENERAL.AUTO_MAX_DOWNLOADS_SPEED_LIMIT.setValue(entry.getMinBandwidth() * 1000);
                    org.jdownloader.settings.staticreferences.CFG_GENERAL.AUTO_MAX_DOWNLOADS_SPEED_LIMIT_MAX_DOWNLOADS.setValue(entry.getMaxNumDownloads());
                    org.jdownloader.settings.staticreferences.CFG_GENERAL.MAX_SIMULTANE_DOWNLOADS.setValue(entry.getMinNumDownloads());
                    org.jdownloader.settings.staticreferences.CFG_GENERAL.MAX_CHUNKS_PER_FILE.setValue(entry.getMaxNumChunks());
                } catch (final Throwable e) {
                    getLogger().log(e);
                }
            }
        }
    }

    @Override
    protected void start() throws StartException {
        if (!Application.isHeadless()) {
            new EDTRunner() {
                @Override
                protected void runInEDT() {
                    getConfigPanel().updateLayout();
                }
            };
            MenuManagerMainToolbar.getInstance().registerExtender(this);
            MenuManagerMainmenu.getInstance().registerExtender(this);
        }
        loadProfileEntries();
        updateTable();
        ShutdownController.getInstance().addShutdownEvent(shutDownEvent);
        getLogger().info("Start AutodownloaderThreadTimer");
        synchronized (lock) {
            scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(this, 60 - Calendar.getInstance().get(Calendar.SECOND), 60l, TimeUnit.SECONDS);
        }
        CFG_AUTODOWNLOADER.ENTRY_LIST.getEventSender().addListener(this, true);
    }

    @Override
    protected void stop() throws StopException {
        CFG_AUTODOWNLOADER.ENTRY_LIST.getEventSender().removeListener(this);
        saveProfileEntries(false);
        if (!Application.isHeadless()) {
            MenuManagerMainToolbar.getInstance().unregisterExtender(this);
            MenuManagerMainmenu.getInstance().unregisterExtender(this);
        }
        ShutdownController.getInstance().removeShutdownEvent(shutDownEvent);
        synchronized (lock) {
            if (scheduler != null) {
                scheduler.shutdown();
                scheduler = null;
            }
        }
    }

    @Override
    public void onConfigValidatorError(KeyHandler<Object> keyHandler, Object invalidValue, ValidationException validateException) {
    }

    @Override
    public void onConfigValueModified(KeyHandler<Object> keyHandler, Object newValue) {
        if (keyHandler == CFG_AUTODOWNLOADER.ENTRY_LIST) {
            loadProfileEntries();
            updateTable();
        }
    }

    @Override
    public AutodownloaderConfigPanel getConfigPanel() {
        return configPanel;
    }

    @Override
    public boolean hasConfigPanel() {
        return true;
    }

    public AutodownloaderExtension() throws StartException {
        setTitle(T.title());
    }

    @Override
    public String getIconKey() {
        return IconKey.ICON_DOWNLOAD;
    }

    public void removeProfileEntry(ProfileEntry entry) {
        if (entry != null && profileEntries.remove(entry)) {
            saveProfileEntries(true);
            updateTable();
        }
    }

    private void updateTable() {
        if (!Application.isHeadless()) {
            final AutodownloaderConfigPanel panel = getConfigPanel();
            if (panel != null) {
                panel.getTableModel().updateDataModel();
                loadProfileEntries();
            }
        }
    }

    public void addProfileEntry(ProfileEntry entry) {
        if (entry != null && profileEntries.addIfAbsent(entry)) {
            saveProfileEntries(true);
            updateTable();
        }
    }

    public void replaceProfileEntry(long oldID, ProfileEntry newEntry) {
        if (newEntry != null) {
            int pos = -1;
            for (int i = 0; i < profileEntries.size(); i++) {
                if (oldID == profileEntries.get(i).getID()) {
                    pos = i;
                    break;
                }
            }
            if (pos > -1) {
                profileEntries.set(pos, newEntry);
                saveProfileEntries(true);
                updateTable();
            }
        }
    }

    private void loadProfileEntries() {
        final List<ProfileEntryStorable> profileStorables = CFG_AUTODOWNLOADER.CFG.getEntryList();
        final CopyOnWriteArrayList<ProfileEntry> profileEntries = new CopyOnWriteArrayList<ProfileEntry>();
        if (profileStorables != null) {
            for (int i = 0; i < profileStorables.size(); i++) {
                try {
                    ProfileEntry entry = new ProfileEntry(profileStorables.get(i));
                    profileEntries.add(entry);
                    getLogger().info("Available profile:  " + entry.getName() + " Enabled: " + entry.isEnabled());
                    if (entry.isEnabled()) {
                        org.jdownloader.settings.staticreferences.CFG_GENERAL.AUTO_MAX_DOWNLOADS_SPEED_LIMIT.setValue(entry.getMinBandwidth() * 1000);
                        org.jdownloader.settings.staticreferences.CFG_GENERAL.AUTO_MAX_DOWNLOADS_SPEED_LIMIT_MAX_DOWNLOADS.setValue(entry.getMaxNumDownloads());
                        org.jdownloader.settings.staticreferences.CFG_GENERAL.MAX_SIMULTANE_DOWNLOADS.setValue(entry.getMinNumDownloads());
                        org.jdownloader.settings.staticreferences.CFG_GENERAL.MAX_CHUNKS_PER_FILE.setValue(entry.getMaxNumChunks());
                        org.jdownloader.settings.staticreferences.CFG_GENERAL.DOWNLOAD_SPEED_LIMIT.setValue(entry.getMaxBandwidth() * 1000);
                    }
                } catch (Exception e) {
                    getLogger().log(e);
                }
            }
        }
        this.profileEntries = profileEntries;
    }

    @Override
    public String getDescription() {
        return T.description();
    }

    @Override
    public AddonPanel<AutodownloaderExtension> getGUI() {
        return null;
    }

    @Override
    protected void initExtension() throws StartException {
        if (!Application.isHeadless()) {
            configPanel = new AutodownloaderConfigPanel(this);
        }
    }

    @Override
    public boolean isQuickToggleEnabled() {
        return true;
    }

    @Override
    public MenuItemData updateMenuModel(ContextMenuManager manager, MenuContainerRoot mr) {
        return null;
    }
}
