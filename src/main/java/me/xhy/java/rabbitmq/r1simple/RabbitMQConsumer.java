package me.xhy.java.rabbitmq.r1simple;

import com.rabbitmq.client.*;
import com.rabbitmq.client.Consumer;
import me.xhy.java.rabbitmq.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQConsumer {

    private static String QUEUE_NAME = "queue.simple";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("receive : " + message);
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);


    }
}
