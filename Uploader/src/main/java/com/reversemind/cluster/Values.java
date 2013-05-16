package com.reversemind.cluster;

import java.io.Serializable;
import java.util.Date;

/**
 * Date: 5/16/13
 * Time: 11:00 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class Values implements Serializable {

    long id;

    long total;

    long updates;
    long links;
    long replies;
    long retweets;

    Date startDate;
    Date stopDate;

    long deltaDate;

    public Values(long total, long updates, long links, long replies, long retweets, Date startDate, Date stopDate, long deltaDate, long id) {
        this.total = total;
        this.updates = updates;
        this.links = links;
        this.replies = replies;
        this.retweets = retweets;
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.deltaDate = deltaDate;

        this.id = id;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getUpdates() {
        return updates;
    }

    public void setUpdates(long updates) {
        this.updates = updates;
    }

    public long getLinks() {
        return links;
    }

    public void setLinks(long links) {
        this.links = links;
    }

    public long getReplies() {
        return replies;
    }

    public void setReplies(long replies) {
        this.replies = replies;
    }

    public long getRetweets() {
        return retweets;
    }

    public void setRetweets(long retweets) {
        this.retweets = retweets;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    public long getDeltaDate() {
        return deltaDate;
    }

    public void setDeltaDate(long deltaDate) {
        this.deltaDate = deltaDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String print2(){
        return
                //id + "|" +

//                total + "," +
//
//                updates + "," +
//                links + "," +
//                replies + "," +
//                retweets + "," +

                (updates*1.0d/total) + "," +
                (links*1.0d/total) + "," +
                (replies*1.0d/total) + "," +
                (retweets*1.0d/total);
                //        + "," +

                //deltaDate;
    }

    public String print(){
        return  total + "|" +
                updates + "|" +
                links + "|" +
                replies + "|" +
                retweets + "|" +
                deltaDate;
    }

    @Override
    public String toString() {
        return "Values{" +
                "total=" + total +
                ", updates=" + updates +
                ", links=" + links +
                ", replies=" + replies +
                ", retweets=" + retweets +
                ", startDate=" + startDate +
                ", stopDate=" + stopDate +
                ", deltaDate=" + deltaDate +
                '}';
    }
}
