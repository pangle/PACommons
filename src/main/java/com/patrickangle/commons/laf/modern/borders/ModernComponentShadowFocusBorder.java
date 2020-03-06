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
package com.patrickangle.commons.laf.modern.borders;

import com.patrickangle.commons.laf.modern.ModernShapedComponentUI;
import com.patrickangle.commons.laf.modern.util.GraphicsUtils;
import com.patrickangle.commons.laf.modern.util.PaintingUtils;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Shape;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.border.Border;

/**
 *
 * @author patrickangle
 */
public class ModernComponentShadowFocusBorder implements Border {
    protected static final Insets INSETS = new Insets(PaintingUtils.FOCUSABLE_COMPONENT_INSET_SIZE, PaintingUtils.FOCUSABLE_COMPONENT_INSET_SIZE, PaintingUtils.FOCUSABLE_COMPONENT_INSET_SIZE, PaintingUtils.FOCUSABLE_COMPONENT_INSET_SIZE);

    @Override
    public void paintBorder(Component c, Graphics graphics, int x, int y, int width, int height) {
        JComponent component = (JComponent) c;
        Graphics2D g = GraphicsUtils.configureGraphics(graphics);

        // The shape will have an origin at [0, 0]. We will translate accordingly.
        final Shape shape;
        if (component.getUI() instanceof ModernShapedComponentUI) {
            shape = ((ModernShapedComponentUI) component.getUI()).getShape((JComponent) c);
        } else {
            System.out.println("component was not shaped: " + c);
            shape = new Rectangle(0, 0, width, height);
        }
        g.translate(x + INSETS.left, y + INSETS.top);

        if (c.isFocusOwner()) {
            // The component is the focus owner, so draw the focus ring.
            PaintingUtils.paintFocusRing(g, component, shape);
        } else if (c instanceof AbstractButton) {
            // If the component is not the focus owner, we draw a shadow instead.
            PaintingUtils.paintShadow(g, component, shape);
        }

        g.dispose();

    }

    @Override
    public Insets getBorderInsets(Component c) {
        return INSETS;
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
