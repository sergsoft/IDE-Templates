package com.arcbees.plugin.template.create.event;

import com.arcbees.plugin.template.domain.event.EventOptions;
import org.junit.Test;

public class CreateEventTest {

    @Test
    public void testRun() throws Exception {
        EventOptions eventOption = new EventOptions();

        eventOption.setPackageName("pkg.name");
        eventOption.setName("Test");

        CreateEvent.run(eventOption);
    }
}