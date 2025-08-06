package com.pillsquad.yakssok.core.domain.usecase

import com.pillsquad.yakssok.core.domain.repository.ImageRepository
import javax.inject.Inject

class PostImageUrlUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(imageUrl: String): Result<String> = imageRepository.postImage(imageUrl)
}