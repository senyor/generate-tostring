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
package generate.tostring.inspection;

import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import generate.tostring.GenerateToStringActionHandler;
import generate.tostring.GenerateToStringActionHandlerImpl;
import generate.tostring.GenerateToStringContext;
import generate.tostring.config.InsertNewMethodPolicy;
import org.jetbrains.annotations.NotNull;

/**
 * Quick fix to run Generate toString() to fix field not used in existing toString() method.
 */
public class FieldNotUsedInToStringQuickFix extends AbstractGenerateToStringQuickFix {

    public void applyFix(@NotNull Project project, ProblemDescriptor desc) {

        // find the class
        PsiClass clazz = psi.findClass(desc.getPsiElement());
        if (clazz == null) {
            return; // no class to fix, so return
        }

        // execute the action
        GenerateToStringActionHandler handler = new GenerateToStringActionHandlerImpl();

        // should use the policy determined in the configuration
        InsertNewMethodPolicy policy = GenerateToStringContext.getConfig().getInsertNewMethodInitialOption();

        handler.executeActionQickFix(project, clazz, desc, policy);
    }

}
