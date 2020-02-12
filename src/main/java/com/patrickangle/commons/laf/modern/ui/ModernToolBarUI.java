/*
 * Patrick Angle Commons Library
 * Copyright 2018 Patrick Angle
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.patrickangle.commons.laf.modern.ui;

import com.patrickangle.commons.laf.modern.ui.util.GradientTexturePaint;
import com.patrickangle.commons.laf.modern.ui.util.NoisePaint;
import com.patrickangle.commons.util.AquaUtils;
import com.patrickangle.commons.util.Colors;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicToolBarUI;

/**
 *
 * @author patrickangle
 */
public class ModernToolBarUI extends BasicToolBarUI {

    private WindowAdapter windowFocusListener;
    private HierarchyListener buttonHierarchyListener;
    protected Window currentWindow;

    @SuppressWarnings({"MethodOverridesStaticMethodOfSuperclass", "UnusedDeclaration"})
    public static ComponentUI createUI(JComponent c) {
        return new ModernToolBarUI();
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        windowFocusListener = new WindowAdapter() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                c.repaint();
            }

            @Override
            public void windowGainedFocus(WindowEvent e) {
                c.repaint();
            }

            @Override
            public void windowActivated(WindowEvent e) {
                c.repaint();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                c.repaint();
            }
        };

        buttonHierarchyListener = new HierarchyListener() {
            @Override
            public void hierarchyChanged(HierarchyEvent e) {
                updateFocusListener(c);
            }
        };
    }

    @Override
    protected void installListeners() {
        super.installListeners(); //To change body of generated methods, choose Tools | Templates.
        toolBar.removeMouseListener(dockingListener);
        toolBar.removeMouseMotionListener(dockingListener);
        toolBar.addHierarchyListener(buttonHierarchyListener);
    }

    @Override
    protected void uninstallListeners() {
        super.uninstallListeners(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    protected void updateFocusListener(JComponent b) {
        uninstallFocusListener();

        Window w = SwingUtilities.getWindowAncestor(b);
        if (w != null) {
            this.currentWindow = w;
            w.addWindowListener(windowFocusListener);
        }
    }

    protected void uninstallFocusListener() {
        if (currentWindow != null) {
            currentWindow.removeWindowListener(windowFocusListener);
        }
    }

    private GradientTexturePaint cachedPaint = null;
    private int cachedHeight = 0;

    @Override
    public void paint(Graphics g, JComponent c) {
        if (!AquaUtils.isMac()) {
            g.setColor(UIManager.getColor("ToolBar.background"));
            g.fillRect(0, 0, c.getWidth(), c.getHeight());
        }

        // Paint gradient
        Window w = SwingUtilities.getWindowAncestor(c);
//        if (w != null && w.isFocused()) {
            // Only active windows get this extra affordance
//            if (c.getHeight() != cachedHeight || cachedPaint == null) {
            Graphics2D g2 = (Graphics2D) g.create();
            cachedPaint = new GradientTexturePaint(0, 0, Colors.transparentColor(Color.BLACK, 0f), 0, c.getHeight(), Colors.transparentColor(Color.BLACK, 0.2f));
            cachedHeight = c.getHeight();
            g2.setPaint(cachedPaint);
            g2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.fillRect(0, 0, c.getWidth(), c.getHeight());

//            g2.setPaint(new NoisePaint(Colors.grey(0.2f), 0.01f, 0.01f));
//            g2.fillRect(0, 0, c.getWidth(), c.getHeight());
//            }
//        }

//        if (c.isOpaque()) {
//            
//        } else {
//            
//        }
    }

    @Override
    protected Border getNonRolloverBorder(AbstractButton b) {
        return b.getBorder();
    }

    @Override
    protected Border getRolloverBorder(AbstractButton b) {
        return b.getBorder();
    }

    @Override
    protected void installNormalBorders(JComponent c) {
    }

    @Override
    protected void installNonRolloverBorders(JComponent c) {
    }

    @Override
    protected void installRolloverBorders(JComponent c) {
    }

    @Override
    protected void setBorderToNonRollover(Component c) {
    }

    @Override
    protected void setBorderToNormal(Component c) {
    }

    @Override
    public void update(Graphics g, JComponent c) {
        super.update(g, c); //To change body of generated methods, choose Tools | Templates.
        g.setColor(Color.BLACK);
        g.drawLine(0, c.getHeight() - 1, c.getWidth(), c.getHeight() - 1);
    }

    public static void installIntoDefaults(UIDefaults defaults) {
        defaults.put("ToolBarUI", ModernToolBarUI.class.getName());
        defaults.put("ToolBar.Border", new EmptyBorder(0, 0, 0, 0));
    }
}
