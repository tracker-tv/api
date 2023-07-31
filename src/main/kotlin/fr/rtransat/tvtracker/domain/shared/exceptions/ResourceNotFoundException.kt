package fr.rtransat.tvtracker.domain.shared.exceptions

abstract class ResourceNotFoundException(override val message: String) : RuntimeException(message)
