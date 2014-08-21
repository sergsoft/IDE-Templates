package com.arcbees.plugin.template.domain.event;

import com.arcbees.plugin.template.domain.presenter.RenderedTemplate;

/**
 * Created by serg on 21.08.2014.
 */
public class CreatedEvent {

    private RenderedTemplate event;

    public CreatedEvent() {
    }

    public RenderedTemplate getEvent() {
        return event;
    }

    public void setEvent(RenderedTemplate event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "CreatedEvent{" +
                "event=" + event +
                '}';
    }
}
