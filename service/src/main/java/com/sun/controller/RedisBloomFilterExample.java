package com.sun.controller;

import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedisBloomFilterExample {
    public static void main(String[] args) {
        // 配置 Redisson 客户端
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");

        // 创建 Redisson 客户端
        RedissonClient redisson = Redisson.create(config);

        // 获取布隆过滤器实例
        RBloomFilter<String> bloomFilter = redisson.getBloomFilter("myBloomFilter");

        // 初始化布隆过滤器，预计插入 1000 个元素，误判率为 0.01
        bloomFilter.tryInit(1000, 0.01);

        // 向布隆过滤器中添加元素
        String[] elements = {"apple", "banana", "cherry"};
        for (String element : elements) {
            bloomFilter.add(element);
        }

        // 检查元素是否存在于布隆过滤器中
        String[] checkElements = {"apple", "date", "banana"};
        for (String element : checkElements) {
            boolean exists = bloomFilter.contains(element);
            if (exists) {
                System.out.println("元素 " + element + " 可能存在于布隆过滤器中。");
            } else {
                System.out.println("元素 " + element + " 肯定不存在于布隆过滤器中。");
            }
        }

        // 关闭 Redisson 客户端
        redisson.shutdown();
    }
}