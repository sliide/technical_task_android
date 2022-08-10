package com.sliide.demoapp.utils.mapper

/**
 * Interface responsible for defining conversion methods from entity to domain model and the
 * other way around.
 */
interface EntityMapper<Entity, DomainModel> {

    fun mapFromEntity(entity: Entity) : DomainModel

    fun mapFromDomainModel(domainModel: DomainModel) : Entity

}