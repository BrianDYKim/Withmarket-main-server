package team.bakkas.applicationquery.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.common.runBlocking
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.reactor.mono
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import team.bakkas.common.exceptions.ShopReviewNotFoundException
import team.bakkas.domaindynamo.entity.ShopReview
import team.bakkas.domainqueryservice.repository.ShopReviewRepository
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
internal class ShopReviewServiceTest {
    @MockK(relaxed = true)
    private lateinit var shopReviewRepository: ShopReviewRepository

    private lateinit var shopReviewService: ShopReviewService

    @BeforeEach
    fun setUp() {
        shopReviewService = spyk(ShopReviewService(shopReviewRepository))
    }

    // 1-1. 잘못된 review Key로 인해서 review를 가져오지 못하는 경우 테스트
    @Test
    @DisplayName("[service] 잘못된 review key 정보로 인해 review를 가져오지 못하는 테스트")
    fun failFindReviewByIdAndTitle() = runBlocking {
        // given
        val reviewId = "review-fake-id"
        val reviewTitle = "review-fake-title"

        every { shopReviewRepository.findShopReviewByIdAndTitleWithCaching(reviewId, reviewTitle) } returns mono {
            null
        }

        // when
        val exception =
            shouldThrow<ShopReviewNotFoundException> { shopReviewService.findReviewByIdAndTitle(reviewId, reviewTitle) }

        // then
        verify(exactly = 1) { shopReviewRepository.findShopReviewByIdAndTitleWithCaching(reviewId, reviewTitle) }
        coVerify(exactly = 1) { shopReviewService.findReviewByIdAndTitle(reviewId, reviewTitle) }
        assert(exception is ShopReviewNotFoundException)

        println("[[service] 잘못된 review key 정보로 인해 review를 가져오지 못하는 테스트] passed!!")
    }

    @Test
    @DisplayName("[service] review를 하나 성공적으로 가져오는 메소드")
    fun successFindReviewByIdAndTitle() = runBlocking {
        // given
        val reviewId = "review-id"
        val reviewTitle = "review-title"
        val shopId = "shop-id"
        val shopName = "shop-name"

        every { shopReviewRepository.findShopReviewByIdAndTitleWithCaching(reviewId, reviewTitle) } returns
                mono { getMockReview(reviewId, reviewTitle, shopId, shopName) }

        // when
        val shopReview = shopReviewService.findReviewByIdAndTitle(reviewId, reviewTitle)

        // then
        verify(exactly = 1) { shopReviewRepository.findShopReviewByIdAndTitleWithCaching(reviewId, reviewTitle) }
        coVerify(exactly = 1) { shopReviewService.findReviewByIdAndTitle(reviewId, reviewTitle) }
        assertNotNull(shopReview)
        shopReview.let {
            assertEquals(it.reviewId, reviewId)
            assertEquals(it.reviewTitle, reviewTitle)
            assertEquals(it.shopId, shopId)
            assertEquals(it.shopName, shopName)
        }

        println("[[service] review를 하나 성공적으로 가져오는 메소드] passed!!")
    }



    private fun getMockReview(reviewId: String, reviewTitle: String, shopId: String, shopName: String) =
        ShopReview(
            reviewId = reviewId,
            reviewTitle = reviewTitle,
            shopId = shopId,
            shopName = shopName,
            reviewContent = "저는 아주 불만족했어요! ^^",
            reviewScore = 1.0,
            reviewPhotoList = listOf(),
            createdAt = LocalDateTime.now(),
            updatedAt = null
        )
}