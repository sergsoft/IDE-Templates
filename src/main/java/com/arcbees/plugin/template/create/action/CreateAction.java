package com.arcbees.plugin.template.create.action;

import com.arcbees.plugin.template.NamedCreator;
import com.arcbees.plugin.template.domain.action.ActionOptions;
import com.arcbees.plugin.template.domain.action.CreatedAction;
import org.apache.velocity.VelocityContext;

/**
 * Created by serg on 22.08.2014.
 */
public class CreateAction extends NamedCreator<ActionOptions, CreatedAction> {

    public CreateAction(ActionOptions actionOptions) {
        super(actionOptions, CreatedAction.class, false);
    }

    @Override
    protected String getLocalTemplatePath() {
        return "templates/action/";
    }

    @Override
    protected String getRemoveTemplatePath() {
        return null;
    }

    @Override
    protected void doSetupVelocityContext(VelocityContext context) {
        context.put("name", getOption().getName());
        context.put("packageClient", getOption().getPackageName());
        context.put("packageServer", getOption().getActionHandlerPkg());
        context.put("withoutSecure", getOption().isWithoutSecure());
        context.put("actionParams", getOption().getActionFields());
        context.put("resultParams", getOption().getResultFileds());
    }

    @Override
    protected void doRun() throws Exception {
        getCreated().setRequest(processTemplate("__name__.java.vm"));
        getCreated().setResult(processTemplate("__name__Result.java.vm"));
        getCreated().setActionHandler(processTemplate("__name__ActionHandler.java.vm"));
    }
}
