package com.mario.java.restful.api.hibernate.jpa.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import com.mario.java.restful.api.hibernate.jpa.entity.PetEntity;
import com.mario.java.restful.api.hibernate.jpa.entity.PetEntity_;
import com.mario.java.restful.api.hibernate.jpa.entity.UserEntity;
import com.mario.java.restful.api.hibernate.jpa.repository.PetRepository;
import com.mario.java.restful.api.hibernate.jpa.repository.UserRepository;
import com.mario.java.restful.api.hibernate.jpa.repository.exception.ObjectNotFoundException;
import com.mario.java.restful.api.hibernate.jpa.service.Service;
import com.mario.java.restful.api.hibernate.jpa.service.impl.qualifiers.UserService;

@Model
@UserService
public class UserServiceImpl implements Service<UserEntity, Long> {

	private UserRepository userRepository;
	private PetRepository petRepository;

	public UserServiceImpl(){
	}

	@Inject
	public UserServiceImpl(UserRepository userRepository, PetRepository petRepository){
		this.userRepository = userRepository;
		this.petRepository = petRepository;
	}

	@Override
	public void persist(UserEntity userEntity) throws Exception {
		this.userRepository.persist(userEntity);
	}

	@Override
	public void update(Long id, UserEntity userEntity) throws Exception, ObjectNotFoundException {

		if(this.find(id) != null){
			userEntity.setId(id);
			this.userRepository.update(userEntity);
		} else {
			throw new ObjectNotFoundException(id, UserEntity.class.getSimpleName());
		}
	}

	@Override
	public void delete(Long id) throws Exception, ObjectNotFoundException {
		UserEntity userEntity = this.find(id);

		if(userEntity != null){
			this.deletePets(userEntity);
			this.userRepository.delete(userEntity);
		} else {
			throw new ObjectNotFoundException(id, UserEntity.class.getSimpleName());
		}
	}

	@Override
	public void deleteAll() throws Exception, ObjectNotFoundException {
		this.userRepository.deleteAll();
		this.petRepository.deleteAll();
	}

	@Override
	public UserEntity find(Long id) {
		return this.userRepository.find(id);
	}

	@Override
	public List<UserEntity> findAll() {
		return this.userRepository.findAll();
	}

	@SuppressWarnings("hiding")
	@Override
	public <SingularAttribute, Object> List<UserEntity> findAll(Map<SingularAttribute, Object> restrictions) {
		return this.userRepository.findAll(restrictions);
	}

	private void deletePets(UserEntity userEntity) throws Exception {
		Map<javax.persistence.metamodel.SingularAttribute<PetEntity, UserEntity>, Object> restrictions = new HashMap<javax.persistence.metamodel.SingularAttribute<PetEntity,UserEntity>, Object>();
		restrictions.put(PetEntity_.user, userEntity);
		List<PetEntity> pets = this.petRepository.findAll(restrictions);

		if(pets != null) this.petRepository.deleteAll(pets);
	}
}
