package com.pillsquad.yakssok.core.model

enum class DomainErrorCode(
    override val code: Long,
    override val message: String,
) : ErrorCode {
    // OAuth 관련 에러
    INVALID_OAUTH_TOKEN(1000L, "토큰이 만료되었습니다."),
    UNSUPPORTED_SOCIAL_PROVIDER(1001L, "회원가입을 다시 시도해주세요."),

    // 인증 관련 에러
    ALREADY_REGISTERED_USER(2000L, "이미 가입된 회원입니다."),
    INVALID_JWT_TOKEN(2001L, "토큰이 만료되었습니다."),

    // 회원 관련 에러
    USER_NOT_FOUND(3000L, "존재하지 않는 회원입니다."),
    INVALID_INVITATION_CODE(3001L, "유효하지 않은 초대 코드입니다."),

    // 친구/팔로우 관련 에러
    ALREADY_FRIEND(4000L, "이미 친구로 등록된 사용자입니다."),
    CANNOT_ADD_SELF_AS_FRIEND(4001L, "자기 자신을 친구로 추가할 수 없습니다."),
    NOT_FRIEND(4002L, "친구가 아닌 사용자입니다."),

    // 복약 관련 에러
    MEDICATION_SCHEDULE_NOT_FOUND(5000L, "존재하지 않는 복약 스케줄입니다."),
    MEDICATION_RECORD_NOT_FOUND(5001L, "존재하지 않는 복약 기록입니다."),
    NO_PERMISSION(5002L, "해당 작업을 수행할 권한이 없습니다."),
    NOT_TODAY_SCHEDULE(5003L, "오늘의 복약 스케줄이 아닙니다."),

    // 이미지 관련 에러
    IMAGE_UPLOAD_FAILED(9100L, "이미지 업로드에 실패했습니다."),
    IMAGE_DELETE_FAILED(9101L, "이미지 삭제에 실패했습니다."),
    UNSUPPORTED_FILE_TYPE(9102L, "이미지 업로드에 실패했습니다."),
    UNSUPPORTED_FILE_EXTENSION(9103L, "이미지 업로드에 실패했습니다."),

    // 공통 에러
    SERVER_ERROR(9000L, "서버 오류가 발생했습니다."),
    INVALID_INPUT(9001L, "잘못된 입력 값입니다."),

    // 네트워크/기타 에러 (API 스펙에 없지만 클라이언트에서 필요한 것들)
    NETWORK_ERROR(-1L, "네트워크 환경을 확인해주세요."),
    UNKNOWN_ERROR(-2L, "알 수 없는 오류가 발생했습니다.");
}

sealed class DomainException(
    override val cause: Throwable? = null,
    override val errorCode: ErrorCode
) : ApplicationException(cause?.message ?: "Unknown", cause, errorCode) {

    // OAuth 관련 예외
    data class InvalidOAuthTokenException(
        override val cause: Throwable? = null,
        override val errorCode: ErrorCode = DomainErrorCode.INVALID_OAUTH_TOKEN
    ) : DomainException(cause, errorCode)

    data class UnsupportedSocialProviderException(
        override val cause: Throwable? = null,
        override val errorCode: ErrorCode = DomainErrorCode.UNSUPPORTED_SOCIAL_PROVIDER
    ) : DomainException(cause, errorCode)

    // 인증 관련 예외
    data class AlreadyRegisteredUserException(
        override val cause: Throwable? = null,
        override val errorCode: ErrorCode = DomainErrorCode.ALREADY_REGISTERED_USER
    ) : DomainException(cause, errorCode)

    data class InvalidJwtTokenException(
        override val cause: Throwable? = null,
        override val errorCode: ErrorCode = DomainErrorCode.INVALID_JWT_TOKEN
    ) : DomainException(cause, errorCode)

    // 회원 관련 예외
    data class UserNotFoundException(
        override val cause: Throwable? = null,
        override val errorCode: ErrorCode = DomainErrorCode.USER_NOT_FOUND
    ) : DomainException(cause, errorCode)

    data class InvalidInvitationCodeException(
        override val cause: Throwable? = null,
        override val errorCode: ErrorCode = DomainErrorCode.INVALID_INVITATION_CODE
    ) : DomainException(cause, errorCode)

    // 친구/팔로우 관련 예외
    data class AlreadyFriendException(
        override val cause: Throwable? = null,
        override val errorCode: ErrorCode = DomainErrorCode.ALREADY_FRIEND
    ) : DomainException(cause, errorCode)

    data class CannotAddSelfAsFriendException(
        override val cause: Throwable? = null,
        override val errorCode: ErrorCode = DomainErrorCode.CANNOT_ADD_SELF_AS_FRIEND
    ) : DomainException(cause, errorCode)

    data class NotFriendException(
        override val cause: Throwable? = null,
        override val errorCode: ErrorCode = DomainErrorCode.NOT_FRIEND
    ) : DomainException(cause, errorCode)

    // 복약 관련 예외
    data class MedicationScheduleNotFoundException(
        override val cause: Throwable? = null,
        override val errorCode: ErrorCode = DomainErrorCode.MEDICATION_SCHEDULE_NOT_FOUND
    ) : DomainException(cause, errorCode)

    data class MedicationRecordNotFoundException(
        override val cause: Throwable? = null,
        override val errorCode: ErrorCode = DomainErrorCode.MEDICATION_RECORD_NOT_FOUND
    ) : DomainException(cause, errorCode)

    data class NoPermissionException(
        override val cause: Throwable? = null,
        override val errorCode: ErrorCode = DomainErrorCode.NO_PERMISSION
    ) : DomainException(cause, errorCode)

    data class NotTodayScheduleException(
        override val cause: Throwable? = null,
        override val errorCode: ErrorCode = DomainErrorCode.NOT_TODAY_SCHEDULE
    ) : DomainException(cause, errorCode)

    // 이미지 관련 예외
    data class ImageUploadFailedException(
        override val cause: Throwable? = null,
        override val errorCode: ErrorCode = DomainErrorCode.IMAGE_UPLOAD_FAILED
    ) : DomainException(cause, errorCode)

    data class ImageDeleteFailedException(
        override val cause: Throwable? = null,
        override val errorCode: ErrorCode = DomainErrorCode.IMAGE_DELETE_FAILED
    ) : DomainException(cause, errorCode)

    data class UnsupportedFileTypeException(
        override val cause: Throwable? = null,
        override val errorCode: ErrorCode = DomainErrorCode.UNSUPPORTED_FILE_TYPE
    ) : DomainException(cause, errorCode)

    data class UnsupportedFileExtensionException(
        override val cause: Throwable? = null,
        override val errorCode: ErrorCode = DomainErrorCode.UNSUPPORTED_FILE_EXTENSION
    ) : DomainException(cause, errorCode)

    // 공통 예외
    data class ServerErrorException(
        override val cause: Throwable? = null,
        override val errorCode: ErrorCode = DomainErrorCode.SERVER_ERROR
    ) : DomainException(cause, errorCode)

    data class InvalidInputException(
        override val cause: Throwable? = null,
        override val errorCode: ErrorCode = DomainErrorCode.INVALID_INPUT
    ) : DomainException(cause, errorCode)

    // 네트워크/기타 예외
    data class NetworkException(
        override val cause: Throwable? = null,
        override val errorCode: ErrorCode = DomainErrorCode.NETWORK_ERROR
    ) : DomainException(cause, errorCode)

    data class UnknownException(
        override val cause: Throwable? = null,
        override val errorCode: ErrorCode = DomainErrorCode.UNKNOWN_ERROR
    ) : DomainException(cause, errorCode)
}

interface ErrorCode {
    val code: Long
    val message: String?
}

abstract class ApplicationException(
    override val message: String? = null,
    override val cause: Throwable? = null,
    open val errorCode: ErrorCode,
) : Exception(message, cause) {
    override fun toString(): String {
        return "${javaClass.simpleName}(" +
                "message=${message ?: "null"}, " +
                "cause=${cause?.javaClass?.name ?: "null"}, " +
                "errorCode=${errorCode.code}, " +
                "errorCodeMessage=${errorCode.message}" +
                ")"
    }
}