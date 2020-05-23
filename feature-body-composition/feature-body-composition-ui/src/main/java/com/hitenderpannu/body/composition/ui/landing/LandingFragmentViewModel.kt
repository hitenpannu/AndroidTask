package com.hitenderpannu.body.composition.ui.landing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitenderpannu.body.composition.domain.BodyCompositionInteractor
import com.hitenderpannu.body.composition.entity.PrimaryBodyComposition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LandingFragmentViewModel(
    private val bodyCompositionInteractor: BodyCompositionInteractor
) : ViewModel() {

    private val mutableBodyCompositionEntries = MutableLiveData<List<PrimaryBodyComposition>>()
    val bodyCompositionEntries: LiveData<List<PrimaryBodyComposition>> = mutableBodyCompositionEntries

    private val mutableErrorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = mutableErrorMessage

    init {
        startObservingCompositionEntries()
    }

    private fun startObservingCompositionEntries() {
        CoroutineScope(Dispatchers.IO).launch {
            bodyCompositionInteractor.getAllEntries()
                .collect { updatedList ->
                    mutableBodyCompositionEntries.postValue(updatedList)
                }
        }
    }
}
