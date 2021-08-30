package anton.asmirko.meltmusic.ui.adapters

import anton.asmirko.meltmusic.model.data.Transition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import anton.asmirko.meltmusic.R
import anton.asmirko.meltmusic.application.MeltMusicApp
import anton.asmirko.meltmusic.repository.dao.BlobDao
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TransitionAdapter : RecyclerView.Adapter<TransitionViewHolder>() {

    val data = mutableListOf<Transition>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransitionViewHolder =
        TransitionViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.transition_view_holder, parent, false)
        ).apply {
            MeltMusicApp.INSTANCE.appComponent.inject(this)
        }

    override fun onBindViewHolder(holder: TransitionViewHolder, position: Int) =
        holder.fill(data[position])

    override fun getItemCount(): Int = data.size

    fun clear(){

    }
}

class TransitionViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    @Inject
    lateinit var blobDao: BlobDao

    init {
        view.setOnClickListener {
            val kek = blobDao.getAll()
                .subscribeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                it.size
            }
            print("")
        }
    }

    fun fill(transition: Transition) {

    }
}