package ie.wenderson.ozorio.app.demo.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ie.wenderson.ozorio.app.demo.common.BaseEntity;
import ie.wenderson.ozorio.app.demo.entity.Role;
import ie.wenderson.ozorio.app.demo.entity.User;
import ie.wenderson.ozorio.app.demo.vo.LoginVO;

@Service
public class UserServiceImpl extends GenericService{
	
    public BaseEntity<List<Role>> gettAllRoles(){
    	BaseEntity<List<Role>> baseEntity = new BaseEntity<List<Role>>();
		        baseEntity.setEntity(Arrays.asList(Role.values())); 
    			baseEntity.setSuccess(true);
		        baseEntity.setMessage("Roles list retrieved successfuly");
         return baseEntity;
    }
	
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
    
    public BaseEntity<User> create(Long loggedUserId, User entity)
    {
    	BaseEntity<User> baseEntity = new BaseEntity<User>();
    	
    	try {
    		if(isValidAdminUser(loggedUserId,baseEntity)) {
    			validateParameters(baseEntity, entity);
    			checkUsernameExist(baseEntity, entity.getUsername());
    			entity.setRole(Role.getByName(entity.getRoleName()));
    			entity.encryptPassword();
    			baseEntity.setEntity(userRepository.save(entity));
                baseEntity.setMessage("User has been created successfuly");
                baseEntity.setSuccess(true);
    		}else {
    			baseEntity.setMessage("User not Authorized.");
                baseEntity.setSuccess(false);
    		}
    		
    	}catch(Exception ex ) {
    		if(StringUtils.isEmpty(baseEntity.getMessage())) {
    			baseEntity.setSuccess(false);
    			baseEntity.setMessage("Error on creating.");
    		}
    		
    	}
        
         return baseEntity;
        
    }
    
    public void setupUserAdmin(User entity){
		try {
			entity.encryptPassword();
		} catch (Exception e) {
			e.printStackTrace();
		}

    	userRepository.save(entity);
    }
    
    public BaseEntity<User> login(LoginVO login)
    {
    	BaseEntity<User> baseEntity = new BaseEntity<User>();
    	
    	try {
    		if(StringUtils.isEmpty(login.getUsername()) || StringUtils.isEmpty(login.getPassword())) {
        		baseEntity.setMessage("Invalid Parameters");
        		baseEntity.setSuccess(false);
        	}else {
        		
        		User user =userRepository.findByUsernameIgnoreCase(login.getUsername());
        		if(user == null) {
        			baseEntity.setMessage("Invalid username and/or password.");
            		baseEntity.setSuccess(false);
        		}else {
        			
        			user.decryptPassword();
        			if(login.getPassword().equals(user.getPassword())) {
            			user.setPassword(null);
        				baseEntity.setEntity(user);
                        baseEntity.setMessage("User has been logged successfuly.");
                        baseEntity.setSuccess(true);
                        
        			}else {
                        baseEntity.setMessage("Invalid username and/or password.");
                        baseEntity.setSuccess(false);

        			}
        			
        			
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
    	
    	if (userRepository.existsByUsernameIgnoreCase(username)) {
    		baseEntity.setMessage("Username already exist.");
    		baseEntity.setSuccess(false);
    		throw new Exception();
    	}
    }
    
    private void validateParameters(BaseEntity<User> baseEntity, User entity) throws Exception {
    	baseEntity.setMessage("");
    	if(StringUtils.isEmpty(entity.getUsername()) || StringUtils.isEmpty(entity.getPassword()) 
    			|| Role.getByName(entity.getRoleName()) == null) {
    		baseEntity.setMessage("Invalid Parameters");
    		
    	}else if(!checkIsLettersandNumbers(entity.getUsername())) {
    		baseEntity.setMessage("Username must contain letters and/or numbers without spaces.");
    		
    	}else  if(entity.getUsername().length()< 6 || entity.getUsername().length()> 10) {
        	baseEntity.setMessage("Username must contain minimum 6 and maximum 10 letters.");

    	}else  if(!checkIsLettersandNumbers(entity.getPassword())) {
    		baseEntity.setMessage("Password must contain letters and/or numbers without spaces.");
    		
    	}else if(entity.getPassword().length()< 6 || entity.getPassword().length()> 10) {
    		baseEntity.setMessage("Password must contain minimum 6 and maximum 10 letters.");
    		    		
    	}
    	
    	if(!StringUtils.isEmpty(baseEntity.getMessage())){
    		baseEntity.setSuccess(false);
    		throw new Exception();
    	}
    	
    }
    
    private boolean checkIsLettersandNumbers(String text) {
    	
    	Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");
    	Matcher matcher = pattern.matcher(text);
    	return matcher.matches();
    }

}
