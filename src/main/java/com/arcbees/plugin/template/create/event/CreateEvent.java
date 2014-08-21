package com.arcbees.plugin.template.create.event;

import com.arcbees.plugin.template.NamedCreator;
import com.arcbees.plugin.template.domain.event.CreatedEvent;
import com.arcbees.plugin.template.domain.event.EventOptions;
import com.arcbees.plugin.template.domain.presenter.RenderedTemplate;
import org.apache.velocity.VelocityContext;

/**
 * Created by serg on 21.08.2014.
 */
public class CreateEvent extends NamedCreator<EventOptions, CreatedEvent> {

    public static CreatedEvent run(EventOptions eventOptions) throws Exception {
        CreateEvent createEvent = new CreateEvent(eventOptions);
        createEvent.run();
        return createEvent.getCreated();
    }

    public CreateEvent(EventOptions eventOptions) {
        super(eventOptions, CreatedEvent.class, false);
    }

    @Override
    protected String getLocalTemplatePath() {
        return "templates/event/";
    }

    @Override
    protected String getRemoveTemplatePath() {
        return null;
    }

    @Override
    protected void doSetupVelocityContext(VelocityContext context) {
        // base
        context.put("package", getOption().getPackageName());
        context.put("name", getOption().getName());

        context.put("parameters", getOption().getParameters());
    }

    @Override
    protected void doRun() throws Exception {
        processEvent();
    }

    private void processEvent() throws Exception {
        String fileName = "__name__Event.java.vm";
        RenderedTemplate rendered = processTemplate(fileName);
        getCreated().setEvent(rendered);
    }

}
