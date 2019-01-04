package be.mafken.gowalkgamified.ui.achievements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import be.mafken.gowalkgamified.R
import be.mafken.gowalkgamified.extensions.goToFragment
import be.mafken.gowalkgamified.extensions.nonNull
import be.mafken.gowalkgamified.extensions.observe
import kotlinx.android.synthetic.main.achievements_fragment.*

class AchievementsFragment : Fragment() {

    companion object {
        fun newInstance() = AchievementsFragment()
    }

    private val viewModel: AchievementsViewModel by lazy {
        ViewModelProviders.of(this).get(AchievementsViewModel::class.java)
    }
    private val userAdapter = UserAchievementAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.achievements_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.incrementAchievementOpendTracker()
        viewModel.getUsersFromDatabase()
        achievementsRecycler.layoutManager = LinearLayoutManager(context)
        achievementsRecycler.adapter = userAdapter

        viewModel.users.nonNull().observe(this){
            userAdapter.users = it
        }

        achievementsBtn.setOnClickListener {
            activity?.goToFragment("achievements", UserAchievementFragment.newInstance())
        }
    }

}
