package henrik.testapp

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI


interface FragmentLoadingHelper {

    fun getChildFragmentManager(): FragmentManager

    var isLoading: Boolean
        get() = getChildFragmentManager().findFragmentByTag(LoadingDialogFragment.LOADING_DIALOG_TAG) != null
        set(value) {
            if (value != isLoading) {
                if (value) {
                    getChildFragmentManager().showLoadingSpinner()
                } else {
                    getChildFragmentManager().hideLoadingSpinner()
                }
            }
        }
}

class LoadingDialogFragment() : DialogFragment() {
    companion object {
        val LOADING_DIALOG_TAG = "loading-dialog"
    }

    fun newInstance(): LoadingDialogFragment {
        return LoadingDialogFragment()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(getActivity(), R.style.LoadingDialog)

        dialog.setContentView(UI {
            relativeLayout {
                lparams(width = matchParent, height = matchParent)
                backgroundColor = Color.BLACK.withAlpha(150)
                progressBar(R.style.CircularProgress) {}.lparams(width = dip(60), height = dip(60)) { centerInParent() }
            }
        }.view)

        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)

        return dialog
    }
}


fun FragmentManager.showLoadingSpinner() {
    hideLoadingSpinner()
    LoadingDialogFragment().show(this, LoadingDialogFragment.LOADING_DIALOG_TAG)
}

fun FragmentManager.hideLoadingSpinner() {
    (findFragmentByTag(LoadingDialogFragment.LOADING_DIALOG_TAG) as? DialogFragment)?.dismissAllowingStateLoss()
}