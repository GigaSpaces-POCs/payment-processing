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
@Polling(gigaSpace="localView")
public class SwitchThree extends SwitchBase{
//    Logger log = Logger.getLogger(this.getClass().getName());

//    @Resource
//    private GigaSpace gigaSpace;

    @EventTemplate
    public SQLQuery<Data> findNewData() {
        System.out.println("Starting DataRequestEventContainer EventTemplate for Payment with false status.");


        SQLQuery<Data> paymentTemplate = new SQLQuery<Data>(Data.class, "status = ?" );
        paymentTemplate.setParameter(1, EStatus.Three);

        return paymentTemplate;
    }

    @SpaceDataEvent
    public Data processData(Data data) {

        System.out.println("Starting AuditDataEventContainer SpaceDataEvent.");
        // Set payment status
        data.setProcessed(true);
        data.setStatus(EStatus.Four);
//        try {
////            data.finishWorkFlow();
//            data.setBatchTotalTime(System.currentTimeMillis()-data.getBatchStartTime());
//        }catch (Exception e){
//            e.printStackTrace();
//            log.info(" ------ step 3 error: " + e.getMessage());
//        }
        log.info(" ------ Finished step 3 : " + data);

        return data;
    }


}
