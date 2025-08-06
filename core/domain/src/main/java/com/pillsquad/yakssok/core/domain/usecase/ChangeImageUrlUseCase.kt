package com.pillsquad.yakssok.core.domain.usecase

import com.pillsquad.yakssok.core.domain.repository.ImageRepository
import javax.inject.Inject

class ChangeImageUrlUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(imageUrl: String): String? = imageRepository.changeImageUrlToFile(imageUrl)
}