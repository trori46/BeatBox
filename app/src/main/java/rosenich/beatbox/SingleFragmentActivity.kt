package rosenich.beatbox

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import rosenich.beatbox.extensions.addFragment
import rosenich.beatbox.extensions.findFragmentById

abstract class SingleFragmentActivity : AppCompatActivity() {

    protected abstract fun createFragment(): Fragment

    @LayoutRes
    protected fun getLayoutResId(): Int = R.layout.activity_single_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())

        createFragmentIfNecessary(R.id.fragment_container)
    }

    private fun createFragmentIfNecessary(@IdRes containerViewId: Int) {
        if (findFragmentById(containerViewId) == null) {
            addFragment(containerViewId, createFragment())
        }
    }
}
