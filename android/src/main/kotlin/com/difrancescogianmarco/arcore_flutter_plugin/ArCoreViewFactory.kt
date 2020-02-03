package com.difrancescogianmarco.arcore_flutter_plugin

import android.content.Context
import android.util.Log
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory

enum class ARType {
    NONE,
    AUGMENTED_FACES,
    AUGMENTED_IMAGES,
    ;

    companion object {
        fun get(str: String): ARType {
            return when (str) {
                "faces" -> AUGMENTED_FACES
                "images" -> AUGMENTED_IMAGES
                else -> NONE
            }
        }
    }
}

class ArCoreViewFactory(val messenger: BinaryMessenger) : PlatformViewFactory(StandardMessageCodec.INSTANCE) {

    override fun create(context: Context, id: Int, args: Any?): PlatformView {
        val params = args as HashMap<*, *>
        Log.i("ArCoreViewFactory", id.toString())
        Log.i("ArCoreViewFactory", args.toString())
        val type = params["type"] as String
        if (type == "faces") {
            return ArCoreFaceView(context, messenger, id)
        }
        return ArCoreView(context, messenger, id, ARType.get(type), args)
    }
}