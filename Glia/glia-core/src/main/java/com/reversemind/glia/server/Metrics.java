package com.reversemind.glia.server;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
public class Metrics implements Serializable {

    private Date startDate;
    private long requestsProcessed = 0L;
    private double averageTimePerRequest = 0.0d;
    private double processingTime = 0.0d;


    public Metrics(){
        this.startDate = new Date();
    }

    public void plusRequest(long deltaTimePerRequest) {
        synchronized (this) {
            requestsProcessed++;

            this.processingTime += deltaTimePerRequest;
            if (this.requestsProcessed > 0) {
                this.averageTimePerRequest = (this.processingTime / this.requestsProcessed);
            }

        }
    }

    public long elapsedTime(){
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

    @Override
    public String toString() {
        return "Metrics{" +
                "startDate=" + startDate +
                ", requestsProcessed=" + requestsProcessed +
                ", averageTimePerRequest=" + averageTimePerRequest +
                ", processingTime=" + processingTime +
                ", elapsedTime=" + this.elapsedTime() + " ms" +
                '}';
    }
}
