package henrik.testapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.ViewGroup
import android.widget.FrameLayout
import org.jetbrains.anko.UI
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.support.v4.withArguments
import org.jetbrains.anko.verticalLayout

abstract class SingleFragmentActivity : AppCompatActivity() {

    val FRAGMENT_TAG = "fragment-tag"
    lateinit var container: ViewGroup
    var fragment: Fragment? = null

    abstract fun createFragment(): Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(UI {
            verticalLayout {
                container = frameLayout {
                    id = R.id.single_fragment_container
                    layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                }
            }
        }.view)

        fragment = fragment ?: supportFragmentManager.findFragmentByTag(FRAGMENT_TAG)
        if (fragment == null) {
            // Take args to activity and pass to fragment
            val args = (intent.extras?.pairs ?: listOf<Pair<String, Any>>()).toTypedArray()
            fragment = createFragment().withArguments(*args)
            supportFragmentManager.beginTransaction()
                    .add(container.id, fragment, FRAGMENT_TAG)
                    .commit()
        }
    }
}

val Bundle.pairs: List<Pair<String, Any>>
    get() {
        return keySet().map { Pair(it, get(it)) }
    }