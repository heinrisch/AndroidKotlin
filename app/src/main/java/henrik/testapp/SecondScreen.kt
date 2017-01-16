package henrik.testapp

import android.animation.ObjectAnimator
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout
import kotlin.properties.ObservableProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SecondScreenFragment: Fragment(), FragmentLoadingHelper {

    lateinit var textView: TextView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UI {
            verticalLayout {
                textView = textView {
                    text = "Hello World 2!"
                }
            }
        }.view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Simulate loading
        isLoading = true
        view?.postDelayed({
            isLoading = false
            ObjectAnimator.ofFloat(textView, "translationX", 0f, 100f, 0f).apply {
                duration = 2000
            }.start()
        }, 2000)
    }
}

class SecondScreenActivity: SingleFragmentActivity() {

    override fun createFragment(): Fragment {
        return SecondScreenFragment()
    }

}