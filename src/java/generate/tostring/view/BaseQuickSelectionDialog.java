/*
 * Copyright 2001-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package generate.tostring.view;

import com.intellij.openapi.ui.DialogWrapper;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Base class for quick selection dialog
 * <p/>
 * <b>See</b>: SmartIntroducePlugin
 */
public abstract class BaseQuickSelectionDialog extends DialogWrapper {

    protected Logger log = Logger.getLogger(getClass());

    protected final JList optionList;
    private boolean ok = false;
    private long start;
    private boolean done;

    public BaseQuickSelectionDialog(java.util.List options, String title) {
        super(false);
        ListModel listModel = new TemplateResourceListModel(options);
        this.optionList = new JList(listModel);

        setTitle(title);
        setUndecorated(true);
        setModal(false);

        init();
    }

    public boolean isCanceled() {
        return !ok;
    }

    protected void init() {
        optionList.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                dispose();
            }
        });
        optionList.setBorder(new EmptyBorder(4, 4, 4, 4));
        optionList.setSelectedIndex(0);
        optionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        optionList.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                // Since there's no API to get the current mouse-cursor position,
                // it is not possible no move the dialog away from the mouse. If there
                // is a mouse event up to 100ms after dialog.show(), the cursor is moved
                // to the top left corner of the dialog to avoid preselecting an entry
                // at the current position. I guess that still needs some tweaking...
                final long l = System.currentTimeMillis();
                if (log.isDebugEnabled()) {
                    log.debug("mouseEntered after " + (l - start) + " ms");
                }
                if (l - start <= 100) {
                    try {
                        final Point location = getLocation();
                        new Robot().mouseMove((int)location.getX(), (int)location.getY());
                    } catch (AWTException e1) {
                        log.error("Cannot move mouse", e1);
                    }
                }
            }

            public void mousePressed(MouseEvent event) {
                ok = true;
                dispose();
                event.consume();
            }
        });
        optionList.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                optionList.setSelectedIndex(optionList.locationToIndex(e.getPoint()));
            }
        });
        optionList.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    ok = true;
                    dispose();
                    e.consume();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    ok = false;
                    dispose();
                    e.consume();
                }
            }
        });
        optionList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                applySelection();
            }
        });
        optionList.setCellRenderer(getCellRenderer());
        super.init();
    }

    public void show() {
        start = System.currentTimeMillis();
        super.show();
    }

    protected JComponent createNorthPanel() {
        final JLabel jLabel = new JLabel(getTitle());
        jLabel.setBackground(new Color(214, 214, 214));
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 1, 1));
        p.add(jLabel);
        return p;
    }

    protected JComponent createCenterPanel() {
        return optionList;
    }

    protected Border createContentPaneBorder() {
        return new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, Color.GRAY, Color.WHITE, new Color(216, 216, 216));
    }

    protected Action[] createActions() {
        return new Action[0];
    }

    protected void dispose() {
        super.dispose();

        if (!isCanceled()) {
            // hmm, dispose() seems to be called twice
            if (!done) {
                done = true;
                doOKAction();
            }
        } else {
            doCancelAction();
        }
    }

    public int getSelectedIndex() {
        return optionList.getSelectedIndex();
    }

    protected abstract ListCellRenderer getCellRenderer();

    protected abstract void applySelection();

}


