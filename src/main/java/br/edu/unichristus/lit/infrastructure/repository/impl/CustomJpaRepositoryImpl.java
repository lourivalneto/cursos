package br.edu.unichristus.lit.infrastructure.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import br.edu.unichristus.lit.infrastructure.repository.CustomJpaRepository;


public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID>
	implements CustomJpaRepository<T, ID> {

	private EntityManager manager;
	
	public CustomJpaRepositoryImpl(final JpaEntityInformation<T, ?> entityInformation, 
			EntityManager entityManager) {
		super(entityInformation, entityManager);
		
		this.manager = entityManager;
	}

	@Override
	public void detach(final T entity) {
		this.manager.detach(entity);
	}

}
