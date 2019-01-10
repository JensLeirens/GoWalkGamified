package be.mafken.gowalkgamified.ui.walk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import be.mafken.gowalkgamified.R
import kotlinx.android.synthetic.main.time_picker_dialog.*

class WalkTimePickerDialog : DialogFragment() {
 override fun onCreateView(
  inflater: LayoutInflater,
  container: ViewGroup?,
  savedInstanceState: Bundle?
 ): View? {
  return inflater.inflate(R.layout.time_picker_dialog,
   container, false)
 }

 var onOkClicked: OnDialogOkClicked? = null

 override fun onViewCreated(view: View,
                            savedInstanceState: Bundle?) {
  super.onViewCreated(view, savedInstanceState)

  timePickerSeconds.minValue = 0
  timePickerSeconds.maxValue = 59

  timePickerMinutes.minValue = 0
  timePickerMinutes.maxValue = 59

  timePickerHours.minValue = 0
  timePickerHours.maxValue = 24

  timePickerOkBtn.setOnClickListener {
   onOkClicked?.onOkClicked(
    timePickerHours.value,
    timePickerMinutes.value,
    timePickerSeconds.value
   )
   this.dismiss()
  }

  timePickerCancelBtn.setOnClickListener {
   this.dismiss()
  }
 }

 interface OnDialogOkClicked {
  fun onOkClicked(hours: Int, minutes: Int, seconds: Int)
 }

 override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  setStyle(DialogFragment.STYLE_NORMAL,
   android.R.style.Theme_Material_Light_Dialog)
 }
}