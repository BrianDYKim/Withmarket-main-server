package team.bakkas.domainredis.repository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class RedisShopRepositoryTest @Autowired constructor(
    private val redisShopRepository: RedisShopRepository
){


}