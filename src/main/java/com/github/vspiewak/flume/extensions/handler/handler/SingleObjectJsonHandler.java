package com.github.vspiewak.flume.extensions.handler.handler;

import org.apache.commons.io.IOUtils;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.event.EventBuilder;
import org.apache.flume.source.http.HTTPSourceHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

public class SingleObjectJsonHandler implements HTTPSourceHandler {

    @Override
    public void configure(Context context) {
        //TODO...
    }

    @Override
    public List<Event> getEvents(HttpServletRequest request) throws Exception {
        byte[] bytes = IOUtils.toByteArray(request.getInputStream());
        Event event = EventBuilder.withBody(bytes);
        return Collections.singletonList(event);
    }
}
