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

import com.intellij.ui.OrderPanel;
import generate.tostring.template.TemplateResource;
import generate.tostring.template.TemplateResourceLocator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * List panel for configuring Template Selection Quick List.
 */
public class TemplateQuickSelectionConfigUI extends OrderPanel<TemplateResourceOrderEntry> {

    private JButton upButton;
    private JButton downButton;
    private JButton addButton;
    private JButton removeButton;

    public TemplateQuickSelectionConfigUI(Class<TemplateResourceOrderEntry> aClass) {
        super(aClass, false);
        getEntryTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void moveSelectedItemsUp() {
        if (getEntryTable().getSelectedRow() > 0)
            super.moveSelectedItemsUp();
    }

    public void moveSelectedItemsDown() {
        if (getEntryTable().getSelectedRow() < getEntryTable().getRowCount() - 1)
            super.moveSelectedItemsDown();
    }

    public boolean isCheckable(TemplateResourceOrderEntry templateResourceOrderEntry) {
        return false;
    }

    public boolean isChecked(TemplateResourceOrderEntry templateResourceOrderEntry) {
        return false;
    }

    public void setChecked(TemplateResourceOrderEntry templateResourceOrderEntry, boolean b) {
        // NOP
    }

    public void setUpButton(JButton button) {
        this.upButton = button;
        upButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                TemplateQuickSelectionConfigUI.this.moveSelectedItemsUp();
            }
        });
    }

    public void setDownButton(JButton button) {
        this.downButton = button;
        downButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                TemplateQuickSelectionConfigUI.this.moveSelectedItemsDown();
            }
        });
    }

    public void setAddButton(JButton button) {
        this.addButton = button;
        addButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                List<TemplateResource> templates = getAllTemplateResources();
                List<TemplateResourceOrderEntry> all = new ArrayList<TemplateResourceOrderEntry>();
                for (TemplateResource template : templates) {
                    TemplateResourceOrderEntry entry = new TemplateResourceOrderEntry(template);
                    all.add(entry);
                }
                TemplateSelectionDialog dialog = new TemplateSelectionDialog(all, "Select template to add");
                dialog.show();
                if (dialog.getSelected() != null) {
                    add(dialog.getSelected());
                }
                dialog.dispose();
            }
        });
    }

    public void setRemoveButton(JButton button) {
        this.removeButton = button;
        removeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (getEntryTable().getSelectedRow() > -1) {
                    TemplateResourceOrderEntry entry = TemplateQuickSelectionConfigUI.this.getValueAt(getEntryTable().getSelectedRow());
                    if (entry != null) {
                        TemplateQuickSelectionConfigUI.this.remove(entry);
                    }
                }
            }
        });
    }

    private static List<TemplateResource> getAllTemplateResources() {
        List<TemplateResource> templates = new ArrayList<TemplateResource>();
        templates.add(ConfigUI.activeTemplate);
        templates.addAll(Arrays.asList(TemplateResourceLocator.getAllTemplates()));
        return templates;
    }

    public String getSelectedQuickTemplates() {
        StringBuffer sb = new StringBuffer();

        List entries = getEntries();
        for (Object entry : entries) {
            TemplateResourceOrderEntry ent = (TemplateResourceOrderEntry) entry;
            sb.append(ent.getTemplate().getFileName()).append(';');
        }

        if (sb.length() > 0) {
            return sb.toString();
        } else {
            return null;
        }
    }

    public void initTemplateQuickSelectionList(String selected) {
        if (selected == null) {
            return;
        }

        clear();
        List<TemplateResource> templates = getAllTemplateResources();

        String[] split = selected.split(";");
        for (String name : split) {
            TemplateResource resource = findTemplateResourceWithFileName(templates, name);
            if (resource != null) {
                this.add(new TemplateResourceOrderEntry(resource));
            }
        }
    }

    private static TemplateResource findTemplateResourceWithFileName(List<TemplateResource> templates, String name) {
        for (TemplateResource resource : templates) {
            if (name.equals(resource.getFileName())) {
                return resource;
            }
        }
        return null;
    }

}
