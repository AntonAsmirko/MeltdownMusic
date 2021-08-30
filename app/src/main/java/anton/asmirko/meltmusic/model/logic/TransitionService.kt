package anton.asmirko.meltmusic.model.logic

import anton.asmirko.meltmusic.model.data.Transition
import anton.asmirko.meltmusic.repository.dao.BlobDao
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TransitionService @Inject constructor(val blobDao: BlobDao) {

    fun getTransitions() {
        val blobs = blobDao.getAll()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread()).subscribe { blobs ->
                if (blobs.size >= 2) {
                    var prev = blobs[0]
                    val session = mutableListOf<Transition>()
                    for (i in 1 until blobs.size) {
                        val cur = blobs[i]
                        session.add(Transition(start = prev, end = cur))
                        prev = cur
                    }
                }
            }
    }

}