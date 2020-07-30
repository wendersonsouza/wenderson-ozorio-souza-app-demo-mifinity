package ie.wenderson.ozorio.app.demo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import ie.wenderson.ozorio.app.demo.common.BaseEntity;
import ie.wenderson.ozorio.app.demo.entity.Role;
import ie.wenderson.ozorio.app.demo.entity.User;
import ie.wenderson.ozorio.app.demo.repository.UserRepository;

public abstract class GenericService {
	
	@Autowired
	protected UserRepository userRepository;
	
	protected final boolean isAdminUser(Long loggedUserId) {
		return userRepository.existsByIdAndRole(loggedUserId,Role.AD);
	}
	
	@SuppressWarnings("rawtypes")
	protected final boolean  isValidAdminUser(Long loggedUserId, BaseEntity entity) throws Exception{
		boolean isAdmin = false;
		Optional<User> user = userRepository.findById(loggedUserId);
		if(user.isPresent()) {
			isAdmin = user.get().getRole().equals(Role.AD);
		}else {
			entity.setMessage("User not exist.");
			entity.setSuccess(false);
			throw new Exception();
		}
		
	return isAdmin;
	}
	
}
