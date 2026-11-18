package org.pipepipe.plugin.edutube;

import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.plugin.PipePipePlugin;
import org.schabi.newpipe.extractor.plugin.PluginContext;

import javax.annotation.Nonnull;

/**
 * Entry point for EduTube Plugin, referenced by {@code entryClass} in plugin.json.
 */
public final class EduTubePlugin implements PipePipePlugin {

    @Nonnull
    @Override
    public StreamingService createService(final int serviceId, @Nonnull final PluginContext context) {
        return new EduTubeService(serviceId, context.getPreferences());
    }
}
