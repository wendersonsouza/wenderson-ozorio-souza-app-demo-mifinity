package ie.wenderson.ozorio.app.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ie.wenderson.ozorio.app.demo.common.BaseEntity;
import ie.wenderson.ozorio.app.demo.entity.Role;
import ie.wenderson.ozorio.app.demo.entity.User;
import ie.wenderson.ozorio.app.demo.service.impl.UserServiceImpl;

@RestController
@RequestMapping("api/v1/user")
public class UserRestController {
	
	@Autowired
	UserServiceImpl userService;
	
    @RequestMapping(value = "/all/{loggedUserId}", method = RequestMethod.GET)	    
    public ResponseEntity<BaseEntity<List<User>>> getAllUsers(@PathVariable("loggedUserId") Long loggedUserId) {
    	BaseEntity<List<User>> base = userService.getAll(loggedUserId);
        	return new ResponseEntity<BaseEntity<List<User>>>(base, new HttpHeaders(), HttpStatus.OK);
        
    }
    
    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity<BaseEntity<User>> create(@RequestBody User user){
    	BaseEntity<User> base = userService.create(user);
        	return new ResponseEntity<BaseEntity<User>>(base, new HttpHeaders(), HttpStatus.OK);
        
    }
    
    @RequestMapping(value = "/roles", method = RequestMethod.GET)	    
    public ResponseEntity<BaseEntity<List<Role>>> gettAllRoles(){
    	BaseEntity<List<Role>> base = userService.gettAllRoles();
        return new ResponseEntity<BaseEntity<List<Role>>> (base, new HttpHeaders(), HttpStatus.OK);
        
    }

}
