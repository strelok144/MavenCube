package queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import cube.Cube;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Request {
    private final static String QUEUE_NAME = "queueResp";

    static public void publishingResult() throws InterruptedException, IOException, TimeoutException {

        Integer result = Cube.throwCube();  // бросаем кубик
        ConnectionFactory factory = new ConnectionFactory();   //создаём коннект к сервису очередей
        factory.setHost("localhost"); //передаём в него адрес сервиса
        try (
                Connection connection = factory.newConnection();   //создаём канал передачи сообщений
                Channel channel = connection.createChannel()) {
            channel.queueDeclare(   //создаём очередь
                    QUEUE_NAME,
                    false,
                    false,
                    false,
                    null);

            channel.basicPublish("", QUEUE_NAME, null, result.toString().getBytes());
            System.out.println("Записано в очередь: '" + result + "'");
        }
    }
}
