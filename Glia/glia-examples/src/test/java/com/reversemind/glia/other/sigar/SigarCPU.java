package com.reversemind.glia.other.sigar;

import org.hyperic.sigar.*;

/**
 *
 */
public class SigarCPU {

    public static void main(String... args) throws SigarException, InterruptedException {
        Sigar sigar = new Sigar();

        Cpu cpu = sigar.getCpu();
        System.out.println("CPU: " + cpu.toString());

        CpuPerc cpuPerc = sigar.getCpuPerc();
        System.out.println(cpuPerc.toString());

        CpuInfo[] cpuInfo = sigar.getCpuInfoList();
        for (CpuInfo temp : cpuInfo) {
            System.out.println(temp.toString());
        }

        while(true){
            System.out.println(sigar.getCpuPerc().getIdle());
            Thread.sleep(300);
        }
    }

}
