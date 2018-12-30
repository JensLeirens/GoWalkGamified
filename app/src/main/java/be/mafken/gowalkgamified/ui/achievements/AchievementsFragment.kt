package be.mafken.gowalkgamified.ui.achievements

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import be.mafken.gowalkgamified.R

class AchievementsFragment : Fragment() {

    companion object {
        fun newInstance() = AchievementsFragment()
    }

    private lateinit var viewModel: AchievementsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.achievements_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AchievementsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
