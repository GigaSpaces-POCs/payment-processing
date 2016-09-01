package com.gigaspaces.app.event_processing.processor;

import javax.annotation.Resource;

import com.gigaspaces.app.event_processing.common.Data;
import com.gigaspaces.app.event_processing.common.EStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.core.context.GigaSpaceLateContext;
import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.polling.Polling;

import com.j_spaces.core.client.SQLQuery;

import java.util.logging.Logger;

/**
 * Created by tal on 8/25/16.
 */
@EventDriven
@Polling(gigaSpace="gigaSpace")
public class SwitchOne extends SwitchBase{
//    Logger log = Logger.getLogger(this.getClass().getName());





    @EventTemplate
    public SQLQuery<Data> findNewData() {
        System.out.println("Starting DataRequestEventContainer EventTemplate for Payment with false status.");


        SQLQuery<Data> paymentTemplate = new SQLQuery<Data>(Data.class, "status = ?" );
        paymentTemplate.setParameter(1, EStatus.One);

        return paymentTemplate;
    }

    @SpaceDataEvent
    public Data processData(Data data) {

        System.out.println("Starting AuditDataEventContainer SpaceDataEvent.");
        // Set payment status
        data.setProcessed(true);
        data.setStatus(EStatus.Two);
        log.info(" ------ Finished step 1 : " + data);

        return data;
    }


}
