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

package com.arcbees.plugin.template.create.place;

import com.arcbees.plugin.template.BaseCreator;
import com.arcbees.plugin.template.domain.place.CreatedNameTokens;
import com.arcbees.plugin.template.domain.place.NameToken;
import com.arcbees.plugin.template.domain.place.NameTokenOptions;
import com.arcbees.plugin.template.domain.presenter.RenderedTemplate;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import java.io.StringWriter;
import java.util.List;

public class CreateNameTokens extends BaseCreator<NameTokenOptions, CreatedNameTokens> {

    private boolean processFileOnly;

    public static CreatedNameTokens run(NameTokenOptions nameTokenOptions, boolean remote, boolean processFileOnly) throws Exception {
        CreateNameTokens created = new CreateNameTokens(nameTokenOptions, remote, processFileOnly);
        created.run();
        return created.getCreated();
    }

    protected CreateNameTokens(NameTokenOptions nameTokenOptions, boolean remote, boolean processFileOnly) {
        super(nameTokenOptions, CreatedNameTokens.class, remote);
        this.processFileOnly = processFileOnly;
    }

    @Override
    protected void doRun() throws Exception {

        if (processFileOnly) {
            processNameTokensFile();
        } else {
            processNameTokensFile();
            processNameTokens();
        }
    }

    private void processNameTokensFile() throws ResourceNotFoundException, ParseErrorException, Exception {
        String fileName = "NameTokens.java.vm";
        Template template = getTemplate(fileName);
        VelocityContext context = getBaseVelocityContext();
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        RenderedTemplate rendered = new RenderedTemplate(renderFileName(fileName), writer.toString());
        getCreated().setNameTokensFile(rendered);
    }

    private void processNameTokens() throws ResourceNotFoundException, ParseErrorException, Exception {
        List<NameToken> tokens = getOption().getNameTokens();
        if (tokens == null) {
            return;
        }

        for (NameToken token : tokens) {
            processNameToken(token);
        }
    }

    private void processNameToken(NameToken token) throws ResourceNotFoundException, ParseErrorException, Exception {
        String field = processNameTokenFieldTemplate(token);
        String method = processNameTokenMethodTemplate(token);

        getCreated().addField(field);
        getCreated().addMethod(method);
    }

    private String processNameTokenFieldTemplate(NameToken token) throws ResourceNotFoundException, ParseErrorException, Exception {
        String fileName = "NameTokenField.vm";
        RenderedTemplate rendered = processTemplate(fileName, token);
        return rendered.getContents();
    }

    private String processNameTokenMethodTemplate(NameToken token) throws ResourceNotFoundException, ParseErrorException, Exception {
        String fileName = "NameTokenMethod.vm";
        RenderedTemplate rendered = processTemplate(fileName, token);
        return rendered.getContents();
    }

    private RenderedTemplate processTemplate(String fileName, NameToken token) throws ResourceNotFoundException, ParseErrorException, Exception {
        Template template = getTemplate(fileName);
        VelocityContext context = getBaseVelocityContext();
        context.put("crawlable", token.getCrawlable());
        context.put("name", token.getToken());

        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        RenderedTemplate rendered = new RenderedTemplate(renderFileName(fileName), writer.toString());
        return rendered;
    }

    @Override
    protected String getLocalTemplatePath() {
        return "templates/place/";
    }

    @Override
    protected String getRemoveTemplatePath() {
        return "https://raw.github.com/ArcBees/IDE-Templates/1.0.0/src/main/resources/com/arcbees/plugin/template/place/";
    }

    @Override
    protected void doSetupVelocityContext(VelocityContext context) {
        context.put("package", getOption().getPackageName());
        context.put("methodName", getOption().getMethodName(0));
    }
}
