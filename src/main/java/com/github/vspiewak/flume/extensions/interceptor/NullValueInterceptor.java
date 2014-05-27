package com.github.vspiewak.flume.extensions.interceptor;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Usage:
 *
 * agent.sources.s1.interceptors = null_interceptor
 * agent.sources.s1.interceptors.null_interceptor.type = com.github.vspiewak.flume.extensions.interceptor.NullValueInterceptor$Builder
 */
public class NullValueInterceptor implements Interceptor {

    public static final String UTF_8 = "UTF-8";
    //
    private static final Logger log = LoggerFactory.getLogger(NullValueInterceptor.class);

    @Override
    public void initialize() {
        // no-op
    }

    @Override
    public Event intercept(Event event) {

        try {

            byte[] bytes = event.getBody();
            String bodyString = new String(bytes, UTF_8);

            if(!bodyString.equalsIgnoreCase("null")) {
                return event;
            }

        } catch (UnsupportedEncodingException e) {
            log.error("Unsupported Encoding", e);
        }

        return null;
    }

    @Override
    public List<Event> intercept(List<Event> events) {
        List<Event> out = new ArrayList<>();
        for (Event event : events) {
            Event outEvent = intercept(event);
            if (outEvent != null) { out.add(outEvent); }
        }
        return out;
    }

    @Override
    public void close() {
        // no-op
    }

    /**
     * Builder which builds new instance of the StaticInterceptor.
     */
    public static class Builder implements Interceptor.Builder {

        @Override
        public void configure(Context context) {
            // no-op
        }

        @Override
        public Interceptor build() {
            log.info("Creating NullValueInterceptor");
            return new NullValueInterceptor();
        }


    }

}
