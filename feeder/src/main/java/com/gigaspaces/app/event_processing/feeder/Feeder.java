package com.gigaspaces.app.event_processing.feeder;

import com.gigaspaces.app.event_processing.common.Data;

import com.gigaspaces.client.ChangeSet;
import com.gigaspaces.query.IdQuery;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.SpaceInterruptedException;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.SpaceInterruptedException;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.core.space.SpaceProxyConfigurer;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.UUID;
import java.util.Random;

/**
 * A feeder bean starts a scheduled task that writes a new Data objects to the space (in an
 * unprocessed state).
 *
 * <p>The space is injected into this bean using OpenSpaces support for @GigaSpaceContext
 * annotation.
 *
 * <p>The scheduling uses the java.util.concurrent Scheduled Executor Service. It is started and
 * stopped based on Spring life cycle events.
 *
 * @author kimchy
 */
//public class Feeder implements InitializingBean, DisposableBean {
    public class Feeder {


        Logger log = Logger.getLogger(this.getClass().getName());

    private ScheduledExecutorService executorService;

    private ScheduledFuture<?> sf;

    private long numberOfTypes = 10;

    private long defaultDelay = 2000;

    private FeederTask feederTask;

    @GigaSpaceContext
    private GigaSpace gigaSpace;

    /**
     * Sets the number of types that will be used to set {@link org.openspaces.example.data.common.Data#setType(Long)}.
     *
     * <p>The type is used as the routing index for partitioned space. This will affect the
     * distribution of Data objects over a partitioned space.
     */
    public void setNumberOfTypes(long numberOfTypes) {
        this.numberOfTypes = numberOfTypes;
    }

    public void setDefaultDelay(long defaultDelay) {
        this.defaultDelay = defaultDelay;
    }

    public void afterPropertiesSet() throws Exception {
        log.info("--- STARTING FEEDER WITH CYCLE [" + defaultDelay + "]");
        executorService = Executors.newScheduledThreadPool(1);
        feederTask = new FeederTask();
        sf = executorService.scheduleAtFixedRate(feederTask, defaultDelay, defaultDelay,
                TimeUnit.MILLISECONDS);
    }

    public void destroy() throws Exception {
        sf.cancel(false);
        sf = null;
        executorService.shutdown();
    }

    public long getFeedCount() {
        return feederTask.getCounter();
    }


    public class FeederTask implements Runnable {

        private long counter = 1;

        public void run() {
            try {
                long time = System.currentTimeMillis();
                Data data = new Data((counter++ % numberOfTypes), "FEEDER " + Long.toString(time));
                gigaSpace.write(data);
                log.info("--- FEEDER WROTE " + data);
            } catch (SpaceInterruptedException e) {
                // ignore, we are being shutdown
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public long getCounter() {
            return counter;
        }
    }

//    public static void Main(String[] args){
public void MainFeed(){

    System.out.println("Connecting to data grid");
        SpaceProxyConfigurer configurer = new SpaceProxyConfigurer("myGrid");
        configurer.lookupGroups("xap-11.0.0");
        final GigaSpace gigaSpace = new GigaSpaceConfigurer(configurer).create();

        System.out.println("Write (store) a few of account entries in the data grid:");
        Random rand = new Random(50);
        //rand.nextInt()
        for (int i = 0; i < 50; i++) {
//            Data account = new Data(i, UUID.randomUUID().toString(), rand.nextInt());
            Data account = new Data((long)rand.nextInt(), UUID.randomUUID().toString());
//            Data account = new Data((long)4, "test");


            gigaSpace.write(account);
//    		System.out.println("Result: " + person);
        }

            Data account = new Data((long)4, "tal");
            gigaSpace.write(account);

        System.out.println("For POC purposes we add the cards to the account using changeset");
        rand = new Random(3);
//        for (int i = 0; i < 50000; i++) {
//            IdQuery<Data> idQuery = new IdQuery<Data>(Data.class, i);
//            gigaSpace.change(idQuery, new ChangeSet().addToCollection("cardIds", getCards(i)));
//            Account account = new Account(i, UUID.randomUUID().toString(), rand.nextInt());
//            gigaSpace.write(account, 20000);20000
//    		System.out.println("Result: " + person);
        }
        //rand.nextInt()



}
