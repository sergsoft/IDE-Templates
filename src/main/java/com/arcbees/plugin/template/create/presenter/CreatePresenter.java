package com.arcbees.plugin.template.create.presenter;

import com.arcbees.plugin.template.NamedCreator;
import com.arcbees.plugin.template.domain.presenter.PresenterOptions;
import org.apache.velocity.VelocityContext;

/**
 * Created by serg on 21.08.2014.
 */
public abstract class CreatePresenter<TPresenterOption, TCreator> extends NamedCreator<PresenterOptions, TCreator> {

    private TPresenterOption extraOption;

    public CreatePresenter(PresenterOptions presenterOptions, TPresenterOption extraOption, Class<TCreator> tCreatorClass, boolean remote) {
        super(presenterOptions, tCreatorClass, remote);
        this.extraOption = extraOption;
    }

    public TPresenterOption getExtraOption() {
        return extraOption;
    }

    @Override
    protected String getLocalTemplatePath() {
        return "templates/presenter/";
    }

    @Override
    protected String getRemoveTemplatePath() {
        return "https://raw.github.com/ArcBees/IDE-Templates/1.0.0/src/main/resources/com/arcbees/plugin/template/presenter/";
    }

    @Override
    protected void doSetupVelocityContext(VelocityContext context) {
        // base
        context.put("package", getOption().getPackageName());
        context.put("name", getOption().getName());

        // extra options
        context.put("uihandlers", getOption().getUihandlers());
        context.put("onbind", getOption().getOnbind());
        context.put("onhide", getOption().getOnhide());
        context.put("onreset", getOption().getOnreset());
        context.put("onunbind", getOption().getOnunbind());
        context.put("manualreveal", getOption().getManualReveal());
        context.put("preparefromrequest", getOption().getPrepareFromRequest());
    }
}
