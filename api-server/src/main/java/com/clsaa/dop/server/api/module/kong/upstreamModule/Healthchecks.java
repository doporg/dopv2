package com.clsaa.dop.server.api.module.kong.upstreamModule;

public class Healthchecks {
    private Active active;
    private Passive passive;
    private Long threshold;

    public Healthchecks() {
    }

    public Healthchecks(Active active, Passive passive, Long threshold) {
        this.active = active;
        this.passive = passive;
        this.threshold = threshold;
    }

    public Active getActive() {
        return active;
    }

    public void setActive(Active active) {
        this.active = active;
    }

    public Passive getPassive() {
        return passive;
    }

    public void setPassive(Passive passive) {
        this.passive = passive;
    }

    public Long getThreshold() {
        return threshold;
    }

    public void setThreshold(Long threshold) {
        this.threshold = threshold;
    }
}
