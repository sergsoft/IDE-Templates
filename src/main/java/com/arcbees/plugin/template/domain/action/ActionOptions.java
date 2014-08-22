package com.arcbees.plugin.template.domain.action;

import com.arcbees.plugin.template.domain.NamedOptions;
import com.arcbees.plugin.template.domain.ParameterOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serg on 21.08.2014.
 */
public class ActionOptions extends NamedOptions {

    private String packageName;

    private String superClass;

    private List<ParameterOptions> actionFields = new ArrayList<ParameterOptions>();

    private List<ParameterOptions> resultFileds = new ArrayList<ParameterOptions>();

    private String actionHandlerPkg;

    private String actionValidator;

    private String handlerModule;

    private boolean withoutSecure = false;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getSuperClass() {
        return superClass;
    }

    public void setSuperClass(String superClass) {
        this.superClass = superClass;
    }

    public List<ParameterOptions> getActionFields() {
        return actionFields;
    }

    public void setActionFields(List<ParameterOptions> actionFields) {
        this.actionFields = actionFields;
    }

    public List<ParameterOptions> getResultFileds() {
        return resultFileds;
    }

    public void setResultFileds(List<ParameterOptions> resultFileds) {
        this.resultFileds = resultFileds;
    }

    public String getActionHandlerPkg() {
        return actionHandlerPkg;
    }

    public void setActionHandlerPkg(String actionHandlerPkg) {
        this.actionHandlerPkg = actionHandlerPkg;
    }

    public String getActionValidator() {
        return actionValidator;
    }

    public void setActionValidator(String actionValidator) {
        this.actionValidator = actionValidator;
    }

    public String getHandlerModule() {
        return handlerModule;
    }

    public void setHandlerModule(String handlerModule) {
        this.handlerModule = handlerModule;
    }

    public boolean isWithoutSecure() {
        return withoutSecure;
    }

    public void setWithoutSecure(boolean withoutSecure) {
        this.withoutSecure = withoutSecure;
    }
}
