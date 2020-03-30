package com.org.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class StatsInternal {

    @JsonIgnore
    private Long timestamp;
    private Double sum;
    private Double min;
    private Double max;
    private Double avg;
    private Long count;


    public StatsInternal() {
    }

    public StatsInternal(Double sum, Double min, Double max, Double avg, Long count) {
        this.sum = sum;
        this.min = min;
        this.max = max;
        this.avg = avg;
        this.count = count;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

        public static StatsInternal create(StatsInternal stats) {
            StatsInternal statistics = new StatsInternal();
            statistics.setSum(stats.getSum());
            statistics.setMax(stats.getMax());
            statistics.setMin(stats.getMin());
            statistics.setCount(stats.getCount());
        return statistics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatsInternal that = (StatsInternal) o;
        return Objects.equals(sum, that.sum) &&
                Objects.equals(avg, that.avg) &&
                Objects.equals(max, that.max) &&
                Objects.equals(min, that.min) &&
                Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {

        return Objects.hash(sum, avg, max, min, count);
    }



}
