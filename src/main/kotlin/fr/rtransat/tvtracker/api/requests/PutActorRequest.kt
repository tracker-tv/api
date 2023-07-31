package fr.rtransat.tvtracker.api.requests

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable

@Serializable
data class PutActorRequest(
    @field:NotBlank
    val name: String
)
