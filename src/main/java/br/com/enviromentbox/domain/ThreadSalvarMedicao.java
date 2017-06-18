package br.com.enviromentbox.domain;

import br.com.enviromentbox.service.AlertaDeviceService;
import br.com.enviromentbox.service.MedicaoService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

/**
 * Created by Kloss Teles on 20/05/2017.
 */
public class ThreadSalvarMedicao extends Thread {
    private final static String QUEUE_NAME = "EnviromentBox";
    com.rabbitmq.client.Channel channel;
    MedicaoService medicaoService;
    AlertaDeviceService alertaDeviceService;

    public ThreadSalvarMedicao(Channel channel, MedicaoService medicaoService, AlertaDeviceService alertaDeviceService) {
        this.channel = channel;
        this.medicaoService = medicaoService;
        this.alertaDeviceService = alertaDeviceService;
    }

    @Override
    public void run() {
        String uri = System.getenv("CLOUDAMQP_URL");
        QueueingConsumer consumer = new QueueingConsumer(channel);
        try {
            channel.basicConsume(QUEUE_NAME, true, consumer);
            while (true) {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody());
                System.out.println(" [x] Received '" + message + "'");
                if (medicaoService != null) {
                    medicaoService.salvarStr(message);
                } else {
                    System.out.println("Service null");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
