package anton.asmirko.meltmusic.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import anton.asmirko.meltmusic.R
import anton.asmirko.meltmusic.databinding.FragmentTransitionDetailBinding


/**
 * A simple [Fragment] subclass.
 * Use the [TransitionDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TransitionDetailFragment : Fragment() {

    private lateinit var transitionDetailFragmentBinding: FragmentTransitionDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        transitionDetailFragmentBinding = FragmentTransitionDetailBinding.inflate(inflater)

        return transitionDetailFragmentBinding.root
    }
}