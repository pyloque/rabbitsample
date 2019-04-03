package rabbitsample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class NotifyTest {

    @Autowired
    private NotifyPublisher publisher;

    @Test
    public void testNotify() {
        for (int i = 0; i < 100000000; i++) {
            publisher.publish(new Notify("test", "test-title", "test-content"));
            System.out.println("publish:" + i);
        }
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

}
