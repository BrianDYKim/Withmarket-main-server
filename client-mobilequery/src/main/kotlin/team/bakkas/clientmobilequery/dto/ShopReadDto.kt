package team.bakkas.clientmobilequery.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

/**
 * Shop Entity에 대한 Query용 dto (CQRS 패턴 적용에 대비하기 위해서 command와 분리를 의식적으로 시켜둠)
 * @author Brian
 * @since 22/05/22
 */
data class ShopReadDto(
    @JsonProperty("shop_id")
    var shopId: String,
    @JsonProperty("shop_name")
    var shopName: String,
    @JsonProperty("is_open")
    var isOpen: Boolean,
    @JsonProperty("open_time")
    var openTime: LocalDateTime,
    @JsonProperty("close_time")
    var closeTime: LocalDateTime,
    @JsonProperty("lot_number_address")
    var lotNumberAddress: String,
    @JsonProperty("road_name_address")
    var roadNameAddress: String,
    @JsonProperty("latitude")
    var latitude: Double,
    @JsonProperty("longitude")
    var longitude: Double,
    @JsonProperty("average_score")
    var averageScore: Double,
    @JsonProperty("review_number")
    var reviewNumber: Int
)