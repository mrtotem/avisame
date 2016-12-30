package com.toto.avisame_mvp.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Octavio on 14/12/2016.
 */

public class ArrivalsResponse implements Serializable {

    private List<Message> arrivals;

    public List<Message> getArrivals() {
        return arrivals;
    }

    public ArrivalsResponse(List<Message> arrivals) {
        this.arrivals = arrivals;
    }
}
