package dev.muthuram.newsbreeze.helper

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import dev.muthuram.newsbreeze.data.model.Trigger


fun <T> LiveData<T>.observeLiveData(lifecycleOwner: LifecycleOwner, function: (T) -> Unit) {
    this.observe(lifecycleOwner, {
        if (it != null)
            function.invoke(it)
    })
}

fun <T, O> setMap(
    source: LiveData<T>,
    block: (data: T?, result: MediatorLiveData<O>) -> Unit
): LiveData<O> {
    val result = MediatorLiveData<O>()
    result.addSource(source) { block(it, result) }
    return result
}

fun MutableLiveData<Trigger>.trigger() {
    value = Trigger
}