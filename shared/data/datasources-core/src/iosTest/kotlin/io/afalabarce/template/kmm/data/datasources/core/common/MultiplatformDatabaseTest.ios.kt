package dev.afalabarce.mangaref.data.datasources.core.common

import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.test.KoinTest
import kotlin.test.Ignore

@Ignore
actual open class RobolectricTest actual constructor()

actual abstract class MultiplatformDatabaseTest : RobolectricTest(), KoinTest {
    actual fun isAndroidTest() = false
    actual inline fun <reified T: RoomDatabase> createInMemoryDatabase(): RoomDatabase.Builder<T> = Room.inMemoryDatabaseBuilder()
}