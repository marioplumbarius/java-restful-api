package com.mario.java.restful.api.hibernate.jpa.mapper;

import java.util.List;

/**
 * Mapper for DTO and Entities objects.
 * @author msouz23
 *
 * @param <T1> the entity class
 * @param <T2> the dto class
 */
public interface Mapper<T1, T2> {

	/**
	 * Maps a list of {@link T1} entities to a list of {@link T2} dtos.
	 * @param entities the list of entities to be mapped to
	 * @return the list of mapped {@link T2} dtos
	 */
	public List<T2> mapFromEntitiesToDTOs(List<T1> entities);

	/**
	 * Maps a list of {@link T2} dtos to a list of {@link T1} entities.
	 * @param dtos the list of dtos to be mapped to
	 * @return the list of mapped {@link T1} entities
	 */
	public List<T1> mapFromDTOsToEntities(List<T2> dtos);

	/**
	 * Maps an {@link T1} entity to a {@link T2} dto.
	 * @param entity the entity to be mapped to the dto
	 * @return the mapped dto
	 */
	public T2 mapFromEntityToDTO(T1 entity);

	/**
	 * Maps a {@link T2} dto to an {@link T1} entity.
	 * @param dto the dto to be mapped to the entity
	 * @return the mapped entity
	 */
	public T1 mapFromDTOToEntity(T2 dto);
}