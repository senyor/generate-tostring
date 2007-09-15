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
package generate.tostring.config;

import com.intellij.psi.*;
import com.intellij.util.IncorrectOperationException;
import generate.tostring.GenerateToStringContext;
import generate.tostring.psi.PsiAdapter;
import generate.tostring.psi.PsiAdapterFactory;


/**
 * Inserts the method at the caret position.
 */
public class InsertAtCaretPolicy implements InsertNewMethodPolicy {

    private static final InsertAtCaretPolicy instance = new InsertAtCaretPolicy();
    private static PsiAdapter psi;

    private InsertAtCaretPolicy() {
    }

    public static InsertAtCaretPolicy getInstance() {
        return instance;
    }

    public boolean insertNewMethod(PsiClass clazz, PsiMethod newMethod) throws IncorrectOperationException {
        // lazy initialize otherwise IDEA throws error: Component requests are not allowed before they are created
        if (psi == null) {
            psi = PsiAdapterFactory.getPsiAdapter();
        }

        PsiElement cur = psi.findElementAtCursorPosition(GenerateToStringContext.getJavaFile(), GenerateToStringContext.getEditor());

        // find better spot to insert, since cur can be anything
        PsiElement spot = findBestSpotToInsert(cur);
        if (spot != null) {
            clazz.addAfter(newMethod, spot);
        } else {
            // okay fallback to just insert at current position
            if (clazz.getRBrace() == cur) {
                // ID 10: If cur positon is last brace (right) of the clazz we should insert before
                clazz.addBefore(newMethod, cur);
            } else {
                clazz.addAfter(newMethod, cur);
            }
        }

        return true;
    }

    private static PsiElement findBestSpotToInsert(PsiElement elem) {
        // we can insert after whitespace, method or a member
        if (elem instanceof PsiWhiteSpace) {
            // parent must not be a method, then we are at whitespace within a method and therefore want to insert after the method
            PsiMethod method = PsiAdapter.findParentMethod(elem);
            return method == null ? elem : method;
        } else if (elem instanceof PsiMethod) {
            // a method is fine
            return elem;
        } else if (elem instanceof PsiMember) {
            // okay only problem is that we can't insert at class position and PsiClass is a subclass for PsiMember
            if (!(elem instanceof PsiClass)) {
                return elem;
            }
        }

        // we reached to far up in the top and can't find a good spot to insert
        if (elem instanceof PsiJavaFile) {
            return null;
        }

        // search up for a good spot
        PsiElement parent = elem.getParent();
        if (parent != null) {
            return findBestSpotToInsert(parent);
        } else {
            return null;
        }
    }

    public String toString() {
        return "At caret";
    }

}
