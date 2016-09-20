package com.hunter.lib.util;

import android.content.Context;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

public class FrescoUtils {

    private static final String IMAGE_CACHE = "img_cache";

    private static final int MAX_CACHE_SIZE      = 100 * 1024 * 1024;
    private static final int LOW_DISK_SPACE      = 50 * 1024 * 1024;
    private static final int VERY_LOW_DISK_SPACE = 10 * 1024 * 1024;

    private static final int DEFAULT_MAX_MEMORY_SIZE = (int) (Runtime.getRuntime().maxMemory() / 8);

    private static FrescoUtils helper;

    private FrescoUtils() {

    }

    public static FrescoUtils getInstance() {
        if (helper == null) {
            helper = new FrescoUtils();
        }
        return helper;
    }

    public void init(Context context) {
        Fresco.initialize(context, getImagePipelineConfig(context));
    }

    public ImagePipelineConfig getImagePipelineConfig(Context context) {
        ImagePipelineConfig.Builder builder = ImagePipelineConfig.newBuilder(context);

        final MemoryCacheParams params = new MemoryCacheParams(DEFAULT_MAX_MEMORY_SIZE,
                                                               Integer.MAX_VALUE,
                                                               DEFAULT_MAX_MEMORY_SIZE,
                                                               Integer.MAX_VALUE,
                                                               Integer.MAX_VALUE);
        Supplier<MemoryCacheParams> encodedMemoryCacheParamsSupplier = new Supplier<MemoryCacheParams>() {
            @Override
            public MemoryCacheParams get() {
                return params;
            }
        };
        builder.setEncodedMemoryCacheParamsSupplier(encodedMemoryCacheParamsSupplier);

        DiskCacheConfig mainDiskCacheConfig = DiskCacheConfig.newBuilder(context)
                                                             .setBaseDirectoryPath(context.getExternalCacheDir())
                                                             .setBaseDirectoryName(IMAGE_CACHE)
                                                             .setMaxCacheSize(MAX_CACHE_SIZE)
                                                             .setMaxCacheSizeOnLowDiskSpace(LOW_DISK_SPACE)
                                                             .setMaxCacheSizeOnVeryLowDiskSpace(VERY_LOW_DISK_SPACE)
                                                             .build();
        builder.setMainDiskCacheConfig(mainDiskCacheConfig);

        return builder.build();
    }

}

