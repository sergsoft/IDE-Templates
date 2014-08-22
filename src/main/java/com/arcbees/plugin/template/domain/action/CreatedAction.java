package com.arcbees.plugin.template.domain.action;

import com.arcbees.plugin.template.domain.presenter.RenderedTemplate;

/**
 * Created by serg on 22.08.2014.
 */
public class CreatedAction {

    private RenderedTemplate request;

    private RenderedTemplate result;

    private RenderedTemplate actionHandler;

    public RenderedTemplate getRequest() {
        return request;
    }

    public void setRequest(RenderedTemplate request) {
        this.request = request;
    }

    public RenderedTemplate getResult() {
        return result;
    }

    public void setResult(RenderedTemplate result) {
        this.result = result;
    }

    public RenderedTemplate getActionHandler() {
        return actionHandler;
    }

    public void setActionHandler(RenderedTemplate actionHandler) {
        this.actionHandler = actionHandler;
    }

    @Override
    public String toString() {
        return "CreatedAction{" +
                "request=" + request +
                ", result=" + result +
                ", actionHandler=" + actionHandler +
                '}';
    }
}
