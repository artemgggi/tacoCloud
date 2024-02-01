package artemgggi.tacocloud.service;

import artemgggi.tacocloud.model.TacoOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsOrderMessagingService implements OrderMassagingService {

    private final JmsTemplate jms;

    @Autowired
    public JmsOrderMessagingService(JmsTemplate jms) {
        this.jms = jms;
    }

//    @Override
//    public void sendOrder(TacoOrder tacoOrder) {
//        jms.send(session -> session.createObjectMessage(tacoOrder));
//    }

    @Override
    public void sendOrder(TacoOrder tacoOrder) {
        jms.convertAndSend("tacocloud.order.queue", tacoOrder, message -> {
            message.setStringProperty("X_ORDER_SOURCE", "WEB");
            return message;
        });
    }
}
