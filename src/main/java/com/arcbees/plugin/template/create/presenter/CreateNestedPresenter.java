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

import com.arcbees.plugin.template.domain.presenter.CreatedNestedPresenter;
import com.arcbees.plugin.template.domain.presenter.NestedPresenterOptions;
import com.arcbees.plugin.template.domain.presenter.PresenterOptions;
import com.arcbees.plugin.template.domain.presenter.RenderedTemplate;
import org.apache.velocity.VelocityContext;

public class CreateNestedPresenter extends CreatePresenter<NestedPresenterOptions, CreatedNestedPresenter> {

    public static CreatedNestedPresenter run(PresenterOptions presenterOptions,
            NestedPresenterOptions nestedPresenterOptions, boolean remote) throws Exception {
        CreateNestedPresenter createNestedPresenter = new CreateNestedPresenter(presenterOptions,
                nestedPresenterOptions, remote);
        createNestedPresenter.run();
        return createNestedPresenter.getCreated();
    }

    private CreateNestedPresenter(PresenterOptions presenterOptions, NestedPresenterOptions nestedPresenterOptions,
            boolean remote) {
        super(presenterOptions, nestedPresenterOptions, CreatedNestedPresenter.class, remote);
    }

    @Override
    protected void doRun() throws Exception {
        process();
    }

    @Override
    protected String getLocalTemplatePath() {
        return super.getLocalTemplatePath() + "nested/";
    }

    @Override
    protected String getRemoveTemplatePath() {
        return super.getRemoveTemplatePath() + "nested/";
    }

    @Override
    protected void doSetupVelocityContext(VelocityContext context) {
        super.doSetupVelocityContext(context);

        // nested presenter options
        context.put("revealType", getExtraOption().getRevealType());
        context.put("codesplit", getExtraOption().getCodeSplit());
        context.put("isplace", getExtraOption().getIsPlace());
        context.put("contentSlotImport", getExtraOption().getContentSlotImport());
        context.put("nameTokenImport", getExtraOption().getNameTokenImport());
        context.put("nametoken", getExtraOption().getNameToken());
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

}
