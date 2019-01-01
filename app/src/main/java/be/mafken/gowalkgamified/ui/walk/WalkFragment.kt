package be.mafken.gowalkgamified.ui.walk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import be.mafken.gowalkgamified.R
import kotlinx.android.synthetic.main.walk_fragment.*
import timber.log.Timber
import kotlin.random.Random

class WalkFragment : Fragment() {

    companion object {
        fun newInstance() = WalkFragment()
    }

    private val viewModel: WalkViewModel by lazy {
        ViewModelProviders.of(this).get(WalkViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.walk_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getWalkingsFromDatabase()
        viewModel.incrementWalkingsCreatedTracker()
        walkPickTimeBtn.setOnClickListener {
            val fragment = WalkTimePickerDialog()
            fragment.onOkClicked = object : WalkTimePickerDialog.OnDialogOkClicked {
                override fun onOkClicked(hours: Int, minutes: Int, seconds: Int) {
                    viewModel.walk.value!!.time = calculateSeconds(hours,minutes,seconds)
                    viewModel.walk.value!!.displayTime = "${hours}h : ${minutes}m : ${seconds}s"
                    walkTimeText.text = viewModel.walk.value!!.displayTime
                }

            }
            fragment.show(fragmentManager!!, "Timepicker Dialog")
        }

        walkSaveBtn.setOnClickListener {

            var noError = true
            try {
                viewModel.walk.value?.amountKm = walkDistanceEdit.text.toString().toInt()
            } catch (e: NumberFormatException){
                noError = false
                walkDistanceEdit.error = "This has to be a number. ex: 5 or 2"
            }

            if(noError) {
                calculateScore()
                viewModel.saveWalkToDatabase()
                activity?.onBackPressed()
            }
        }

    }
    fun calculateSeconds(hours: Int, minutes: Int, seconds: Int) = seconds + minutes * 60 + hours * 60 * 60

    private fun calculateScore(){
        val score = (viewModel.walk.value!!.amountKm * 200) - (viewModel.walk.value!!.time / 10)
        val random = Random.nextInt(0,100)

        Timber.d(random.toString())

        if (random < 11) {
            Toast.makeText(context, "Score has been doubled! Congratz, keep up the walking!", Toast.LENGTH_LONG).show()
            viewModel.walk.value!!.score = score * 2
        } else
            viewModel.walk.value!!.score = score

        when (score) {
            in 0..99 -> Toast.makeText(context, "That can be better! try to walk faster next time.", Toast.LENGTH_LONG).show()
            in 99..199 -> Toast.makeText(context, "Not bad for a small walk! try to walk farther next time.", Toast.LENGTH_LONG).show()
            in 200..399 -> Toast.makeText(context, "Not bad, next time try a faster phase for a higher score!", Toast.LENGTH_LONG).show()
            else -> Toast.makeText(context, "Nice walk, keep it up!", Toast.LENGTH_LONG).show()
        }

    }
}
