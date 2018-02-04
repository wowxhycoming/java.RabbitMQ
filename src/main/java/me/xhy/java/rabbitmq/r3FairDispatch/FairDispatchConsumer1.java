package me.xhy.java.rabbitmq.r3FairDispatch;

import com.rabbitmq.client.*;
import me.xhy.java.rabbitmq.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FairDispatchConsumer1 {

    private static String QUEUE_NAME="queue.worker";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("[1]receive : " + message);

                //发送应答
                channel.basicAck(envelope.getDeliveryTag(), false);

                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // 打开应答模式
        boolean autoAck = false;

        //公平转发  设置最大服务转发消息数量    只有在消费者空闲的时候会发送下一条信息。
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        channel.basicConsume(QUEUE_NAME, autoAck, consumer);

    }
}
