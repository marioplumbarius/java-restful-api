package com.mario.java.restful.api.hibernate.jpa.repository.impl.jpa.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import com.mario.java.restful.api.hibernate.jpa.repository.impl.jpa.RepositoryJPAImpl;

/**
 * Utils which contains helper methods to manipulate data related to {@link RepositoryJPAImpl} instances.
 * @author marioluan
 *
 */
public final class RepositoryJPAImplUtils {

	/**
	 * Builds a custom {@link CriteriaQuery} criteriaQuery matching the {@link Map<SingularAttribute<T, Object>, Object>} restrictions.
	 * @param restrictions the restrictions to be applied to the criteriaQuery
	 * @param entityManager the entityManager
	 * @param entityClass the entityClass
	 * @return the criteriaQuery matching the restrictions
	 */
	public static <T> CriteriaQuery<T> buildCriteriaQueryFromRestrictions(Map<SingularAttribute<T, Object>, Object> restrictions, EntityManager entityManager, Class<T> entityClass) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<T> entity = criteriaQuery.from(entityClass);
		List<Predicate> predicates = new ArrayList<Predicate>();

		for(Map.Entry<SingularAttribute<T, Object>, Object> restriction : restrictions.entrySet()){
			Predicate predicate = criteriaBuilder.equal(entity.get(restriction.getKey()), restriction.getValue());
			predicates.add(predicate);
		}

		/**
		 * The following code is experiencing a crash when using Hibernate's metamodel class generation.
		 * bug report: https://hibernate.atlassian.net/browse/HHH-9259
		 */
		criteriaQuery.select(entity).where(criteriaBuilder.and(predicates.toArray(new Predicate[] {})));

		return criteriaQuery;
	}
}
