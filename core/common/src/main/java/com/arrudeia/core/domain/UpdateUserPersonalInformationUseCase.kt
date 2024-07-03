package com.arrudeia.core.domain

import android.content.Context
import android.net.Uri
import com.arrudeia.core.common.R.string.error_request
import com.arrudeia.core.data.repository.FirebaseUserRepositoryImpl
import com.arrudeia.core.data.repository.UpdateIdUserDocRepositoryImpl
import com.arrudeia.core.result.Result
import com.arrudeia.core.utils.createPdfWithImages
import javax.inject.Inject

class UpdateUserPersonalInformationUseCase @Inject constructor(
    private val firebaseUserRepositoryImpl: FirebaseUserRepositoryImpl,
    private val context: Context,
    private val updateIdDocUserRepository: UpdateIdUserDocRepositoryImpl
) {
    suspend operator fun invoke(
        images: List<Uri>
    ): Result<String?> {
        var result = ""
        val pdf = createPdfWithImages(context, images)
        pdf?.let {
            result = firebaseUserRepositoryImpl.saveUserDocImage(it)
        }
        if (result.isEmpty()) return Result.Error(error_request)
        return updateIdDocUserRepository.updateIdDocUser(result)
    }
}
