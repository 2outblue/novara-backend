package com.novaraspace.aspect;

import com.novaraspace.security.MinResponseTime;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
@Aspect
public class ResponseDelayAspect {

    @Around("@annotation(minResponseTime)")
    public Object enforceMinDuration(ProceedingJoinPoint joinPoint, MinResponseTime minResponseTime) throws Throwable {
        long start = System.nanoTime();
        try {
            return joinPoint.proceed();

        } finally {
            enforceDelay(start, minResponseTime.value());
        }
    }

    private void enforceDelay(long start, long minDurationMs) {
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;

        long rand = ThreadLocalRandom.current().nextLong(-30, 31);
        long target = minDurationMs + rand;

        long remainingMs = target - elapsedMs;
        if (remainingMs > 0) {
            try {
                Thread.sleep(remainingMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
