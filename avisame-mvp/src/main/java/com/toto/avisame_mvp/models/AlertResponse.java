package com.toto.avisame_mvp.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Octavio on 14/12/2016.
 */

public class AlertResponse implements Serializable {

    private List<Message> alerts;

    public List<Message> getAlerts() {
        return alerts;
    }

    public AlertResponse(List<Message> alerts) {
        this.alerts = alerts;
    }
}
