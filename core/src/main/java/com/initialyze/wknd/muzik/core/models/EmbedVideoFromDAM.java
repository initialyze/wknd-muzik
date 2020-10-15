package com.initialyze.wknd.muzik.core.models;

import com.adobe.cq.wcm.core.components.models.Component;
import org.osgi.annotation.versioning.ConsumerType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ConsumerType
public interface EmbedVideoFromDAM extends Component {

    List<String> ALLOWED_MIME_TYPES = Collections.unmodifiableList(Arrays.asList("video/3gpp", "video/x-flv", "video/mp4", "video/ogg", "video/webm"));

    String PN_FILE_REFERENCE = "videoPath";
    String PN_WIDTH = "width";
    String PN_HEIGHT = "height";
    String PN_AUTOPLAY = "autoplay";
    String PN_LOOP = "loop";
    String PN_HIDE_CONTROLS = "hideControls";
    String PN_POSTER_IMAGE_PATH = "posterImagePath";

    default List<VideoSource> getSources() {
        throw new UnsupportedOperationException();
    }

    default int getWidth() { throw new UnsupportedOperationException(); }

    default int getHeight() { throw new UnsupportedOperationException(); }

    default boolean isAutoplayEnabled() {
        throw new UnsupportedOperationException();
    }

    default boolean isLoopEnabled() {
        throw new UnsupportedOperationException();
    }

    default boolean hideControls() {
        throw new UnsupportedOperationException();
    }

    default String getPosterImage() {
        throw new UnsupportedOperationException();
    }

}
