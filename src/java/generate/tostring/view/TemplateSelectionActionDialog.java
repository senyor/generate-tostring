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

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import generate.tostring.GenerateToStringActionHandler;
import generate.tostring.GenerateToStringActionHandlerImpl;
import generate.tostring.GenerateToStringContext;
import generate.tostring.GenerateToStringUtils;
import generate.tostring.config.InsertNewMethodPolicy;
import generate.tostring.psi.PsiAdapter;
import generate.tostring.template.TemplateResource;
import generate.tostring.template.TemplateResourceLocator;

import javax.swing.*;
import java.util.List;

/**
 * Quick selection dialog for selecting the template to be used for the code generation.
 */
public class TemplateSelectionActionDialog extends BaseQuickSelectionDialog {

    protected PsiClass clazz;
    protected Project project;
    protected InsertNewMethodPolicy insertPolicy; // overrule default policy

    /**
     * Constructor.
     *
     * @param project   the project.
     * @param clazz     the class.
     * @param options   list of String's with the template name.
     * @param title     dialog title.
     */
    public TemplateSelectionActionDialog(Project project, PsiClass clazz, List<String> options, String title, InsertNewMethodPolicy insertPolicy) {
        super(options, title);
        this.clazz = clazz;
        this.project = project;
        this.insertPolicy = insertPolicy;

        setModal(true);
    }

    protected ListCellRenderer getCellRenderer() {
        return new DefaultListCellRenderer();
    }

    protected void applySelection() {
        // NOP
    }

    /**
     * Invokes the code generation with the selected template.
     */
    protected void doOKAction() {
        String name = (String) optionList.getSelectedValue();

        // active template
        if (name.equals(ConfigUI.activeTemplate.getFileName())) {
            executeGenerateActionLater(ConfigUI.activeTemplate);
            return;
        }

        // file templates
        TemplateResource[] templates = TemplateResourceLocator.getAllTemplates();
        for (TemplateResource template : templates) {
            if (name.equals(template.getFileName())) {
                executeGenerateActionLater(template);
                return;
            }
        }
    }

    /**
     * Executes the generate toString() action.
     * @param template  the choosen template
     */
    private void executeAction(TemplateResource template) {
        GenerateToStringActionHandler action = new GenerateToStringActionHandlerImpl();
        action.executeActionTemplateQuickSelection(project, clazz, template, insertPolicy);
    }

    /**
     * Generates the toString() code for the specified template, doing the work through a WriteAction
     * ran by a CommandProcessor.
     *
     * @param template  the choosen template
     */
    private void executeGenerateActionLater(final TemplateResource template) {
        PsiAdapter psi = GenerateToStringContext.getPsi();
        Runnable writeCommand = new Runnable() {
            public void run() {
                ApplicationManager.getApplication().runWriteAction(new Runnable() {
                    public void run() {
                        try {
                            executeAction(template);
                        } catch (Exception e) {
                            GenerateToStringUtils.handleExeption(project, e);
                        }
                    }
                });
            }
        };

        psi.executeCommand(project, writeCommand);
    }

    public String toString() {
        return "SimpleSelectionDialog{}";
    }

}
