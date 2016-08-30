import com.gigaspaces.app.event_processing.common.Data;

import com.gigaspaces.app.event_processing.feeder.Feeder;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


/**
 * A simple unit test that verifies the Processor processData method actually processes the Data
 * object.
 */
public class FeederTest {

    @Test
    public void verifyFeeder() {
        int objectsPerSecond = 10;
        int totalObjectsToWriteTogrid = 20;
        Feeder feedTester = new Feeder();
        feedTester.MainFeed(objectsPerSecond,totalObjectsToWriteTogrid);
    }



}
