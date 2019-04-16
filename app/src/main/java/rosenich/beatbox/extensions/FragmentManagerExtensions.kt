package rosenich.beatbox.extensions

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

/**
 * https://medium.com/thoughts-overflow/how-to-add-a-fragment-in-kotlin-way-73203c5a450b
 */

inline fun FragmentManager.inTransaction(function: FragmentTransaction.() -> FragmentTransaction)
        = beginTransaction().function().commit()