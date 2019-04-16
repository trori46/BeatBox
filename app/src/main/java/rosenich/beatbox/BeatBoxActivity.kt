package rosenich.beatbox

class BeatBoxActivity : SingleFragmentActivity() {
    override fun createFragment() = BeatBoxFragment.newInstance()
}
