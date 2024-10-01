package tv.tracker.api.integrations.utils

class Resource {
    companion object {
        fun asString(path: String): String = ClassLoader.getSystemResourceAsStream(path)!!.bufferedReader().use { it.readText() }
    }
}
