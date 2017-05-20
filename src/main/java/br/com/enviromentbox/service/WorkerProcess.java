package br.com.enviromentbox.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * Created by Kloss Teles on 05/05/2017.
 */
@Component
public class WorkerProcess {
    private final static String QUEUE_NAME = "EnviromentBox";
    @Autowired
    MedicaoService medicaoService;

    @PostConstruct
    public void initIt() throws Exception {

        String uri = System.getenv("CLOUDAMQP_URL");
        uri = "amqp://eacrpkvt:UYaVBR5qhckx8y0katPBcVTB7tJQxcYR@clam.rmq.cloudamqp.com/eacrpkvt";
        System.out.println("Iniciando conexão com ClouAMQ");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);
        factory.setRequestedHeartbeat(30);
        factory.setConnectionTimeout(30000);
        Connection connection = factory.newConnection();
        System.out.println("Conexão Iniciada");
        Channel channel = connection.createChannel();

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, true, consumer);
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
            if (medicaoService != null) {
//                message = "id_device:1151;id_sensor:1202;valor_medicao:2121";
                medicaoService.salvarStr(message);
            } else {
                System.out.println("Service null");
            }
        }
    }
}
