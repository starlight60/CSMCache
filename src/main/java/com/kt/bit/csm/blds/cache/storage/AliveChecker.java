package com.kt.bit.csm.blds.cache.storage;

public class AliveChecker implements Runnable {

    private RedisCacheManager redisCacheManager;

    public AliveChecker(final RedisCacheManager redisCacheManager) {
        this.redisCacheManager = redisCacheManager;
    }

    private boolean lastStatus = true;

    public void run() {

        // Todo: Generate log
        System.out.println("Alive Checker started");

        while(true) {

            try {

                if(!redisCacheManager.ping().equalsIgnoreCase("PONG")) {
                    setOff();
                } else {
                    setOn();
                }

                Thread.sleep(1000);
            } catch (Exception e) {
                setOff();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    // Skip
                }
            }

        }
    }

    private void setOff() {
        if (lastStatus) {
            redisCacheManager.setServerStatus(false);
            lastStatus = false;

            // Todo: Generate log
            System.out.println("cannot to connect the redis server, redisStatus->off");
        }
    }

    private void setOn() {
        if (!lastStatus) {
            redisCacheManager.setServerStatus(true);
            lastStatus = true;

            // Todo: Generate log
            System.out.println("redis server back to normal, redisStatus->on");
        }
    }

}
