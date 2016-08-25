import com.gigaspaces.app.event_processing.common.Data;
import com.j_spaces.core.IJSpace;
import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.SpaceProxyConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.openspaces.core.space.cache.LocalViewSpaceConfigurer;

/**
 * Created by tal on 8/24/16.
 */
public class Client {

    public void createView() {


//    LogUtils.log("trying to create proxy for local-view creation");

        UrlSpaceConfigurer space = new UrlSpaceConfigurer("jini://*/*/myGrid").lookupGroups("xap-11.0.0");
        GigaSpace gs = new GigaSpaceConfigurer(space).create();
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

        Data data = localView.read(new SQLQuery<Data>(Data.class, "processed = false and type = 4"));
        System.out.println("Local view read: " + data.toString());


    }
}
