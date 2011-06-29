package org.joget.apps.workflow.lib;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.joget.apps.form.model.Form;
import org.joget.apps.form.model.FormButton;
import org.joget.apps.form.model.FormData;
import org.joget.apps.form.service.FormUtil;

/**
 * Form button to complete a workflow assignment
 */
public class AssignmentCompleteButton extends FormButton {

    public static final String DEFAULT_ID = "assignmentComplete";

    @Override
    public String getName() {
        return "Assignment Complete Button";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String getDescription() {
        return "Assignment Complete Button";
    }

    @Override
    public FormData actionPerformed(Form form, FormData formData) {
        Logger.getLogger(AssignmentCompleteButton.class.getName()).log(Level.INFO, " -- AssignmentCompleteButton actionPerformed " + FormUtil.getElementParameterName(this));
        formData.addFormResult(DEFAULT_ID, "true");
        return formData;
    }
}
