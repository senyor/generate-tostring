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
package generate.tostring;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiManager;
import com.intellij.psi.codeStyle.CodeStyleManager;
import generate.tostring.config.Config;
import generate.tostring.psi.PsiAdapter;
import generate.tostring.psi.PsiAdapterFactory;
import org.apache.log4j.Logger;

/**
 * Application context for this plugin.
 */
public class GenerateToStringContext {

    private static Logger log = Logger.getLogger(GenerateToStringContext.class);
    private static PsiAdapter psi;
    private static Config config;
    private static Project project;

    public static Config getConfig() {
        if (config == null) {
            log.warn("Config is null - return a new default Config");
            config = new Config();
        }
        return config;
    }

    public static void setConfig(Config newConfig) {
        config = newConfig;
    }

    public static void setProject(Project newProject) {
        project = newProject;
    }

    public static PsiAdapter getPsi() {
        if (psi == null) {
            psi = PsiAdapterFactory.getPsiAdapter();
        }
        return psi;
    }

    public static Project getProject() {
        return project;
    }

    public static PsiManager getManager() {
        return psi.getPsiManager(getProject());
    }

    public static PsiElementFactory getElementFactory() {
        return psi.getPsiElemetFactory(getManager());
    }

    public static CodeStyleManager getCodeStyleManager() {
        return psi.getCodeStyleManager(getProject());
    }

    /**
     * Cleanup any resources when the project is closed 
     */
    public static void dispose() {
        project = null;
        psi = null;
        config = null;
    }

}
