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
package generate.tostring.util;

/**
 * String utility methods.
 */
public class StringUtil {

    /**
     * Private constructor, to prevent instances of this class, since it only has static members.
     */
    private StringUtil() {
    }

    /**
     * Is the string empty (null, or contains just whitespace)
     *
     * @param s string to test.
     * @return true if it's an empty string.
     */
    public static boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    /**
     * Does the string contain some chars (whitespace is consideres as empty)
     *
     * @param s string to test.
     * @return true if it's NOT an empty string.
     */
    public static boolean isNotEmpty(String s) {
        return ! isEmpty(s);
    }

    /**
     * Is the string in lowercase only?
     * @param s   string to test.
     * @return   true if string is in lowercase only, false if not.
     */
    public static boolean isLowerCase(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (! Character.isLowerCase(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Is the string in uppercase only?
     * @param s   string to test.
     * @return   true if string is in uppercase only, false if not.
     */
    public static boolean isUpperCase(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (! Character.isUpperCase(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Does the string have an uppercase character?
     * @param s  the string to test.
     * @return   true if the string has an uppercase character, false if not.
     */
    public static boolean hasUpperCaseChar(String s) {
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Does the string have a lowercase character?
     * @param s  the string to test.
     * @return   true if the string has a lowercase character, false if not.
     */
    public static boolean hasLowerCaseChar(String s) {
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if (Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the part of s after the token.
     * <p/>
     * <br/>Example:   after("helloWorldThisIsMe", "World") will return "ThisIsMe".
     * <br/>Example:   after("helloWorldThisIsMe", "Dog") will return null.
     *
     * @param s   the string to test.
     * @param token   the token.
     * @return  the part of s that is after the token.
     */
    public static String after(String s, String token) {
        return after(s, token, false);
    }

    /**
     * Returns the part of s after the last token.
     *
     * @param s   the string to test.
     * @param token   the token.
     * @param last should scan for last token, if not then the first token.
     * @return  the part of s that is after the token.
     */
    public static String after(String s, String token, boolean last) {
        if (s == null) {
            return null;
        }

        int i;
        if (last) {
            i = s.lastIndexOf(token);
        } else {
            i = s.indexOf(token);
        }
        if (i == -1) {
            return s;
        }

        return s.substring(i + token.length());
    }

    /**
     * Returns the part of s before the token.
     * <p/>
     * <br/>Example:   before("helloWorldThisIsMe", "World") will return "hello".
     * <br/>Example:   before("helloWorldThisIsMe", "Dog") will return "helloWorldThisIsMe".
     * <p/>
     * If the token is not in the string, the entire string is returned.
     *
     * @param s   the string to test.
     * @param token   the token.
     * @return  the part of s that is before the token.
     */
    public static String before(String s, String token) {
        if (s == null) {
            return null;
        }

        int i = s.indexOf(token);
        if (i == -1) {
            return s;
        }

        return s.substring(0, i);
    }

    /**
     * Returns the middle part of s between before and after tokens.
     * <p/>
     * <br/>Example:   middle("helloWorldThisIsMe", "World", "Me") will return "ThisIs".
     * <br/>Example:   middle("helloWorldThisIsMe", "World", Dog") will return "ThisIsMe".
     *
     * @param s       the string to test
     * @param before  the before token
     * @param after   the after token
     * @return  the middle part
     */
    public static String middle(String s, String before, String after) {
        String first = after(s, before);
        return before(first, after);
    }

    /**
     * Converts the first letter to lowercase
     * <p/>
     * <br/>Example: FirstName => firstName
     * <br/>Example: name => name
     * <br/>Example: S => s
     *
     * @param s  the string
     * @return  the string with the first letter in lowercase.
     */
    public static String firstLetterToLowerCase(String s) {
        if (s.length() > 1) {
            return Character.toLowerCase(s.charAt(0)) + s.substring(1);
        } else if (s.length() == 1) {
            return "" + Character.toLowerCase(s.charAt(0));
        } else {
            return s;
        }
    }

    /**
     * Fixes linebreaks generated from Velocity to be compatible with IDEA #3296 or later.
     * 
     * @param s   input string
     * @return    output string with fixed linebreaks
     */
    public static String fixLineBreaks(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.LINE_SEPARATOR != c) {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * Counts the number of tokens in the string.
     * 
     * @param s  the string
     * @param token  the token to count
     * @return number of tokens found
     */
    public static int countTokens(String s, char token) {
        int cnt = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == token) {
                cnt++;
            }
        }
        return cnt;
    }

}
