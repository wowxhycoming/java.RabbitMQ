ack = true: Round-robin 转发   消费者被杀死，消息会丢失 
ack = false: 消息应答 ，为了保证消息永远不会丢失，RabbitMQ支持消息应答（message acknowledgments）。 
消费者发送应答给RabbitMQ，告诉它信息已经被接收和处理，然后RabbitMQ可以自由的进行信息删除。 
如果消费者被杀死而没有发送应答，RabbitMQ会认为该信息没有被完全的处理，然后将会重新转发给别的消费者。 
通过这种方式，你可以确认信息不会被丢失，即使消者偶尔被杀死。 
消费者需要耗费特别特别长的时间是允许的。 
