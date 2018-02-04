package me.xhy.java.rabbitmq.r1simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import me.xhy.java.rabbitmq.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQProvider {

    private static String QUEUE_NAME = "queue.simple";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String message = "Hello World!";
        System.out.println("send : " + message);
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));

        channel.close();
        connection.close();

    }
}
