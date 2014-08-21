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

import com.arcbees.plugin.template.domain.presenter.CreatedPopupPresenter;
import com.arcbees.plugin.template.domain.presenter.PopupPresenterOptions;
import com.arcbees.plugin.template.domain.presenter.PresenterOptions;
import com.arcbees.plugin.template.domain.presenter.RenderedTemplate;
import org.apache.velocity.VelocityContext;

public class CreatePopupPresenter extends CreatePresenter<PopupPresenterOptions, CreatedPopupPresenter> {

    public static CreatedPopupPresenter run(PresenterOptions presenterOptions,
                    PopupPresenterOptions popupPresenterOptions, boolean remote) throws Exception {
        CreatePopupPresenter createdPopupPresenter = new CreatePopupPresenter(presenterOptions, popupPresenterOptions,
                        remote);
        createdPopupPresenter.run();
        return createdPopupPresenter.getCreated();
    }

    // TODO should be more
    private static final String BASE_REMOTE_GWT = "https://raw.github.com/ArcBees/IDE-Templates/1.0.0/src/main/resources/com/arcbees/plugin/template/presenter/popup/gwt/";
    private static final String BASE_REMOTE_CUSTOM = "https://raw.github.com/ArcBees/IDE-Templates/1.0.0/src/main/resources/com/arcbees/plugin/template/presenter/popup/custom/";
    private final static String BASE_LOCAL_GWT = "./src/main/resources/com/arcbees/plugin/template/presenter/popup/gwt/";
    private final static String BASE_LOCAL_CUSTOM = "./src/main/resources/com/arcbees/plugin/template/presenter/popup/custom/";

    private String baseLocal;
    private String baseRemote;

    private CreatePopupPresenter(PresenterOptions presenterOptions, PopupPresenterOptions popupPresenterOptions,
                    boolean remote) {
        super(presenterOptions, popupPresenterOptions, CreatedPopupPresenter.class, remote);

        // decide which template base to use custom or gwt
        if (remote) {
            if (popupPresenterOptions.getCustom()) {
                baseRemote = BASE_REMOTE_CUSTOM;
            } else {
                baseRemote = BASE_REMOTE_GWT;
            }
        } else {
            if (popupPresenterOptions.getCustom()) {
                baseLocal = BASE_LOCAL_CUSTOM;
            } else {
                baseLocal = BASE_LOCAL_GWT;
            }
        }
    }

    @Override
    protected String getLocalTemplatePath() {
        return baseLocal;
    }

    @Override
    protected String getRemoveTemplatePath() {
        return baseRemote;
    }

    @Override
    protected void doSetupVelocityContext(VelocityContext context) {
        super.doSetupVelocityContext(context);

        // popup presenter options
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
