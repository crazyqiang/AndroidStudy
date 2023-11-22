package org.ninetripods.mq.study.jetpack_compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState

/**
 * Created by mq on 2023/11/16
 */
@Composable
fun loadNetworkImage(
    url: String,
    imageRepository: ImageRepository,
): State<Result<Image>> {

    // Creates a State<T> with Result.Loading as initial value
    // If either `url` or `imageRepository` changes, the running producer
    // will cancel and will be re-launched with the new inputs.
    return produceState<Result<Image>>(initialValue = Result.Loading, url, imageRepository) {

        // In a coroutine, can make suspend calls
        val image = imageRepository.load(url)

        // Update State with either an Error or Success result.
        // This will trigger a recomposition where this State is read
        value = if (image == null) {
            Result.Error
        } else {
            Result.Success(image)
        }
    }
}

class ImageRepository{
    fun load(url: String):Image? {
        return if (url.isEmpty()) null else Image("")
    }
}

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    object Error : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
}

data class Image(val url: String)