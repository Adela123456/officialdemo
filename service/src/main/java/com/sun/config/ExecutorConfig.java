package com.sun.config;


import com.sun.excutor.ContextAwareThreadPoolTaskExecutor;
import com.sun.util.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
@Slf4j
public class ExecutorConfig {

    /**
     * Set the ThreadPoolExecutor's core pool size.
     */
    private int corePoolSize = 20;
    /**
     * Set the ThreadPoolExecutor's maximum pool size.
     */
    private int maxPoolSize = 200;
    /**
     * Set the capacity for the ThreadPoolExecutor's BlockingQueue.
     */
    private int queueCapacity = 100;

    @Bean(name = "taskExecutor")
    public TaskExecutor taskAsyncPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("Executor-");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务  
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行  -
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Bean(name = "goodsDetailTaskExecutor")
    public Executor goodsDetailExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("goodsDetailTaskExecutor-");
        return executor;
    }

    @Bean(name = "templateExecutor")
    public Executor templateExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("templateExecutor-");
        return executor;
    }

    @Bean(name = "testExecutor")
    public Executor getAsyncExecutor() {
        log.info("进入线程池,thread:{}", Thread.currentThread().getName());
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setTaskDecorator((runnable)->{
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            log.info("线程池装饰器thread:{}",Thread.currentThread().getName());

            return () -> {
                try {
                    Map<String, String> map = new HashMap<>();
                    HttpServletRequest request = servletRequestAttributes.getRequest();
                    map.put("channel", request.getHeader("channel"));
                    RequestContext.setHeaders(map);//设置全局的header
                    log.info("线程池重写方法，thread:{}", Thread.currentThread().getName());
                    runnable.run();
                } finally {
                    RequestContext.clear();
                }
            };
        });
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("Async-");
        log.info("线程池初始化，start======");
        executor.initialize();
        log.info("线程池初始化，end======");
        return executor;
    }

    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor() {
        ContextAwareThreadPoolTaskExecutor executor = new ContextAwareThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(50);
        executor.initialize();
        return executor;
    }

}
