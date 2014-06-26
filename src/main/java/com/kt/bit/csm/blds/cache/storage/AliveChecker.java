package com.kt.bit.csm.blds.cache.storage;

public class AliveChecker implements Runnable {

    private RedisCacheManager redisCacheManager;

    public AliveChecker(final RedisCacheManager redisCacheManager) {
        this.redisCacheManager = redisCacheManager;
    }

    @Override
    public void run() {

        // Todo: Generate log
        System.out.println("Alive Checker started");

        while(true) {

            try {

                if(!redisCacheManager.ping().equalsIgnoreCase("PONG")) {
                    redisCacheManager.setServerStatus(false);

                    // Todo: Generate log
                    System.out.println("cannot to connect the redis server, redisStatus->off");
                } else {
                    redisCacheManager.setServerStatus(true);

                    // Todo: Generate log
                    System.out.println("redis server back to normal, redisStatus->on");
                }

                Thread.sleep(500);
            } catch (Exception e) {
                // Skip
            }

        }
    }

}
