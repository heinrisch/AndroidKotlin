package henrik.testapp

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.onClick
import kotlin.properties.ObservableProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class MainActivity : AppCompatActivity() {

    // Use lazy here because context (this) is not available on creation
    private val textView by lazy { TextView(this).apply { text = "test2" } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(findViewById(R.id.text) as CustomTextView) {
            myText = "Hello Kotlin!"
        }

        (findViewById(R.id.activity_main) as ViewGroup).addView(textView)


        findViewById(R.id.button).onClick {
            startActivity(intentFor<SecondScreenActivity>())
        }
    }
}


// @JvmOverloads will create all the different constructors (kotlin will otherwise remove them becuase no one uses them)
class CustomTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : TextView(context, attrs, defStyleAttr) {

    var myText by didSet<String> { newString ->
        text = newString
    }

}


// "Inspired" by https://github.com/JetBrains/kotlin/blob/master/libraries/stdlib/src/kotlin/properties/Delegates.kt#L22
public inline fun <T> didSet(crossinline onChange: (newValue: T?) -> Unit):
        ReadWriteProperty<Any?, T?> = object : ObservableProperty<T?>(null) {
    override fun afterChange(property: KProperty<*>, oldValue: T?, newValue: T?) = onChange(newValue)
}