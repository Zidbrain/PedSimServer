import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun main() {
    embeddedServer(Netty, 8080) {
        routing {
            val schedule = Schedule(
                listOf(
                    ScheduleRecord(
                        from = "Baumanskaya",
                        to = "BMSTU",
                        fromTime = 0.0f,
                        toTime = 400.0f,
                        amount = 3000
                    ),
                    ScheduleRecord(
                        from = "Baumanskaya",
                        to = "ULK",
                        fromTime = 0.0f,
                        toTime = 400.0f,
                        amount = 3000
                    ),
                    ScheduleRecord(
                        from = "Baumanskaya",
                        to = "BMSTU",
                        fromTime = 0.0f,
                        toTime = 90.0f,
                        amount = 800
                    ),
                    ScheduleRecord(
                        from = "Baumanskaya",
                        to = "ULK",
                        fromTime = 0.0f,
                        toTime = 90.0f,
                        amount = 800
                    ),
                    ScheduleRecord(
                        from = "BMSTU",
                        to = "ULK",
                        fromTime = 90.0f,
                        toTime = 180.0f,
                        amount = 300
                    ),
                    ScheduleRecord(
                        from = "BMSTU",
                        to = "Energo",
                        fromTime = 90.0f,
                        toTime = 180.0f,
                        amount = 150
                    ),
                    ScheduleRecord(
                        from = "ULK",
                        to = "BMSTU",
                        fromTime = 90.0f,
                        toTime = 180.0f,
                        amount = 300
                    ), // 2
                    ScheduleRecord(
                        from = "BMSTU",
                        to = "ULK",
                        fromTime = 180.0f,
                        toTime = 270.0f,
                        amount = 350
                    ),
                    ScheduleRecord(
                        from = "BMSTU",
                        to = "Energo",
                        fromTime = 180.0f,
                        toTime = 270.0f,
                        amount = 100
                    ),
                    ScheduleRecord(
                        from = "Energo",
                        to = "ULK",
                        fromTime = 180.0f,
                        toTime = 270.0f,
                        amount = 20
                    ),
                    ScheduleRecord(
                        from = "Energo",
                        to = "BMSTU",
                        fromTime = 180.0f,
                        toTime = 270.0f,
                        amount = 25
                    ),
                    ScheduleRecord(
                        from = "ULK",
                        to = "BMSTU",
                        fromTime = 180.0f,
                        toTime = 270.0f,
                        amount = 350
                    ),
                    ScheduleRecord(
                        from = "ULK",
                        to = "Baumanskaya",
                        fromTime = 180.0f,
                        toTime = 270.0f,
                        amount = 100
                    ),
                    ScheduleRecord(
                        from = "BMSTU",
                        to = "Baumanskaya",
                        fromTime = 180.0f,
                        toTime = 270.0f,
                        amount = 100
                    ), // 3
                    ScheduleRecord(
                        from = "BMSTU",
                        to = "ULK",
                        fromTime = 270.0f,
                        toTime = 360.0f,
                        amount = 400
                    ),
                    ScheduleRecord(
                        from = "BMSTU",
                        to = "Energo",
                        fromTime = 270.0f,
                        toTime = 360.0f,
                        amount = 100
                    ),
                    ScheduleRecord(
                        from = "Energo",
                        to = "ULK",
                        fromTime = 270.0f,
                        toTime = 360.0f,
                        amount = 50
                    ),
                    ScheduleRecord(
                        from = "Energo",
                        to = "BMSTU",
                        fromTime = 270.0f,
                        toTime = 360.0f,
                        amount = 60
                    ),
                    ScheduleRecord(
                        from = "ULK",
                        to = "BMSTU",
                        fromTime = 270.0f,
                        toTime = 360.0f,
                        amount = 400
                    ),
                    ScheduleRecord(
                        from = "ULK",
                        to = "Baumanskaya",
                        fromTime = 270.0f,
                        toTime = 360.0f,
                        amount = 400
                    ),
                    ScheduleRecord(
                        from = "BMSTU",
                        to = "Baumanskaya",
                        fromTime = 270.0f,
                        toTime = 360.0f,
                        amount = 300
                    ),//4
                    ScheduleRecord(
                        from = "ULK",
                        to = "Baumanskaya",
                        fromTime = 360.0f,
                        toTime = 450.0f,
                        amount = 700
                    ),
                    ScheduleRecord(
                        from = "BMSTU",
                        to = "Baumanskaya",
                        fromTime = 360.0f,
                        toTime = 450.0f,
                        amount = 700
                    ),
                )
            )

            get("/") {
                val text = Json.encodeToString(schedule)
                call.respondText(ContentType.parse("application/json")) { text }
            }
        }
    }.start(true)
}

@Serializable
data class Schedule(
    val records: List<ScheduleRecord>
)

@Serializable
data class ScheduleRecord(
    val from: String,
    val to: String,
    val fromTime: Float,
    val toTime: Float,
    val amount: Int,
)