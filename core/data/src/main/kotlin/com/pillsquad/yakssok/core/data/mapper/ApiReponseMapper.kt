package com.pillsquad.yakssok.core.data.mapper

import com.pillsquad.yakssok.core.model.DomainException
import com.pillsquad.yakssok.core.model.HttpException
import com.pillsquad.yakssok.core.network.model.ApiResponse

inline fun <T, R> ApiResponse<T>.toResult(
    @Suppress("UNCHECKED_CAST") transform: (T) -> R = { it as R },
): Result<R> = when (this) {
    is ApiResponse.Success -> runCatching { transform(data) }
    is ApiResponse.Failure.HttpError -> Result.failure(
        mapErrorCodeToDomainException(code, HttpException(code, message))
    )
    is ApiResponse.Failure.NetworkError -> Result.failure(
        DomainException.NetworkException(cause = throwable)
    )
    is ApiResponse.Failure.UnknownApiError -> Result.failure(
        DomainException.UnknownException(cause = throwable)
    )
}

fun <T> ApiResponse<T>.toResult(): Result<T> = toResult { it }

fun mapErrorCodeToDomainException(errorCode: Long, cause: Throwable? = null): DomainException {
    return when (errorCode) {
        1000L -> DomainException.InvalidOAuthTokenException(cause = cause)
        1001L -> DomainException.UnsupportedSocialProviderException(cause = cause)
        2000L -> DomainException.AlreadyRegisteredUserException(cause = cause)
        2001L -> DomainException.InvalidJwtTokenException(cause = cause)
        3000L -> DomainException.UserNotFoundException(cause = cause)
        3001L -> DomainException.InvalidInvitationCodeException(cause = cause)
        4000L -> DomainException.AlreadyFriendException(cause = cause)
        4001L -> DomainException.CannotAddSelfAsFriendException(cause = cause)
        4002L -> DomainException.NotFriendException(cause = cause)
        5000L -> DomainException.MedicationScheduleNotFoundException(cause = cause)
        5001L -> DomainException.MedicationRecordNotFoundException(cause = cause)
        5002L -> DomainException.NoPermissionException(cause = cause)
        5003L -> DomainException.NotTodayScheduleException(cause = cause)
        9100L -> DomainException.ImageUploadFailedException(cause = cause)
        9101L -> DomainException.ImageDeleteFailedException(cause = cause)
        9102L -> DomainException.UnsupportedFileTypeException(cause = cause)
        9103L -> DomainException.UnsupportedFileExtensionException(cause = cause)
        9000L -> DomainException.ServerErrorException(cause = cause)
        9001L -> DomainException.InvalidInputException(cause = cause)
        else -> DomainException.UnknownException(cause = cause)
    }
}