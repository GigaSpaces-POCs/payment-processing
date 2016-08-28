import com.gigaspaces.app.event_processing.common.Data;
import com.j_spaces.core.IJSpace;
import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.SpaceProxyConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.openspaces.core.space.cache.LocalViewSpaceConfigurer;

import java.util.concurrent.atomic.AtomicInteger;
/**
 * Created by tal on 8/24/16.
 */
public class Client {

    static AtomicInteger counter = new AtomicInteger(0);

    public static final String LOOKUPGROUPS = "XAP_LOOKUP_GROUPS";

    public void createView() {



//    LogUtils.log("trying to create proxy for local-view creation");

        UrlSpaceConfigurer space = new UrlSpaceConfigurer("jini://*/*/myGrid").lookupGroups("xap-11.0.0");
        GigaSpace gs = new GigaSpaceConfigurer(space).gigaSpace();//.create();
//    LogUtils.log("space proxy for local-view was created");
//    LogUtils.log("trying to create local-view");
//        IJSpace localJView = new LocalViewSpaceConfigurer(space).addViewQuery(new SQLQuery<Data>(Data.class, "processed = false")).create();
//    Logger.getLogger(com.gigaspaces.logger.Constants.LOGGER_LOCAL_VIEW).addHandler(handler);
//    Assert.assertTrue("local view should get ping ConnectException", sem.tryAcquire(2, TimeUnit.MINUTES));

        // Initialize remote space configurer:
//        SpaceProxyConfigurer urlConfigurer = new SpaceProxyConfigurer("myGrid").lookupGroups("xap-11.0.0");
// Initialize local view configurer
        LocalViewSpaceConfigurer localViewConfigurer = new LocalViewSpaceConfigurer(gs.getSpace())
                .batchSize(1000)
                .batchTimeout(100)
                .maxDisconnectionDuration(1000*60*60)
                .addProperty("space-config.engine.memory_usage.high_watermark_percentage", "90")
                .addProperty("space-config.engine.memory_usage.write_only_block_percentage", "88")
                .addProperty("space-config.engine.memory_usage.write_only_check_percentage", "86")
                .addProperty("space-config.engine.memory_usage.retry_count", "5")
                .addProperty("space-config.engine.memory_usage.explicit", "false")
                .addProperty("space-config.engine.memory_usage.retry_yield_time", "50")
                .addViewQuery(new SQLQuery<Data>(Data.class, "processed = false"))
//                .addViewQuery(new SQLQuery(com.example.Message2.class, "priority > 3"))
                ;

// Create local view:
        GigaSpace localView = new GigaSpaceConfigurer(localViewConfigurer).gigaSpace();
        long batchStartTime = System.currentTimeMillis();
        Data data;
        int amountOfObejctsToReadFromSpace = 1000;

        for (int i=0;i<amountOfObejctsToReadFromSpace;i++)
        {
            data = localView.read(new SQLQuery<Data>(Data.class, "processed = false and type = 4"));
        }
//        Data data = localView.read(new SQLQuery<Data>(Data.class, "processed = false and type = 4"));
        System.out.println("Local view read speed for " + amountOfObejctsToReadFromSpace + " objects is "
                + (System.currentTimeMillis() - batchStartTime) + " millis");



//Create remote connection to test read speed without local view
        System.out.println("Connecting to data grid");
        SpaceProxyConfigurer configurer = new SpaceProxyConfigurer("myGrid");
        final GigaSpace gigaSpace = new GigaSpaceConfigurer(configurer).create();
        System.out.println("Write (store) a few of account entries in the data grid:");

        batchStartTime = System.currentTimeMillis();
        for (int i = 0; i < amountOfObejctsToReadFromSpace; i++) {
            gigaSpace.read(new SQLQuery<Data>(Data.class, "processed = false and type = 4"));
        }
        System.out.println("Regular read speed for " + amountOfObejctsToReadFromSpace + " objects is "
                + (System.currentTimeMillis() - batchStartTime) + " millis");

    }
}
