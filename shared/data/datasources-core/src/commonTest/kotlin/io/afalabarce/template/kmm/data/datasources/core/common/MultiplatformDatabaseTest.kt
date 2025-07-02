package dev.afalabarce.mangaref.data.datasources.core.common

import androidx.room.RoomDatabase
import org.koin.test.KoinTest

expect open class RobolectricTest()
expect abstract class MultiplatformDatabaseTest(): RobolectricTest, KoinTest {
    fun isAndroidTest(): Boolean
    inline fun <reified T: RoomDatabase> createInMemoryDatabase(): RoomDatabase.Builder<T>
}