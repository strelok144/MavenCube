package queue;

import com.rabbitmq.client.*;
import com.rabbitmq.client.AMQP.BasicProperties;
import cube.Cube;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
   private String message = "";

   public String getMessage(){
       return message;
   }
    private final static String QUEUE_NAME = "queueRequest";

    public void reading() throws IOException, TimeoutException, InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(
                QUEUE_NAME,
                false,
                false,
                false,
                null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            message = new String(delivery.getBody(), "UTF-8");
            System.out.println("Вычитано из очереди: '" + message + "'");
            if (message.equals("1")){
                try {
                    Request.publishingResult();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (TimeoutException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("Не корректный запрос");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}
