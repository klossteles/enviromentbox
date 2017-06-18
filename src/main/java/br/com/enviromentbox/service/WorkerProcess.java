package br.com.enviromentbox.service;

import br.com.enviromentbox.domain.ThreadSalvarMedicao;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
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

    @Autowired
    AlertaDeviceService alertaDeviceService;

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

        ThreadSalvarMedicao teste = new ThreadSalvarMedicao(channel, medicaoService, alertaDeviceService);
        teste.start();
    }
}
