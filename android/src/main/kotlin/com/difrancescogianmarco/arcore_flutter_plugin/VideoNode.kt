package com.difrancescogianmarco.arcore_flutter_plugin

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import com.difrancescogianmarco.arcore_flutter_plugin.flutter_models.FlutterArCoreMaterial
import com.difrancescogianmarco.arcore_flutter_plugin.flutter_models.FlutterArCoreNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ExternalTexture
import com.google.ar.sceneform.rendering.ModelRenderable
import java.io.File
import kotlin.math.min

class VideoNode(context: Context, params: FlutterArCoreNode, material: FlutterArCoreMaterial,
                videoPath: String) : Node() {

    companion object {
        val videos = HashMap<String, VideoTexture>()
        fun dispose() {
            videos.forEach { (_, v) -> v.player.release() }
        }
    }

    val video: VideoTexture

    init {
        video = videos.get(videoPath) ?: let {
            val p = MediaPlayer.create(context, Uri.fromFile(File(videoPath)))
            p.isLooping = material.isLooping
            val ret = VideoTexture(p)
            videos[videoPath] = ret
            p.setSurface(ret.texture.surface)
            ret
        }
        ModelRenderable.builder()
                .setSource(context, R.raw.chroma_key_video)
                .build()
                .thenAccept { renderable ->
                    renderable.material.setExternalTexture("videoTexture", video.texture)
                    if (!video.player.isPlaying) {
                        video.player.start()
                    }
                    this.renderable = renderable
                }

        val vWidth = video.player.videoWidth
        val vHeight = video.player.videoHeight
        val scale = min((params.scale.x / vWidth), (params.scale.y / vHeight))

        name = params.name
        localPosition = params.position
        localScale = Vector3(scale * vWidth, scale * vHeight, 1.0f)
        localRotation = params.rotation
    }


}

data class VideoTexture(val player: MediaPlayer) {
    val texture = ExternalTexture()
}