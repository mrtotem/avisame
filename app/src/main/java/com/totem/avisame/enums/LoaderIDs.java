package com.totem.avisame.enums;

public enum LoaderIDs {

    POST_LOGIN(0),
    POST_ARRIVED(1),
    PUT_UPDATE_USER(2),
    POST_REGISTER(3);

    private final int id;

    LoaderIDs(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

