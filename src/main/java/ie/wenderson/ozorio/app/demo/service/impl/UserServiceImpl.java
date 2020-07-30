package ie.wenderson.ozorio.app.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ie.wenderson.ozorio.app.demo.common.BaseEntity;
import ie.wenderson.ozorio.app.demo.entity.Role;
import ie.wenderson.ozorio.app.demo.entity.User;
import ie.wenderson.ozorio.app.demo.vo.LoginVO;

@Service
public class UserServiceImpl extends GenericService{
	
    public BaseEntity<List<User>> getAll(Long loggedUserId)
    {
    	BaseEntity<List<User>> baseEntity = new BaseEntity<List<User>>();
    	try {
    		boolean isAdmin = userRepository.existsByIdAndRole(loggedUserId, Role.AD);
    		if(isAdmin) {
		        baseEntity.setEntity(userRepository.findAll()); 
		        baseEntity.setSuccess(true);
		        baseEntity.setMessage(baseEntity.getEntity().size() > 0 ? "User list retrieved successfuly" : "User list is empty");
    		}else {
    			baseEntity.setSuccess(false);
		        baseEntity.setMessage("User not authorized");
    		}
    	}catch(Exception ex) {
    		if(StringUtils.isEmpty(baseEntity.getMessage())) {
    			baseEntity.setSuccess(false);
    			baseEntity.setMessage("Error on retriving user.");
    		}
    		
    	}
         return baseEntity;
    }
    
    public BaseEntity<User> getUserById(Long loggedUserId, Long id) {
    	BaseEntity<User> baseEntity = new BaseEntity<User>();
    	try {
    		if(id == null) {
    			baseEntity.setSuccess(false);
    			baseEntity.setMessage("Invalid Parameters");
    			throw new Exception();
    		}
    		
    		Optional<User> User = userRepository.findById(id);
    		if(User.isPresent()) {
    			baseEntity.setEntity(User.get());
            } else {
            	baseEntity.setMessage("No User record exist for given id");
            }
    		baseEntity.setSuccess(User.isPresent());
    		
    		
    	}catch(Exception ex) {
    		if(StringUtils.isEmpty(baseEntity.getMessage())) {
    			baseEntity.setSuccess(false);
    			baseEntity.setMessage("Error on retriving user.");
    		}
    		
    	}
         return baseEntity;
        
    }
    
    public BaseEntity<User> create(User entity)
    {
    	BaseEntity<User> baseEntity = new BaseEntity<User>();
    	
    	try {
    			validateParameters(baseEntity, entity);
    			checkUsernameExist(baseEntity, entity.getUsername());
    			baseEntity.setEntity(userRepository.save(entity));
                baseEntity.setMessage("User has been created successfuly");
                baseEntity.setSuccess(true);
    		
    		
    	}catch(Exception ex ) {
    		if(StringUtils.isEmpty(baseEntity.getMessage())) {
    			baseEntity.setSuccess(false);
    			baseEntity.setMessage("Error on creating.");
    		}
    		
    	}
        
         return baseEntity;
        
    }
    
    public BaseEntity<User> login(LoginVO login)
    {
    	BaseEntity<User> baseEntity = new BaseEntity<User>();
    	
    	try {
    		if(StringUtils.isEmpty(login.getUsername()) || StringUtils.isEmpty(login.getPassword())) {
        		baseEntity.setMessage("Invalid Parameters");
        		baseEntity.setSuccess(false);
        	}else {
        		
        		User user =userRepository.findByUsernameAndPassword(login.getUsername(), login.getPassword());
        		if(user == null) {
        			baseEntity.setMessage("Invalid username and/or password.");
            		baseEntity.setSuccess(false);
        		}else {
        			user.setPassword(null);
        			baseEntity.setEntity(user);
                    baseEntity.setMessage("User has been logged successfuly.");
                    baseEntity.setSuccess(true);
        		}
    		

        	}
    		
    	}catch(Exception ex ) {
    		if(StringUtils.isEmpty(baseEntity.getMessage())) {
    			baseEntity.setSuccess(false);
    			baseEntity.setMessage("Error on authentication.");
    		}
    		
    	}
        
         return baseEntity;
        
    }
    
    private void checkUsernameExist(BaseEntity<User> baseEntity,String username) throws Exception{
    	
    	if (userRepository.existsByUsername(username)) {
    		baseEntity.setMessage("Username already exist.");
    		baseEntity.setSuccess(false);
    		throw new Exception();
    	}
    }
    
    private void validateParameters(BaseEntity<User> baseEntity, User entity) throws Exception {
    	
    	if(StringUtils.isEmpty(entity.getUsername()) || StringUtils.isEmpty(entity.getPassword()) 
    			|| entity.getRole() == null) {
    		baseEntity.setMessage("Invalid Parameters");
    		baseEntity.setSuccess(false);
    		throw new Exception();
    	}
    }

}