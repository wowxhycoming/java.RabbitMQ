package me.xhy.java.rabbitmq.r3FairDispatch;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import me.xhy.java.rabbitmq.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FairDispatchProvider {

    /*               |-C1
     *  P --- QUEUE -|
     *               |-C2
     */

    private static String QUEUE_NAME = "queue.worker";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        for (int i = 0; i < 50; i++) {
            String message = "Hello World ! " + i;
            System.out.println("send : " + message);
            channel.basicPublish("",QUEUE_NAME, null, message.getBytes("UTF-8"));
            TimeUnit.MILLISECONDS.sleep(500);
        }
        
    }
}
