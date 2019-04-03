package rabbitsample;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;

@Component
public class NotifyConsumer {

	// @RabbitListener(queues = "q_notify", containerFactory = "notifyListenerContainerFactory")
	public void onNotify(String msg) {
		System.out.println(Thread.currentThread().getName());
		var notify = JSON.parseObject(msg, Notify.class);
		System.out.println(notify);
	}

}
