package com.sun.demo;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ProducerDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1. 配置生产者客户端参数
        Properties props = new Properties();
        // Kafka集群地址
        props.put("bootstrap.servers", "172.18.71.129:9092");
        // 消息ack模式: all表示消息被leader和follower都写入后才返回ack, -1表示只被leader写入就返回ack
        props.put("acks", "all");
        // 重试次数
        props.put("retries", 0);
        // 批量发送大小
        props.put("batch.size", 16384);
        // 发送延时，用于控制producer发送请求的延迟时间，可以提高吞吐量
        props.put("linger.ms", 1);
        // 缓冲区大小
        props.put("buffer.memory", 33554432);
        // key序列化类
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // value序列化类
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // 2. 创建生产者对象，传入配置参数
        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 100; i++) {
            // 3. 创建消息对象，指定topic、消息key和消息体value
            ProducerRecord<String, String> record = new ProducerRecord<>("today", "my" + i, "day" + i);
            // 4. 发送消息到Kafka集群，并获取返回结果
            RecordMetadata metadata = producer.send(record).get();
            // 打印结果，发送是否成功，以及发送到的分区和offset等信息
            System.out.printf("offset = %d, partition = %d%n", metadata.offset(), metadata.partition());
            System.out.println("record=" + metadata.topic()+record.key()+record.value());
        }
        // 5. 关闭生产者对象，释放资源
        producer.close();
    }
}

