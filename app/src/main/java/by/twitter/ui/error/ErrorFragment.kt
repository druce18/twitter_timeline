package by.twitter.ui.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import by.twitter.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.dialog_error.view.*
import kotlinx.android.synthetic.main.fragment_error.*
import java.lang.RuntimeException

class ErrorFragment : Fragment(R.layout.fragment_error) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showDialogButton.setOnClickListener {
            displayDialog()
        }
    }

    private fun displayDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_error, null)
        val builder = MaterialAlertDialogBuilder(requireContext()).setView(dialogView)
        val alertDialog = builder.show()

        dialogView.okButton.setOnClickListener {
            alertDialog.dismiss()
            throw RuntimeException("Run Error")
        }

        dialogView.exitButton.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    companion object {

        fun newInstance(): Fragment = ErrorFragment()

    }

}
