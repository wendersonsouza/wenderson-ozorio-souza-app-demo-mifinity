package ie.wenderson.ozorio.app.demo.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import ie.wenderson.ozorio.app.demo.common.BaseEntity;
import ie.wenderson.ozorio.app.demo.common.MonthsEnum;
import ie.wenderson.ozorio.app.demo.entity.CreditCard;
import ie.wenderson.ozorio.app.demo.repository.CreditCardRepository;

@Service
public class CreditCardServiceImpl extends GenericService {
	
	
	@Autowired
	private CreditCardRepository credCardRepository;
	
	
	public BaseEntity<List<MonthsEnum>> listAllMonths(){
    	BaseEntity<List<MonthsEnum>> baseEntity = new BaseEntity<List<MonthsEnum>>();
    	baseEntity.setEntity(Arrays.asList(MonthsEnum.values()));
		baseEntity.setMessage("Month list retrieved successfuly");
		baseEntity.setSuccess(true);
        return baseEntity;
	}
	
	
	public BaseEntity<List<CreditCard>> listAllByUserAndNumber(Long loggedUserId, String number){
    	BaseEntity<List<CreditCard>> baseEntity = new BaseEntity<List<CreditCard>>();
    	List<CreditCard> list = new ArrayList<CreditCard>();
    	try {
    		
    		if(isValidAdminUser(loggedUserId, baseEntity)) {
    			list.addAll(credCardRepository.findByNumberContaining(number));
    		}else {
    			list.addAll(credCardRepository.findByUserIdAndNumberContaining(loggedUserId,number));
    		}
    		baseEntity.setEntity(list);
    		baseEntity.setMessage(!CollectionUtils.isEmpty(list) ? "Credit card list retrieved successfuly" : "Credit Card not found.");
    		baseEntity.setSuccess(true);
    	}catch(Exception ex) {
    		if(StringUtils.isEmpty(baseEntity.getMessage())) {
    			baseEntity.setSuccess(false);
    			baseEntity.setMessage("Error on recovery CreditCard.");
    		}
    		
    	}
         return baseEntity;
	}
	
	public BaseEntity<CreditCard> retrieveByUserAndNumber(Long loggedUserId, Long id){
    	BaseEntity<CreditCard> baseEntity = new BaseEntity<CreditCard>();
    	Optional<CreditCard> entity = null;
    	try {
    		
    		if(isValidAdminUser(loggedUserId, baseEntity)) {
    			entity = credCardRepository.findById(id);
    		}else {
    			entity = credCardRepository.findByIdAndUserId(id,loggedUserId);
    		}
    		CreditCard creditcard = entity.orElse(null);
    		baseEntity.setEntity(creditcard);
    		baseEntity.setMessage(entity.isPresent() ? "Credit card retrieved successfuly" : "Credit Card not found.");
    		baseEntity.setSuccess(true);
    	}catch(Exception ex) {
    		if(StringUtils.isEmpty(baseEntity.getMessage())) {
    			baseEntity.setSuccess(false);
    			baseEntity.setMessage("Error on recovery CreditCard.");
    		}
    		
    	}
         return baseEntity;
	}
	
	public BaseEntity<CreditCard> update(Long loggedUserId, CreditCard credicard){
		BaseEntity<CreditCard> baseEntity = new BaseEntity<CreditCard>();
		try {
			Optional<CreditCard> entity = null;
			boolean isAdminUser = isValidAdminUser(loggedUserId, baseEntity);
			validateUpdateParameters(baseEntity, credicard);
			validateMonthAndYear(baseEntity, credicard.getYear(), credicard.getMonth());
			
			if(isAdminUser) {
				entity = credCardRepository.findById(credicard.getId());
			}else {
				entity = credCardRepository.findByIdAndUserId(credicard.getId(), loggedUserId);
			}
			
			if(entity.isPresent()){
				entity.get().setExpiryDate(CreditCard.expiryDateFormartter(credicard.getYear(), credicard.getMonth()));
				credCardRepository.save(entity.get());
				baseEntity.setMessage("Credit card updated successfuly.");
				baseEntity.setSuccess(true);
				
			}else{
				baseEntity.setMessage("Credit card not found.");
				baseEntity.setSuccess(false);
			}
		}catch(Exception ex) {
    		if(StringUtils.isEmpty(baseEntity.getMessage())) {
    			baseEntity.setSuccess(false);
    			baseEntity.setMessage("Error on updating creditCard details.");
    		}
    		
    	}
         return baseEntity;
	}
	
	public BaseEntity<CreditCard> create(Long loggedUserId, CreditCard credicard){
		BaseEntity<CreditCard> baseEntity = new BaseEntity<CreditCard>();
		try {
			isValidAdminUser(loggedUserId, baseEntity);
			validateInsertParameters(baseEntity, credicard);
			validateMonthAndYear(baseEntity, credicard.getYear(), credicard.getMonth());
			validateNumberExist(baseEntity, credicard.getNumber()); 
			credicard.setExpiryDate(expiryDateFormartter(credicard.getYear(), credicard.getMonth()));
			credicard.setOperatorUserId(loggedUserId);
			credCardRepository.save(credicard);
			baseEntity.setMessage("Credit card created successfuly.");
			baseEntity.setSuccess(true);
		}catch(Exception ex) {
    		if(StringUtils.isEmpty(baseEntity.getMessage())) {
    			baseEntity.setSuccess(false);
    			baseEntity.setMessage("Error on creating creditCard details.");
    		}
    		
    	}
         return baseEntity;
	}
	
	private void validateInsertParameters(BaseEntity<CreditCard> baseEntity, CreditCard credicard) throws Exception{
    	baseEntity.setMessage("");		
		if(StringUtils.isEmpty(credicard.getMonth()) || StringUtils.isEmpty(credicard.getYear())
				|| StringUtils.isEmpty(credicard.getHolderName()) || StringUtils.isEmpty(credicard.getNumber())) {
			baseEntity.setMessage("Invalid parameters.");
			

		}else if(credicard.getNumber().length() != 16 || !checkIsNumbers(credicard.getNumber())) {
			baseEntity.setMessage("Credit card number must contain 16 numeric digits.");
		} 

    	if(!StringUtils.isEmpty(baseEntity.getMessage())){
    		baseEntity.setSuccess(false);
    		throw new Exception();
    	}
	}
	
	private boolean checkIsNumbers(String text) {
    	
    	Pattern pattern = Pattern.compile("^[0-9]+$");
    	Matcher matcher = pattern.matcher(text);
    	return matcher.matches();
    }
	
	private void validateNumberExist(BaseEntity<CreditCard> baseEntity, String number) throws Exception{
		  
		if(!isNumeric(number)) {
			baseEntity.setMessage("Invalid card credit number.");
		}else if (credCardRepository.existsByNumber(number)) {
			baseEntity.setMessage("Credit Card number already exist.");
		}
		
		if(!StringUtils.isEmpty(baseEntity.getMessage())) {
			baseEntity.setSuccess(false);
			throw new Exception();
		}
	}
	
	private void validateUpdateParameters(BaseEntity<CreditCard> baseEntity, CreditCard credicard) throws Exception{
		if(credicard.getId() == null || StringUtils.isEmpty(credicard.getMonth())
				|| StringUtils.isEmpty(credicard.getYear())) {
			baseEntity.setMessage("Invalid parameters.");
			baseEntity.setSuccess(false);
			throw new Exception();
		}
	}

	private void validateMonthAndYear(BaseEntity<CreditCard> baseEntity, String year , String month) throws Exception{
		
		baseEntity.setMessage("");
		try {
			LocalDate currentdate = LocalDate.now();
			int convertedYear = Integer.parseInt(year);
			int convertedMonth = Integer.parseInt(month);
			
			if(convertedMonth < 1 || convertedMonth > 12 ) {
				baseEntity.setMessage("Invalid month.");
			}else
			if(convertedYear < currentdate.getYear()) {
				baseEntity.setMessage("Year can not be before current year.");
			}else
			if(convertedYear == currentdate.getYear() &&  convertedMonth < currentdate.getMonthValue()) {
				baseEntity.setMessage("Expiry date can not be before current date.");		
			}
			
		}catch(NumberFormatException ex) {
			baseEntity.setMessage("Invalid Expiry date.");
		}
		
		if(!StringUtils.isEmpty(baseEntity.getMessage())) {
			baseEntity.setSuccess(false);
			throw new Exception();
		}
	}
	
	private String expiryDateFormartter(String year,String month) {
		LocalDate currentdate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month),1);
		return String.format("%ty", currentdate) + "/" + String.format("%tm", currentdate);
	}
	
	public static boolean isNumeric(final String str) {

        // null or empty
        if (str == null || str.length() == 0) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;

    }
}
