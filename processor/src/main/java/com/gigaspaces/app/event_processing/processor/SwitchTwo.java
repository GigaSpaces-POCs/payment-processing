package com.gigaspaces.app.event_processing.processor;

import com.gigaspaces.app.event_processing.common.Data;
import com.gigaspaces.app.event_processing.common.EStatus;
import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.polling.Polling;

import javax.annotation.Resource;
import java.util.logging.Logger;

/**
 * Created by tal on 8/25/16.
 */
@EventDriven
@Polling(gigaSpace="gigaSpace")
public class SwitchTwo extends SwitchBase{
//    Logger log = Logger.getLogger(this.getClass().getName());

//    @Resource
//    private GigaSpace gigaSpace;

    @EventTemplate
    public SQLQuery<Data> findNewData() {
        System.out.println("Starting DataRequestEventContainer EventTemplate for Payment with false status.");


        SQLQuery<Data> paymentTemplate = new SQLQuery<Data>(Data.class, "status = ?" );
        paymentTemplate.setParameter(1, EStatus.Two);

        return paymentTemplate;
    }

    @SpaceDataEvent
    public Data processData(Data data) {

        System.out.println("Starting AuditDataEventContainer SpaceDataEvent.");
        // Set payment status
        data.setProcessed(true);
        data.setStatus(EStatus.Three);
        log.info(" ------ Finished step 2 : " + data);

        return data;
    }
}
