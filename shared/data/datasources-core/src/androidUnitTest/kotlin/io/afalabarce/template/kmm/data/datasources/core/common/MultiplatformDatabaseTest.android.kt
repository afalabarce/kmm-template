package dev.afalabarce.mangaref.data.datasources.core.common

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.robolectric.RobolectricTestRunner
import kotlin.jvm.java
import kotlin.test.Ignore

@RunWith(RobolectricTestRunner::class)
@Ignore("This is used as an interface")
actual open class RobolectricTest actual constructor()

actual abstract class MultiplatformDatabaseTest : RobolectricTest(), KoinTest {
    actual fun isAndroidTest() = true
    actual inline fun <reified T: RoomDatabase> createInMemoryDatabase(): RoomDatabase.Builder<T> {
        return Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            T::class.java
        )
    }
}