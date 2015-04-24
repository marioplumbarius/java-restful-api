package com.mario.java.restful.api.hibernate.jpa.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import com.mario.java.restful.api.hibernate.jpa.dto.UserDTO;
import com.mario.java.restful.api.hibernate.jpa.entity.PetEntity;
import com.mario.java.restful.api.hibernate.jpa.entity.PetEntity_;
import com.mario.java.restful.api.hibernate.jpa.entity.UserEntity;
import com.mario.java.restful.api.hibernate.jpa.mapper.UserMapper;
import com.mario.java.restful.api.hibernate.jpa.repository.PetRepository;
import com.mario.java.restful.api.hibernate.jpa.repository.UserRepository;
import com.mario.java.restful.api.hibernate.jpa.repository.exception.ObjectNotFoundException;
import com.mario.java.restful.api.hibernate.jpa.service.Service;
import com.mario.java.restful.api.hibernate.jpa.service.impl.qualifiers.UserService;

@Model
@UserService
public class UserServiceImpl implements Service<UserDTO, Long> {

	private UserRepository userRepository;
	private UserMapper userMapper;
	
	private PetRepository petRepository;

	public UserServiceImpl(){
	}

	@Inject
	public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PetRepository petRepository){
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.petRepository = petRepository;
	}

	@Override
	public void persist(UserDTO userDto) throws Exception {
		UserEntity userEntity = userMapper.mapFromDTOToEntity(userDto);
		
		this.userRepository.persist(userEntity);
	}

	@Override
	public void update(Long id, UserDTO userDto) throws Exception, ObjectNotFoundException {
		UserEntity userEntity = userMapper.mapFromDTOToEntity(userDto);
		
		if(this.find(id) != null){
			userEntity.setId(id);
			this.userRepository.update(userEntity);
		} else {
			throw new ObjectNotFoundException(id, UserDTO.class.getSimpleName());
		}
	}

	@Override
	public void delete(Long id) throws Exception, ObjectNotFoundException {
		UserEntity userEntity = this.userRepository.find(id);

		if(userEntity != null){
			this.deletePets(userEntity);
			this.userRepository.delete(userEntity);
		} else {
			throw new ObjectNotFoundException(id, UserDTO.class.getSimpleName());
		}
	}

	@Override
	public void deleteAll() throws Exception, ObjectNotFoundException {
		this.userRepository.deleteAll();
		this.petRepository.deleteAll();
	}

	@Override
	public UserDTO find(Long id) {
		UserEntity userEntity = this.userRepository.find(id);
		UserDTO userDTO = this.userMapper.mapFromEntityToDTO(userEntity);
		
		return userDTO;
	}

	@Override
	public List<UserDTO> findAll() {
		List<UserEntity> listUserEntity = this.userRepository.findAll();
		List<UserDTO> listUserDTO = this.userMapper.mapFromEntitiesToDTOs(listUserEntity);
		
		return listUserDTO;
	}

	@SuppressWarnings("hiding")
	@Override
	public <SingularAttribute, Object> List<UserDTO> findAll(Map<SingularAttribute, Object> restrictions) {
		List<UserEntity> listUserEntity = this.userRepository.findAll(restrictions);
		List<UserDTO> listUserDTO = this.userMapper.mapFromEntitiesToDTOs(listUserEntity);
		
		return listUserDTO;
	}

	private void deletePets(UserEntity userEntity) throws Exception {
		Map<javax.persistence.metamodel.SingularAttribute<PetEntity, UserEntity>, Object> restrictions = new HashMap<javax.persistence.metamodel.SingularAttribute<PetEntity,UserEntity>, Object>();
		restrictions.put(PetEntity_.user, userEntity);
		List<PetEntity> pets = this.petRepository.findAll(restrictions);

		if(pets != null) this.petRepository.deleteAll(pets);
	}
}
