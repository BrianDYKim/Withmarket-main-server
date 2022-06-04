package team.bakkas.applicationquery.advice

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import team.bakkas.common.error.ErrorCode
import team.bakkas.common.error.ErrorResponse
import team.bakkas.common.exceptions.RequestParamLostException
import team.bakkas.common.exceptions.ShopNotFoundException
import team.bakkas.common.exceptions.ShopReviewListNotValidException
import team.bakkas.common.exceptions.ShopReviewNotFoundException

/** Application-query 전반에서 발생하는 모든 exception을 캐치해서 처리하는 클래스
 * @since 22/05/31
 * @author Brian
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(javaClass)

    // ShopNotFoundException를 처리하는 exceptionHandler (500)
    @ExceptionHandler(ShopNotFoundException::class)
    fun handleShopNotFoundException(e: ShopNotFoundException): ResponseEntity<ErrorResponse.Response> {

        logger.error("Caught ShopNotFoundException!!")
        logger.warn(ErrorResponse.Response.of(ErrorCode.ENTITY_NOT_FOUND).toString())

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.Response.of(ErrorCode.ENTITY_NOT_FOUND))
    }

    // RequestParamLostException을 잡아서 처리하는 exceptionHandler (400)
    @ExceptionHandler(RequestParamLostException::class)
    fun handleRequestParamLostException(e: RequestParamLostException): ResponseEntity<ErrorResponse.Response> {

        logger.error("Caught requestParamException")
        logger.warn(ErrorResponse.Response.of(ErrorCode.REQUEST_PARAM_LOST).toString())

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.Response.of(ErrorCode.REQUEST_PARAM_LOST))
    }

    // Http 요청에서 RequestParam을 누락시킨 상태로 보낼 경우를 처리하는 exceptionHandler (400)
    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingServletRequestParameterException(e: MissingServletRequestParameterException): ResponseEntity<ErrorResponse.Response> {

        logger.error("Caught Request Parameters lost exception!!")
        logger.warn(ErrorResponse.Response.of(ErrorCode.REQUEST_PARAM_LOST).toString())

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.Response.of(ErrorCode.REQUEST_PARAM_LOST))
    }

    // ShopReview의 목록이 잘못 전달된 경우를 처리하는 exceptionHandler (500)
    @ExceptionHandler(ShopReviewListNotValidException::class)
    fun handleShopReviewListNotValidException(e: ShopReviewListNotValidException): ResponseEntity<ErrorResponse.Response> {

        logger.error("Caught shop list invalid exception!!")
        logger.warn(ErrorResponse.Response.of(ErrorCode.INVALID_SHOP_REVIEW_LIST).toString())

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.Response.of(ErrorCode.INVALID_SHOP_REVIEW_LIST))
    }

    // ShopReview를 찾아내지 못한 경우를 처리하는 exceptionHandler (500)
    @ExceptionHandler(ShopReviewNotFoundException::class)
    fun handleShopReviewNotFoundException(e: ShopReviewNotFoundException): ResponseEntity<ErrorResponse.Response> {

        logger.error("Caught shop review not found exception!!")
        logger.warn(ErrorResponse.Response.of(ErrorCode.ENTITY_NOT_FOUND).toString())

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.Response.of(ErrorCode.ENTITY_NOT_FOUND))
    }
}