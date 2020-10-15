package com.initialyze.wknd.muzik.core.models.v1;


import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.initialyze.wknd.muzik.core.models.EmbedVideoFromDAM;
import com.initialyze.wknd.muzik.core.models.VideoSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class, adapters = {EmbedVideoFromDAM.class, ComponentExporter.class}, resourceType = EmbedVideoFromDAMImpl.RESOURCE_TYPE_V1)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class EmbedVideoFromDAMImpl implements EmbedVideoFromDAM {

    public final static String RESOURCE_TYPE_V1 = "wknd-muzik/components/embed/videofromdam";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmbedVideoFromDAMImpl.class);

    @Self
    protected SlingHttpServletRequest request;

    @Inject
    private Resource resource;

    @SlingObject
    @JsonIgnore
    private ResourceResolver resourceResolver;

    @ValueMapValue(name = EmbedVideoFromDAM.PN_HEIGHT, injectionStrategy = InjectionStrategy.OPTIONAL)
    private int height;

    @ValueMapValue(name = EmbedVideoFromDAM.PN_WIDTH, injectionStrategy = InjectionStrategy.OPTIONAL)
    private int width;

    @ValueMapValue(name = EmbedVideoFromDAM.PN_FILE_REFERENCE, injectionStrategy = InjectionStrategy.OPTIONAL)
    private String videoPath;

    @ValueMapValue(name = EmbedVideoFromDAM.PN_AUTOPLAY, injectionStrategy = InjectionStrategy.OPTIONAL)
    private boolean autoplayEnabled;

    @ValueMapValue(name = EmbedVideoFromDAM.PN_LOOP, injectionStrategy = InjectionStrategy.OPTIONAL)
    private boolean loopEnabled;

    @ValueMapValue(name = EmbedVideoFromDAM.PN_HIDE_CONTROLS, injectionStrategy = InjectionStrategy.OPTIONAL)
    private boolean hideControls;

    @ValueMapValue(name = EmbedVideoFromDAM.PN_POSTER_IMAGE_PATH, injectionStrategy = InjectionStrategy.OPTIONAL)
    private String posterImage;

    private List<VideoSource> sources = new ArrayList<>();

    @PostConstruct
    protected void initModel() {

        if (StringUtils.isBlank(videoPath)) {
            return;
        }

        Resource assetResource = resourceResolver.getResource(videoPath);
        if (assetResource == null) {
            LOGGER.error("The asset path '{}' used by video component '{}' does not resolve to a resource.", videoPath, resource.getPath());
            return;
        }

        Asset videoAsset = assetResource.adaptTo(Asset.class);
        if (videoAsset == null) {
            LOGGER.error("Could not adapt resource '{}' used by video component '{}' to an asset.", videoPath, resource.getPath());
            return;
        }

        String mimeType = videoAsset.getMimeType();
        if (StringUtils.isBlank(mimeType) || !ALLOWED_MIME_TYPES.contains(mimeType)) {
            LOGGER.error("Mime type '{}' of video asset '{}' is not a recognized video mime type.", mimeType, videoPath);
            return;
        }

        sources.add(new VideoSourceImpl(videoAsset));
        List<Rendition> renditions = videoAsset.getRenditions();
        for (Rendition rendition : renditions) {
            String renditionMimeType = rendition.getMimeType();
            if (StringUtils.isNotBlank(renditionMimeType) &&
                    !renditionMimeType.equals(mimeType) &&
                    ALLOWED_MIME_TYPES.contains(renditionMimeType)) {
                sources.add(new VideoSourceImpl(rendition));
            }
        }


        if(StringUtils.isBlank(posterImage)){
            Rendition imagePreviewRendition = videoAsset.getImagePreviewRendition();
            if (imagePreviewRendition == null || imagePreviewRendition.equals(videoAsset.getOriginal())) {
                LOGGER.warn("No suitable image preview rendition exists for video '{}'", videoPath);
            }
        }

    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public List<VideoSource> getSources() {
        return sources;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public boolean isAutoplayEnabled() {
        return autoplayEnabled;
    }

    @Override
    public boolean isLoopEnabled() {
        return loopEnabled;
    }

    @Override
    public boolean hideControls() {
        return hideControls;
    }

    @Override
    public String getPosterImage() {
        return posterImage;
    }

}