package anton.asmirko.meltmusic.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import anton.asmirko.meltmusic.application.MeltMusicApp
import anton.asmirko.meltmusic.databinding.FragmentTransitionsBinding
import anton.asmirko.meltmusic.ui.adapters.TransitionAdapter
import anton.asmirko.meltmusic.viewmodel.TransitionViewModel
import anton.asmirko.meltmusic.viewmodel.ViewModelProviderFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject


class TransitionsFragment : Fragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    private lateinit var transitionViewModel: TransitionViewModel

    private lateinit var fragmentTransitionsBinding: FragmentTransitionsBinding
    private lateinit var rootView: View
    private var transitionAdapter: TransitionAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentTransitionsBinding = FragmentTransitionsBinding.inflate(inflater)
        rootView = fragmentTransitionsBinding.root
        MeltMusicApp.INSTANCE.appComponent.inject(this)
        transitionViewModel =
            ViewModelProvider(this, viewModelProviderFactory)[TransitionViewModel::class.java]
        initRecycler()
        return rootView
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecycler() {
        transitionAdapter = TransitionAdapter()
        fragmentTransitionsBinding.transitionsRecycler.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = transitionAdapter
        }
        transitionViewModel.transitions.observe(viewLifecycleOwner, {
            transitionAdapter?.data?.addAll(it)
            transitionAdapter?.notifyDataSetChanged()
        })
        transitionViewModel.getTransitions()
    }

    override fun onDestroy() {
        super.onDestroy()
        transitionAdapter?.clear()
    }
}