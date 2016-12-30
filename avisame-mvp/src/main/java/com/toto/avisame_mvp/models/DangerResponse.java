package com.toto.avisame_mvp.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Octavio on 14/12/2016.
 */

public class DangerResponse implements Serializable {

    private List<Message> dangers;

    public List<Message> getDangers() {
        return dangers;
    }

    public DangerResponse(List<Message> dangers) {
        this.dangers = dangers;
    }
}
