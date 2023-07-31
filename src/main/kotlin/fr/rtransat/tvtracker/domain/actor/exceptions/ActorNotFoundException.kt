package fr.rtransat.tvtracker.domain.actor.exceptions

import fr.rtransat.tvtracker.domain.shared.exceptions.ResourceNotFoundException

class ActorNotFoundException(id: Long) : ResourceNotFoundException("Actor does not exists with id: $id")
