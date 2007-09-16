Author:
    Claus Ibsen (cib_rejse@yahoo.dk)

Intellij plugin:
    http://plugins.intellij.net/plugins

Plugin project page:
    http://generate-tostring.googlecode.com
    http://code.google.com/p/generate-tostring/

Wiki:
    http://code.google.com/p/generate-tostring/w/list

Issue tracker:
    http://code.google.com/p/generate-tostring/issues/list

Forum - genereate toString() release announcements:
    http://www.intellij.net/forums/thread.jspa?threadID=267303&tstart=0

About:
======
Generate toString() is a plugin for Intellij IDEA.
The plugin adds a new action 'toString()' in the generate menu (alt + ins).
The action generates a toString() method that dumps the classes fields.
Java body code is generated using Velocity Macro and you can change this to fit your needs.
The plugin has it's own settings (ctrl + alt + s).
Full documentation included (Click the link from Settings).


Ideas:
======
- Screenshot of run toString() inspections (non on-the-fly)
- Screenshot of using quick template selection list
- Include screenshots in plugin documentation
- Refactor HandlerImpl to seperate code generator (having Velocity impl for now)
- Inspection to be smarter for toString() methods that just dump a display name and nothing with dumping fields
- Insert at caret position and within anonymous class
- StringBuffer/Builder templates to use singleline if no fields to output
- Support "insert @Override" in EAP #5557 field chooser dialog
- Support generating ToString() methods for JDK1.5 enums
- JDK1.5 (annotations, enum, generic, vararg etc.) in velocity context
- Insert after/before method (reg exp)
- Possible to add a display name to TemplateResource to have a different text in template quick selection list than the filename
- MethodElement to have more information: hasParameters, throwsException and much more.
- Generate toString() for multiple classes (for fast generation of toString() in classes that for instance has been code generated)
- Dump protected/packagelocal fields from super instead of super.toString()
- Use IDEA UI Components instead of Swing in the Settings
- Redesign Settings UI using IDEA GUI Designer
- Repaint config icon to use new IDEA style of 'override' icon
- Use reg.exp. for parsing template code into javadoc, methodbody, template (better validation error displayed)
- public int compareTo(Object o); template
- Honor insert position of toString according to rearranger plugin
- Velocity Macro Syntax integrated to IDEA
- Use IDEA edtitor for template editing
