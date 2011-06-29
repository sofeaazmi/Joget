package org.joget.apps.form.lib;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import org.joget.apps.app.model.AppDefinition;
import org.joget.apps.app.service.AppUtil;
import org.joget.apps.form.model.Element;
import org.joget.apps.form.model.Form;
import org.joget.apps.form.model.FormBuilderPaletteElement;
import org.joget.apps.form.model.FormBuilderPalette;
import org.joget.apps.form.model.FormData;
import org.joget.apps.form.model.FormRow;
import org.joget.apps.form.model.FormRowSet;
import org.joget.apps.form.service.FormUtil;

public class FileUpload extends Element implements FormBuilderPaletteElement {

    @Override
    public String getName() {
        return "FileUpload";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String getDescription() {
        return "FileUpload Element";
    }

    @Override
    public String renderTemplate(FormData formData, Map dataModel) {
        String template = "fileUpload.ftl";

        // set value
        String value = FormUtil.getElementPropertyValue(this, formData);
        dataModel.put("value", value);

        // determine actual path for the file uploads
        String primaryKeyValue = getPrimaryKeyValue(formData);
        String fileName = value;
        if (fileName != null) {
            try {
                fileName = URLEncoder.encode(value, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                // ignore
            }
        }
        String formDefId = "";
        Form form = FormUtil.findRootForm(this);
        if (form != null) {
            formDefId = form.getPropertyString(FormUtil.PROPERTY_ID);
        }
        String encodedFileName = fileName;
        if (fileName != null) {
            try {
                encodedFileName = URLEncoder.encode(fileName, "UTF8");
            } catch (UnsupportedEncodingException ex) {
                // ignore
            }
        }

        String appId = "";
        String appVersion = "";

        AppDefinition appDef = AppUtil.getCurrentAppDefinition();

        if (appDef != null) {
            appId = appDef.getId();
            appVersion = appDef.getVersion().toString();
        }

        String filePath = "/web/client/app/" + appId + "/" + appVersion + "/form/download/" + formDefId + "/" + primaryKeyValue + "/" + encodedFileName + ".";
        if (Boolean.valueOf(getPropertyString("attachment")).booleanValue()) {
            filePath += "?attachment=true";
        }
        dataModel.put("filePath", filePath);

        String html = FormUtil.generateElementHtml(this, formData, template, dataModel);
        return html;
    }

    @Override
    public FormRowSet formatData(FormData formData) {
        FormRowSet rowSet = super.formatData(formData);
        if (rowSet != null && !rowSet.isEmpty()) {
            // check for file removal
            String postfix = "_remove";
            String id = FormUtil.getElementParameterName(this);
            if (id != null) {
                String removalId = id + postfix;
                String fileName = formData.getRequestParameter(id);
                String removalFlag = formData.getRequestParameter(removalId);
                if (fileName != null && fileName.isEmpty() && removalFlag == null) {
                    // don't remove file, reset value
                    String binderValue = formData.getLoadBinderDataProperty(this, id);
                    if (binderValue != null) {
                        formData.addRequestParameterValues(id, new String[]{binderValue});
                        FormRow row = rowSet.iterator().next();
                        row.setProperty(id, binderValue);
                    }
                }
            }
        }
        return rowSet;
    }

    @Override
    public String getClassName() {
        return getClass().getName();
    }

    @Override
    public String getFormBuilderTemplate() {
        return "<label class='label'>FileUpload</label><input type='file' />";
    }

    @Override
    public String getLabel() {
        return "FileUpload";
    }

    @Override
    public String getPropertyOptions() {
        return AppUtil.readPluginResource(getClass().getName(), "/properties/form/fileUpload.json", null, true, "message/form/FileUpload");
    }

    @Override
    public String getDefaultPropertyValues() {
        return "{label:'FileUpload'}";
    }

    @Override
    public String getFormBuilderCategory() {
        return FormBuilderPalette.CATEGORY_GENERAL;
    }

    @Override
    public int getFormBuilderPosition() {
        return 900;
    }

    @Override
    public String getFormBuilderIcon() {
        return null;
    }
}
