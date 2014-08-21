/**
 * Copyright 2013 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.plugin.template.create.presenter;

import com.arcbees.plugin.template.domain.presenter.CreatedPresenterWidget;
import com.arcbees.plugin.template.domain.presenter.PresenterOptions;
import com.arcbees.plugin.template.domain.presenter.PresenterWidgetOptions;
import com.arcbees.plugin.template.domain.presenter.RenderedTemplate;
import org.apache.velocity.VelocityContext;

public class CreatePresenterWidget extends CreatePresenter<PresenterWidgetOptions, CreatedPresenterWidget> {

    public static CreatedPresenterWidget run(PresenterOptions presenterOptions,
                    PresenterWidgetOptions presenterWidgetOptions, boolean remote) throws Exception {
        CreatePresenterWidget createdPresenterWidget = new CreatePresenterWidget(presenterOptions,
                        presenterWidgetOptions, remote);
        createdPresenterWidget.run();
        return createdPresenterWidget.getCreated();
    }

    private CreatePresenterWidget(PresenterOptions presenterOptions, PresenterWidgetOptions presenterWidgetOptions,
                    boolean remote) {
        super(presenterOptions, presenterWidgetOptions, CreatedPresenterWidget.class, remote);
    }

    @Override
    protected String getLocalTemplatePath() {
        return super.getLocalTemplatePath() + "widget/";
    }

    @Override
    protected String getRemoveTemplatePath() {
        return super.getRemoveTemplatePath() + "widget/";
    }

    @Override
    protected void doSetupVelocityContext(VelocityContext context) {
        super.doSetupVelocityContext(context);

        // presenter widget options
        context.put("singleton", getExtraOption().getSingleton());
    }

    private void process() throws Exception {
        processModule();
        processPresenter();
        processUiHandlers();
        processView();
        processViewBinder();
    }

    private void processModule() throws Exception {
        String fileName = "__name__Module.java.vm";
        RenderedTemplate rendered = processTemplate(fileName);
        getCreated().setModule(rendered);
    }

    private void processPresenter() throws Exception {
        String fileName = "__name__Presenter.java.vm";
        RenderedTemplate rendered = processTemplate(fileName);
        getCreated().setPresenter(rendered);
    }

    private void processUiHandlers() throws Exception {
        String fileName = "__name__UiHandlers.java.vm";
        RenderedTemplate rendered = processTemplate(fileName);
        getCreated().setUihandlers(rendered);
    }

    private void processView() throws Exception {
        String fileName = "__name__View.java.vm";
        RenderedTemplate rendered = processTemplate(fileName);
        getCreated().setView(rendered);
    }

    private void processViewBinder() throws Exception {
        String fileName = "__name__View.ui.xml.vm";
        RenderedTemplate rendered = processTemplate(fileName);
        getCreated().setViewui(rendered);
    }

    @Override
    protected void doRun() throws Exception {
        process();
    }

}
