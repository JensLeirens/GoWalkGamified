package be.mafken.gowalkgamified.ui.scoreboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import be.mafken.gowalkgamified.R
import be.mafken.gowalkgamified.extensions.nonNull
import be.mafken.gowalkgamified.extensions.observe
import kotlinx.android.synthetic.main.scoreboard_fragment.*

class ScoreboardFragment : Fragment() {

    companion object {
        fun newInstance() = ScoreboardFragment()
    }

    private val viewModel: ScoreboardViewModel by lazy {
        ViewModelProviders.of(this).get(ScoreboardViewModel::class.java)
    }

    private val userAdapter = UserAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.scoreboard_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserFromDatabase()
        userRecycler.layoutManager = LinearLayoutManager(context)
        userRecycler.adapter = userAdapter

        viewModel.users.nonNull().observe(this){
            userAdapter.users = it
        }
    }


}
