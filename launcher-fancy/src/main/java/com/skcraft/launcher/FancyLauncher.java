/*
 * SKCraft Launcher
 * Copyright (C) 2010-2014 Albert Pham <http://www.sk89q.com> and contributors
 * Please see LICENSE.txt for license information.
 */

package com.skcraft.launcher;

import com.google.common.base.Supplier;
import com.skcraft.launcher.swing.SwingHelper;
import com.skcraft.launcher.util.SharedLocale;
import com.formdev.flatlaf.FlatDarculaLaf;
import lombok.extern.java.Log;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;

@Log
public class FancyLauncher {

    public static void main(final String[] args) {
        Launcher.setupLogger();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().setContextClassLoader(FancyLauncher.class.getClassLoader());
                    UIManager.getLookAndFeelDefaults().put("ClassLoader", FancyLauncher.class.getClassLoader());
                    UIManager.getDefaults().put("SplitPane.border", BorderFactory.createEmptyBorder());
                    System.setProperty("sun.awt.noerasebackground", "true");
                    System.setProperty("substancelaf.windowRoundedCorners", "true");
					System.setProperty("awt.useSystemAAFontSettings","on");
					System.setProperty("Dawt.useSystemAAFontSettings","on");
					System.setProperty("swing.aatext", "true");
                    FlatDarculaLaf.setup();
                    UIManager.put("Button.arc", 6);
                    UIManager.put("Component.arc", 6);
                    UIManager.put("ProgressBar.arc", 6);
                    UIManager.put("TextComponent.arc", 6);
                    UIManager.put("PopupMenu.arc", 6);
                    System.setProperty("apple.awt.application.name", SharedLocale.tr("launcher.appTitle"));
                    System.setProperty("apple.laf.useScreenMenuBar", "true");
                    System.setProperty("apple.awt.application.appearance", "system");
                    Launcher launcher = Launcher.createFromArguments(args);
                    launcher.setMainWindowSupplier(new CustomWindowSupplier(launcher));
                    launcher.showLauncherWindow();
                } catch (Throwable t) {
                    log.log(Level.WARNING, "Load failure", t);
                    SwingHelper.showErrorDialog(null, "Uh oh! The updater couldn't be opened because a " +
                            "problem was encountered.", "Launcher error", t);
                }
            }
        });
    }

    private static class CustomWindowSupplier implements Supplier<Window> {

        private final Launcher launcher;

        private CustomWindowSupplier(Launcher launcher) {
            this.launcher = launcher;
        }

        @Override
        public Window get() {
            return new FancyLauncherFrame(launcher);
        }
    }

}
