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
import ie.wenderson.ozorio.app.demo.entity.CreditCard;
import ie.wenderson.ozorio.app.demo.service.impl.CreditCardServiceImpl;

@RestController
@RequestMapping("api/v1/creditcard")
public class CreditCardRestController {
	
	@Autowired
	private CreditCardServiceImpl creditCardService;
	
    @RequestMapping(value = "/all/{loggedUserId}/{number}", method = RequestMethod.GET)	    
    public ResponseEntity<BaseEntity<List<CreditCard>>> listByUserAndNumber(@PathVariable("loggedUserId") Long loggedUserId,
    																        @PathVariable("number") String number){
    	BaseEntity<List<CreditCard>> base = creditCardService.listAllByUserAndNumber(loggedUserId, number);
    	HttpStatus status =  base.isSuccess() ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<BaseEntity<List<CreditCard>>>(base, new HttpHeaders(), status);
        
    }
	
    @RequestMapping(value = "/{loggedUserId}", method = RequestMethod.POST)
    public ResponseEntity<BaseEntity<CreditCard>> create(@PathVariable("loggedUserId") Long loggedUserId, @RequestBody CreditCard creditCard){
    	BaseEntity<CreditCard> base = creditCardService.create(loggedUserId, creditCard);
    	HttpStatus status =  base.isSuccess() ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
    	return new  ResponseEntity<BaseEntity<CreditCard>>(base, new HttpHeaders(), status);
    }
    
    @RequestMapping(value = "/{loggedUserId}", method = RequestMethod.PUT)
    public ResponseEntity<BaseEntity<CreditCard>> update(@PathVariable("loggedUserId") Long loggedUserId, @RequestBody CreditCard creditCard){
    	BaseEntity<CreditCard> base = creditCardService.update(loggedUserId, creditCard);
    	HttpStatus status =  base.isSuccess() ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
    	return new  ResponseEntity<BaseEntity<CreditCard>>(base, new HttpHeaders(), status);
    	
    }
	
}
