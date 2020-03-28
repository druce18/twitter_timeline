package by.twitter.ui.dm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import by.twitter.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.dialog_direct.view.*
import kotlinx.android.synthetic.main.fragment_search.*

class DirectMassageFragment : Fragment(R.layout.fragment_direct_massage) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showDialogButton.setOnClickListener {
            displayDialog()
        }
    }

    private fun displayDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_direct, null)
        val builder = MaterialAlertDialogBuilder(requireContext()).setView(dialogView)
        val alertDialog = builder.show()

        dialogView.tryAgainButton.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    companion object {
        fun newInstance(): Fragment = DirectMassageFragment()
    }

}
