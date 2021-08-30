package anton.asmirko.meltmusic.viewmodel

import androidx.lifecycle.MutableLiveData
import anton.asmirko.meltmusic.model.data.Transition
import androidx.lifecycle.ViewModel
import anton.asmirko.meltmusic.model.data.Blob
import anton.asmirko.meltmusic.repository.dao.BlobDao
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TransitionViewModel @Inject constructor(val blobDao: BlobDao) : ViewModel() {

    val transitions = MutableLiveData<MutableList<Transition>>()

    fun getTransitions() {
        val res = blobDao.getAll()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { blobs ->
                if (blobs.size >= 2) {
                    var prev = blobs[0]
                    val session = mutableListOf<Transition>()
                    for (i in 1 until blobs.size) {
                        val cur = blobs[i]
                        session.add(Transition(start = prev, end = cur))
                        prev = cur
                    }
                    transitions.value =
                        session.apply { transitions.value?.let { session.addAll(it) } }
                }
            }
    }
}