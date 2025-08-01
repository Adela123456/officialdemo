package com.sun.excutor;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class ContextAwareThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {
    @Override
    public void execute(Runnable task) {
        // 获取当前线程的请求上下文
        RequestAttributes context = RequestContextHolder.getRequestAttributes();
        super.execute(() -> {
            try {
                // 设置请求上下文到新线程
                RequestContextHolder.setRequestAttributes(context);
                task.run();
            } finally {
                // 清除线程上下文
                RequestContextHolder.resetRequestAttributes();
            }
        });
    }
}
