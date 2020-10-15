package com.initialyze.wknd.muzik.core.models.v1;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.initialyze.wknd.muzik.core.models.VideoSource;

public class VideoSourceImpl implements VideoSource {

    private String src;
    private String type;

    public VideoSourceImpl(Asset asset) {
        this(asset.getPath(), asset.getMimeType());
    }

    public VideoSourceImpl(Rendition rendition) {
        this(rendition.getPath(), rendition.getMimeType());
    }

    public VideoSourceImpl(String src, String type) {
        this.src = src;
        this.type = type;
    }

    @Override
    public String getSrc() {
        return src;
    }

    @Override
    public String getType() {
        return type;
    }
}
