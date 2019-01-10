package be.mafken.gowalkgamified.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import be.mafken.gowalkgamified.R
import be.mafken.gowalkgamified.extensions.nonNull
import be.mafken.gowalkgamified.extensions.observe
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : Fragment() {

 companion object {
  fun newInstance() = ProfileFragment()
 }


 private val viewModel: ProfileViewModel by lazy {
  ViewModelProviders.of(this).
   get(ProfileViewModel::class.java)
 }


 override fun onCreateView(
  inflater: LayoutInflater,
  container: ViewGroup?,
  savedInstanceState: Bundle?
 ): View? {
  return inflater.inflate(R.layout.profile_fragment,
   container, false)
 }

 override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
  super.onViewCreated(view, savedInstanceState)

  viewModel.getUserFromDatabase()
  viewModel.incrementProfileOpendTracker()
  viewModel.user.nonNull().observe(this) {
   userEmail.text = it.email
   userNameEdit.setText(it.name)
   Picasso.with(context).load(it.imageUrl).
    error(R.drawable.ic_user).into(userImage)
   userPicEdit.setText(it.imageUrl)
  }

  userSaveBtn.setOnClickListener {
   if (userNameEdit.text.isNullOrBlank())
    userNameEdit.error = "Name cannot be blank"
   if (userPicEdit.text.isNullOrBlank())
    userPicEdit.error = "ImageUrl cannot be blank"

   if (!userNameEdit.text.isNullOrBlank()
    && !userPicEdit.text.isNullOrBlank()) {
    viewModel.user.value?.name = userNameEdit.text.toString()
    viewModel.user.value?.imageUrl = userPicEdit.text.toString()
    viewModel.saveUserToDatabase()
    activity?.onBackPressed()
   }
  }

 }

}
