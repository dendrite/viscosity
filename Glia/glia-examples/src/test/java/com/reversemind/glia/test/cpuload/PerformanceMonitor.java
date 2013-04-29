package com.reversemind.glia.test.cpuload;


import sun.management.ManagementFactory;

import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;

/**
 * Created with IntelliJ IDEA.
 * User: dendrite
 * Date: 28.04.13
 * Time: 23:50
 * To change this template use File | Settings | File Templates.
 */
public class PerformanceMonitor {
    static long lastSystemTime      = 0;
    static long lastProcessCpuTime  = 0;
    public static int  availableProcessors = ManagementFactory.getOperatingSystemMXBean().getAvailableProcessors();
    public synchronized double getCpuUsage()
    {
        ManagementFactory.getThreadMXBean().setThreadCpuTimeEnabled(true);
        if ( lastSystemTime == 0 )
        {
            baselineCounters();
            //  return ;
        }

        long systemTime     = System.nanoTime();
        long processCpuTime = 0;

        processCpuTime = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
        double cpuUsage = (double) (processCpuTime - lastProcessCpuTime ) / ( systemTime - lastSystemTime )*100.0;

        lastSystemTime     = systemTime;
        lastProcessCpuTime = processCpuTime;

        return cpuUsage / availableProcessors;
    }

    static float javacpu = 0;
    static double uptime =0;

    public void _getJavaRuntime() {

        System.out.println("!!!" + ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage());

//        OperatingSystemMXBean osbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
//        RuntimeMXBean runbean = (RuntimeMXBean) ManagementFactory.getRuntimeMXBean();
//        int nCPUs = osbean.getAvailableProcessors();
//        long prevUpTime = runbean.getUptime();
//        long prevProcessCpuTime = osbean.getProcessCpuTime();
//        try {
//            Thread.sleep(1);
//        } catch (Exception e) { }
//
//        osbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
//        long upTime = runbean.getUptime();
//        long processCpuTime = osbean.getProcessCpuTime();
//        if (prevUpTime > 0L && upTime > prevUpTime) {
//            long elapsedCpu = processCpuTime - prevProcessCpuTime;
//            long elapsedTime = upTime - prevUpTime;
//            javacpu = Math.min(99F, elapsedCpu / (elapsedTime * 10000F * nCPUs));
//        } else {
//            javacpu = 0.001f;
//        }
//        uptime = runbean.getUptime();
    }

    private void baselineCounters()
    {
        lastSystemTime = System.nanoTime();

        //lastProcessCpuTime = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
        lastProcessCpuTime = 0;
        long[] ids = ManagementFactory.getThreadMXBean().getAllThreadIds();
        for(int i=0; i<ids.length; i++){
            if(ManagementFactory.getThreadMXBean().getThreadCpuTime(ids[i]) >= 0){
                lastProcessCpuTime += ManagementFactory.getThreadMXBean().getThreadCpuTime(ids[i]);
            }

        }
    }

        public static void main(String[] args) throws InterruptedException {
//        System.out.println(Runtime.getRuntime().availableProcessors());
//        System.exit(0);
        PerformanceMonitor monitor = new PerformanceMonitor();
        while(true){
            //Thread.sleep(1000);
            start();
            double usage = monitor.getCpuUsage();
            monitor._getJavaRuntime();
            System.out.println("Current CPU usage in pourcentage : " + usage + " - " + javacpu + " " + uptime);
        }
    }


//
//    static long lastSystemTime = 0;
//    static long lastProcessCpuTime = 0;
//    public static int availableProcessors = ManagementFactory.getOperatingSystemMXBean().getAvailableProcessors();
//
//    // public static int  availableProcessors = Runtime.getRuntime().availableProcessors();
//    public synchronized double getCpuUsage() {
//        ManagementFactory.getThreadMXBean().setThreadCpuTimeEnabled(true);
//        if (lastSystemTime == 0) {
//            baselineCounters();
//            //  return ;
//        }
//
//        long systemTime = System.nanoTime();
//        long processCpuTime = 0;
//
//        processCpuTime = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
//        double cpuUsage = (double) (processCpuTime - lastProcessCpuTime) / (systemTime - lastSystemTime) * 100.0;
//
//        lastSystemTime = systemTime;
//        lastProcessCpuTime = processCpuTime;
//
//        return cpuUsage / 4;// / availableProcessors;
//    }
//
//    private void baselineCounters() {
//        lastSystemTime = System.nanoTime();
//
//        lastProcessCpuTime = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
//    }
//
//    public static void main(String[] args) {
////        System.out.println(Runtime.getRuntime().availableProcessors());
////        System.exit(0);
//        PerformanceMonitor monitor = new PerformanceMonitor();
//        for (int i = 0; i < 10000; i++) {
//            start();
//            double usage = monitor.getCpuUsage();
//            if (usage != 0) System.out.println("Current CPU usage in pourcentage : " + usage);
//        }
//    }
//
    private static void start() {
        int count = 0;
        for (int i = 0; i < 10000000; i++) {
            count = (int) Math.random() * 100;
        }
    }
}

