import com.gigaspaces.app.event_processing.common.Data;
import com.j_spaces.core.IJSpace;
import com.j_spaces.core.client.SQLQuery;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.openspaces.core.space.cache.LocalViewSpaceConfigurer;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.openspaces.core.space.SpaceProxyConfigurer;
/**
 * Created by tald on 21/08/16.
 */
public class Main {

    public static void main(String[] args) {


//    LogUtils.log("trying to create proxy for local-view creation");
//        IJSpace space = new UrlSpaceConfigurer("jini://*/*/space").create();
//    LogUtils.log("space proxy for local-view was created");
//    LogUtils.log("trying to create local-view");
//        new LocalViewSpaceConfigurer(space).addViewQuery(new SQLQuery<Data>(Data.class, "processed = false")).create();
//    Logger.getLogger(com.gigaspaces.logger.Constants.LOGGER_LOCAL_VIEW).addHandler(handler);
//    Assert.assertTrue("local view should get ping ConnectException", sem.tryAcquire(2, TimeUnit.MINUTES));
    }


}
