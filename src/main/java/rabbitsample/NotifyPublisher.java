package rabbitsample;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;

@Component
public class NotifyPublisher {

	@Autowired
	private RabbitTemplate rabbit;

	public void publish(Notify notify) {
		rabbit.convertAndSend("ex_notify_transfer", "r_notify_transfer", JSON.toJSONString(notify));
	}

}
