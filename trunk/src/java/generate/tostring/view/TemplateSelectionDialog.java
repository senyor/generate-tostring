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

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import generate.tostring.template.TemplateResource;

import javax.swing.*;
import java.util.List;

/**
 * Selection dialog for selecting template to be added in Quick Template Selection List.
 */
public class TemplateSelectionDialog extends BaseQuickSelectionDialog {

    protected PsiClass clazz;
    protected Project project;
    private TemplateResourceOrderEntry selected;

    /**
     * Constructor.
     *
     * @param options   list of {@link TemplateResource} objects.
     * @param title     title of dialog
     */
    public TemplateSelectionDialog(List options, String title) {
        super(options, title);

        setModal(true);
    }

    protected ListCellRenderer getCellRenderer() {
        return new DefaultListCellRenderer();
    }

    protected void applySelection() {
        // NOP
    }

    protected void doOKAction() {
        selected = (TemplateResourceOrderEntry) optionList.getSelectedValue();
    }

    /**
     * Get's the selected template.
     *
     * @return  the selected template
     */
    public TemplateResourceOrderEntry getSelected() {
        return selected;
    }

    public String toString() {
        return "SimpleSelectionDialog{}";
    }



}
