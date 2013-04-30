package com.reversemind.glia.server;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.io.Serializable;
import java.util.Date;

/**
 * Measurable parameters for GliaServer
 *
 */
public class Metrics implements Serializable {

    private Date startDate;
    private long requestsProcessed = 0L;
    private double averageTimePerRequest = 0.0d;
    private double processingTime = 0.0d;
    private double cpuIdle;

    private Sigar sigar;

    public Metrics() {
        this.startDate = new Date();
        this.sigar = new Sigar();
    }

    // TODO need to rename method
    public void plusRequest(long deltaTimePerRequest) {
        synchronized (this) {
            requestsProcessed++;

            this.processingTime += deltaTimePerRequest;
            if (this.requestsProcessed > 0) {
                this.averageTimePerRequest = (this.processingTime / this.requestsProcessed);
            }

            // just update CPU load
            this.getCpuIdle();
        }
    }

    public long elapsedTime() {
        return System.currentTimeMillis() - this.startDate.getTime();
    }

    public double getAverageTimePerRequest() {
        return averageTimePerRequest;
    }

    public void setAverageTimePerRequest(double averageTimePerRequest) {
        this.averageTimePerRequest = averageTimePerRequest;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public long getRequestsProcessed() {
        return requestsProcessed;
    }

    public double getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(double processingTime) {
        this.processingTime = processingTime;
    }

    public double getCpuIdle() {
        try {
            this.cpuIdle = sigar.getCpuPerc().getIdle();
        } catch (SigarException e) {
        }
        return cpuIdle;
    }

    public void setCpuIdle(double cpuIdle) {
        this.cpuIdle = cpuIdle;
    }

    @Override
    public String toString() {
        return "Metrics{" +
                "startDate=" + startDate +
                ", requestsProcessed=" + requestsProcessed +
                ", averageTimePerRequest=" + averageTimePerRequest +
                ", processingTime=" + processingTime +
                ", cpuIdle=" + cpuIdle + "% " +
                '}';
    }
}
