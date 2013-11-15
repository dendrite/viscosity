package com.reversemind.glia.other.sigar;

import org.hyperic.sigar.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class SigarCPU {

    private static final Logger LOG = LoggerFactory.getLogger(SigarCPU.class);

    public static void main(String... args) throws SigarException, InterruptedException {
        Sigar sigar = new Sigar();

        Cpu cpu = sigar.getCpu();
        LOG.debug("CPU: " + cpu.toString());

        CpuPerc cpuPerc = sigar.getCpuPerc();
        LOG.debug(cpuPerc.toString());

        CpuInfo[] cpuInfo = sigar.getCpuInfoList();
        for (CpuInfo temp : cpuInfo) {
            LOG.debug(temp.toString());
        }

        while (true) {
            LOG.debug(sigar.getCpuPerc().getIdle());
            Thread.sleep(300);
        }
    }

}
