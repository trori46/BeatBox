package rosenich.beatbox

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.fragment_beat_box.*
import kotlinx.android.synthetic.main.list_item_sound.view.*
import rosenich.beatbox.extensions.inflate

private const val NUM_COLUMNS = 3

class BeatBoxFragment : Fragment() {
    private val beatBox: BeatBox by lazy { BeatBox(activity) }

    companion object {
        fun newInstance() = BeatBoxFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Audio should keep playing on a config change, therefore fragment instance is retained to enable that
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_beat_box, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_beat_box_recycler_view.layoutManager = GridLayoutManager(activity, NUM_COLUMNS)
        fragment_beat_box_recycler_view.adapter = SoundAdapter(beatBox.sounds)
    }

    override fun onDestroy() {
        super.onDestroy()
        beatBox.release()
    }

    private inner class SoundHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView)
        , View.OnClickListener {

        private lateinit var sound: Sound

        fun bindSound(sound: Sound) {
            this.sound = sound
            itemView.list_item_sound_button.text = this.sound.name
            itemView.list_item_sound_button.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            beatBox.play(sound)
        }
    }

    private inner class SoundAdapter(private val sounds: List<Sound>)
        : RecyclerView.Adapter<SoundHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                SoundHolder(parent.inflate(R.layout.list_item_sound))

        override fun onBindViewHolder(holder: SoundHolder, position: Int) =
                holder.bindSound(sounds[position])

        override fun getItemCount() = sounds.size
    }
}