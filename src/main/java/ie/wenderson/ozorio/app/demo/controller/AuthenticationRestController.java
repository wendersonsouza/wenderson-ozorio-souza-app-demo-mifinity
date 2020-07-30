package ie.wenderson.ozorio.app.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ie.wenderson.ozorio.app.demo.common.BaseEntity;
import ie.wenderson.ozorio.app.demo.entity.User;
import ie.wenderson.ozorio.app.demo.service.impl.UserServiceImpl;
import ie.wenderson.ozorio.app.demo.vo.LoginVO;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationRestController {
	
	@Autowired
	UserServiceImpl userService;
	
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<BaseEntity<User>> login(@RequestBody LoginVO login){
    	BaseEntity<User> base = userService.login(login);
        if(base.isSuccess()) {
        	return new ResponseEntity<BaseEntity<User>>(base, new HttpHeaders(), HttpStatus.OK);
        }else {
        	return new ResponseEntity<BaseEntity<User>>(base, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
