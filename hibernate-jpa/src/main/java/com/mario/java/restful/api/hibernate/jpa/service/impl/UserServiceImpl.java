package com.mario.java.restful.api.hibernate.jpa.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import com.mario.java.restful.api.hibernate.jpa.domain.PetDomain;
import com.mario.java.restful.api.hibernate.jpa.domain.PetDomain_;
import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;
import com.mario.java.restful.api.hibernate.jpa.repository.PetRepository;
import com.mario.java.restful.api.hibernate.jpa.repository.UserRepository;
import com.mario.java.restful.api.hibernate.jpa.repository.exception.ObjectNofFoundException;
import com.mario.java.restful.api.hibernate.jpa.service.Service;
import com.mario.java.restful.api.hibernate.jpa.service.impl.qualifiers.UserService;

@Model
@UserService
public class UserServiceImpl implements Service<UserDomain, Long> {

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
	public void persist(UserDomain userDomain) throws Exception {
		this.userRepository.persist(userDomain);
	}

	@Override
	public void update(Long id, UserDomain userDomain) throws Exception, ObjectNofFoundException {

		if(this.find(id) != null){
			userDomain.setId(id);
			this.userRepository.update(userDomain);
		} else {
			throw new ObjectNofFoundException(id, UserDomain.class.getSimpleName());
		}
	}

	@Override
	public void delete(Long id) throws Exception, ObjectNofFoundException {
		UserDomain userDomain = this.find(id);

		if(userDomain != null){
			this.deletePets(userDomain);
			this.userRepository.delete(userDomain);
		} else {
			throw new ObjectNofFoundException(id, UserDomain.class.getSimpleName());
		}
	}

	@Override
	public void deleteAll() throws Exception, ObjectNofFoundException {
		this.userRepository.deleteAll();
		this.petRepository.deleteAll();
	}

	@Override
	public UserDomain find(Long id) {
		return this.userRepository.find(id);
	}

	@Override
	public List<UserDomain> findAll() {
		return this.userRepository.findAll();
	}

	@SuppressWarnings("hiding")
	@Override
	public <SingularAttribute, Object> List<UserDomain> findAll(Map<SingularAttribute, Object> restrictions) {
		return this.userRepository.findAll(restrictions);
	}

	private void deletePets(UserDomain userDomain) throws Exception {
		Map<javax.persistence.metamodel.SingularAttribute<PetDomain, UserDomain>, Object> restrictions = new HashMap<javax.persistence.metamodel.SingularAttribute<PetDomain,UserDomain>, Object>();
		restrictions.put(PetDomain_.user, userDomain);
		List<PetDomain> pets = this.petRepository.findAll(restrictions);

		if(pets != null) this.petRepository.deleteAll(pets);
	}
}
