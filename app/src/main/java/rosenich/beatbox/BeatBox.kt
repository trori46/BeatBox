package rosenich.beatbox

import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import android.util.Log
import java.io.IOException

private const val TAG: String = "BeatBox"
private const val SOUNDS_FOLDER: String = "sample_sounds"
private const val MAX_SOUNDS = 4

class BeatBox(context: Context) {

    private val assets = context.assets!!
    val sounds = mutableListOf<Sound>()
    val soundPool = SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0)

    init {
        loadSounds()
    }

    private fun loadSounds() {
        try {
            assets.list(SOUNDS_FOLDER)!!.let {
                Log.i(TAG, "Found ${it.size} sounds")
                it.forEach {
                    val assetPath = "$SOUNDS_FOLDER/$it"
                    val sound = Sound(assetPath)
                    load(sound)
                    sounds.add(sound)
                }
            }
        } catch (ioe: IOException) {
            Log.e(TAG, "Could not list assets", ioe)
        }
    }

    private fun load(sound: Sound) {
        val assetFileDescriptor = assets.openFd(sound.assetPath)
        sound.soundId = soundPool.load(assetFileDescriptor, 1)
    }

    fun play(sound: Sound) {
        soundPool.play(sound.soundId ?: return, 1.0f, 1.0f, 1, 0, 1.0f)
    }

    fun release() = soundPool.release()
}