package com.gigaspaces.app.event_processing.processor;

import com.gigaspaces.app.event_processing.common.Data;
import com.gigaspaces.app.event_processing.common.EStatus;
import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.polling.Polling;

import javax.annotation.Resource;
import java.util.logging.Logger;

/**
 * Created by tal on 8/25/16.
 */
//@EventDriven
//@Polling
public class SwitchBase {
    Logger log = Logger.getLogger(this.getClass().getName());

    @Resource
    @GigaSpaceContext(name="gigaSpace")
    public GigaSpace gigaSpace;




}
