package com.initialyze.wknd.muzik.core.models;

public interface VideoSource {

    default String getSrc() {
        throw new UnsupportedOperationException();
    }

    default String getType() {
        throw new UnsupportedOperationException();
    }

}
