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

import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.OrderEntry;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.RootPolicy;
import com.intellij.openapi.vfs.VirtualFile;
import generate.tostring.template.TemplateResource;

/**
 * TemplateResource as an OrderEntry.
 * <p/>
 * Is used by TemplateQuickSelectionConfigUI.
 */
public class TemplateResourceOrderEntry implements OrderEntry {

    private TemplateResource template;

    public TemplateResourceOrderEntry(TemplateResource template) {
        this.template = template;
    }

    public boolean isChecked() {
        return false;
    }

    public void setChecked(boolean checked) {
    }

    public VirtualFile[] getFiles(OrderRootType orderRootType) {
        return null;
    }

    public String[] getUrls(OrderRootType orderRootType) {
        return null;
    }

    public String getPresentableName() {
        return template.getFileName();
    }

    public boolean isValid() {
        return true;
    }

    public Module getOwnerModule() {
        return null;
    }

    public Object accept(RootPolicy rootPolicy, Object object) {
        return null;
    }

    public boolean isSynthetic() {
        return false;
    }

    public TemplateResource getTemplate() {
        return template;
    }

    public int compareTo(OrderEntry o) {
        if (this == o) return 0;
        if (!(o instanceof TemplateResourceOrderEntry)) return 0;
        TemplateResourceOrderEntry obj = (TemplateResourceOrderEntry) o;
        return template.getFileName().compareTo(obj.template.getFileName());
    }

    public String toString() {
        return template.getFileName();
    }

}
