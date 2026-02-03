package com.sun.service;

import com.bailian.scloud.common.util.redis.JedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class HotKeyService {
    private ConcurrentHashMap<String, AtomicLong> articleCollectMap = new ConcurrentHashMap<>();
    @Autowired
    private JedisUtils jedisUtils;

    /**
     *
     * @param articleId
     * @param type  =0 收藏   =1 取消收藏
     *
     */
    public void collectOrCancelCollectArticle(String articleId, int type){
        // 操作数据库的其他操作，暂不处理
        AtomicLong articleNum = articleCollectMap.computeIfAbsent(articleId, k -> new AtomicLong(0));
        if (type ==1) {
            // 我这块是想说如果articleNum <=0这块就不操作了,为了线程安全，这么写对么
            articleNum.updateAndGet(v -> Math.max(0, v - 1));

        } else if (type == 0) {
            articleNum.incrementAndGet();
        }
    }

    /**
     * 批量写入map里的值到redis里
     * 这里需要注意的是，本地缓存值，会丢，重启机器以后，需要用数据库的值，定期修复这个值。
     */
    public void writeMapToRedis(){
        // 遍历写入到redis里
        /**
         * 我感觉还是需要 增量处理比较好，起码数据不会差值比较大。 因为他这个可能不太准确。。。
         * 因为本地机器可能重启，你不能完全信任他这个数据，还是要用数据库
         * 用完之后把他清空掉
         */
        articleCollectMap.forEach((articleId,score)-> {
            jedisUtils.zadd("article:favorite", articleId,score.get());

        });
        articleCollectMap.clear();
    }

    /**
     * V2 改良版
     * V1 存在的问题：搭配writeMapToRedis 使用，1、jedisUtils.zadd 是全量覆盖，实际上map中存 增量数据比较合适，最终要db 来刷数据
     *
     * 2、articleCollectMap.clear();  并发会出问题，这会如果有别的线程过来增加了这个量，你全给人清了
     *
     * 3、取消收藏，因为map做到增量，所以他会变为0，你用上面的这个 articleNum.updateAndGet(v -> Math.max(0, v - 1));
     * 意思是取消了也不 减数量，这不是你最开始设想的，我想的是总量减到0就不减了
     * @param articleId
     * @param type
     */
    public void collectOrCancelCollectArticleV2(String articleId, int type){
        // 操作数据库的其他操作，暂不处理
        AtomicLong articleNum = articleCollectMap.computeIfAbsent(articleId, k -> new AtomicLong(0));
        if (type ==1) {
            // 取消收藏，这块map中可能会存负数
            articleNum.decrementAndGet();

        } else if (type == 0) {
            articleNum.incrementAndGet();
        }
    }

    /**
     * 定期把map中的增量数据写入到redis里去
     */
    public void writeMapToRedisV2(){
        // 遍历写入到redis里
        /**
         * 我感觉还是需要 增量处理比较好，起码数据不会差值比较大。 因为他这个可能不太准确。。。
         * 因为本地机器可能重启，你不能完全信任他这个数据，还是要用数据库
         * 用完之后把他清空掉
         */
        articleCollectMap.forEach((articleId,score)-> {
            // 塞入新值，返回旧值，是个原子操作，多线程下不会出问题,当线程2 此时操作了这个articleId，他只会下次再修改redis不会丢掉
            long delta = score.getAndSet(0);
            // 增量增加  因为我们的jedis里没实现这个方法，如果搞了会直接报错
//            jedisUtils.zIncreBy("article:favorite", articleId,delta);



//====================================================================分界线
            long delta1 = score.get();  // 这块是30
            // jedisUtils.zIncreBy("article:favorite", articleId,delta1);
            // 判断如果成功了，修正这个值啊,如果不成功，下次继续
            // 他说这个多线程条件下有问题，你想想可能会发生什么，这块可能变成42了，然后减去30，没问题啊
            // 我这里可能会有多个flush任务么？  他说多个flush任务会有问题，你不能说你现在写的业务上不会存在这样的问题
            // 正确的方案是，不管怎样我这个都很健壮




            score.addAndGet(-delta1);


        });
    }
}
