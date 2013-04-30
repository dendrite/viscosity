package com.reversemind.glia.other.sigar;

import org.hyperic.sigar.*;

/**
 * Created with IntelliJ IDEA.
 * User: dendrite
 * Date: 30.04.13
 * Time: 19:18
 * To change this template use File | Settings | File Templates.
 */
public class Sigar2 {

    public static void main(String... args) throws Exception {
        Sigar sigar = new Sigar();

        // enabling logging in the native Sigar code (log4j is required)
        sigar.enableLogging(true);

        Mem mem = sigar.getMem();
        System.out.println(mem.toString());

        Swap swap = sigar.getSwap();
        System.out.println(swap.toString());
        System.out.println("Total swap in readable format: " + Sigar.formatSize(swap.getTotal()));

        Cpu cpu = sigar.getCpu();
        System.out.println("CPU: " + cpu.toString());

        CpuPerc cpuPerc = sigar.getCpuPerc();
        System.out.println(cpuPerc.toString());

        CpuInfo[] cpuInfo = sigar.getCpuInfoList();
        for (CpuInfo temp : cpuInfo) {
            System.out.println(temp.toString());
        }

        ResourceLimit rLimit = sigar.getResourceLimit();
        // a range of values includes core, cpu, mem, opened files etc and depends on platform
        System.out.println("ResourceLimit: " + rLimit.toString());

        System.out.println("System uptime in seconds: " + sigar.getUptime().toString());

        long pid = sigar.getPid();
        // also it's possible to get a list of pids

        ProcState pState = sigar.getProcState(pid);
        System.out.println(pState.toString());
        // also you can get proc mem, state, time, cpu, credentials, descriptors,
        // current working directory, arguments, environment etc by PID

        int port = 12080;
        long pidByPort = sigar.getProcPort(NetFlags.CONN_TCP, port);
        System.out.println("Name of the process which uses port " + port + ": "
                + sigar.getProcState(pidByPort).getName());

        FileSystem[] fileSystems = sigar.getFileSystemList();
        FileSystemUsage fsu = sigar.getFileSystemUsage(fileSystems[1].getDevName());
        System.out.println("File system usage: " + fsu.toString());
        //you can find disk reads/writes, total/avail/free size, disk queue etc
    }

}
