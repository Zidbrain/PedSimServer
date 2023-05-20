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
    val schedule = generateSchedule()
    embeddedServer(Netty, 8080) {
        routing {
            get("/") {
                val text = Json.encodeToString(schedule)
                call.respondText(ContentType.parse("application/json")) { text }
            }
        }
    }.start(true)
}

fun generateSchedule(): Schedule {
    val schedule = mutableListOf<ScheduleRecord>()
    val periods = List(4) {
        (it * 90f) to ((it + 1) * 90f)
    }

    fun handlePath(
        path: Pair<String, String>,
        forward: List<Float>,
        backward: List<Float>
    ): List<ScheduleRecord> {
        val res = mutableListOf<ScheduleRecord>()
        val (from, to) = path
        periods.forEachIndexed { i, (start, end) ->
            if (forward[i] != 0f)
                res.add(
                    ScheduleRecord(
                        from = from,
                        to = to,
                        fromTime = start,
                        toTime = end,
                        rate = forward[i]
                    )
                )
            if (backward[i] != 0f)
                res.add(
                    ScheduleRecord(
                        from = to,
                        to = from,
                        fromTime = start,
                        toTime = end,
                        rate = backward[i]
                    )
                )
        }
        return res
    }

    schedule.addAll(
        listOf(
            handlePath(
                path = "Baumanskaya" to "BMSTU-Back",
                forward = listOf(15f, 8f, 3f, 0f),
                backward = listOf(0f, 3f, 8f, 15f)
            ),
            handlePath(
                path = "Baumanskaya" to "ULK",
                forward = listOf(16f, 7f, 3f, 0f),
                backward = listOf(0f, 3f, 7f, 16f)
            ),
            handlePath(
                path = "Baumanskaya" to "Energo",
                forward = listOf(3f, 1.5f, 1f, 0f),
                backward = listOf(0f, 1f, 1.5f, 3f)
            ),
            handlePath(
                path = "BMSTU-Front" to "Energo",
                forward = listOf(0f, 0.7f, 0.7f, 0f),
                backward = listOf(0f, 0.7f, 0.7f, 2f)
            ),
            handlePath(
                path = "BMSTU-Front" to "ULK",
                forward = listOf(0f, 1f, 1f, 0f),
                backward = listOf(0f, 1f, 1f, 2f)
            ),
            handlePath(
                path = "Energo" to "ULK",
                forward = listOf(0f, 0.4f, 0.4f, 0f),
                backward = listOf(0f, 0.4f, 0.4f, 2f)
            ),
        ).flatten()
    )

    return Schedule(records = schedule)
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
    val rate: Float,
)