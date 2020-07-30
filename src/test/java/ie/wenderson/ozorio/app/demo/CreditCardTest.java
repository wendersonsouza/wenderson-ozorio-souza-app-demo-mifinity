package ie.wenderson.ozorio.app.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import ie.wenderson.ozorio.app.demo.common.BaseEntity;
import ie.wenderson.ozorio.app.demo.entity.CreditCard;
import ie.wenderson.ozorio.app.demo.entity.Role;
import ie.wenderson.ozorio.app.demo.entity.User;
import ie.wenderson.ozorio.app.demo.repository.CreditCardRepository;
import ie.wenderson.ozorio.app.demo.repository.UserRepository;
import ie.wenderson.ozorio.app.demo.service.impl.CreditCardServiceImpl;

@Import(ApplicationTestConfiguration.class)
@RunWith(SpringRunner.class)
//@SpringBootTest 
public class CreditCardTest {
	
	@Autowired
	private CreditCardServiceImpl creditCardServiceImpl;
	
	@MockBean
	private CreditCardRepository creditCardRepository;
	
	@MockBean
	private UserRepository userRepository;
	

	@Before
    public void setup() throws Exception {
		
	}
	
	private List<CreditCard> loadAll(){
		
		List<CreditCard>  list =  new ArrayList<CreditCard>();
		list.add(new CreditCard(1l,"Holder A1", "1111111111111111", "2020/12", getAdmUser()));
		list.add(new CreditCard(2l,"Holder A2", "2222222222222222", "2021/01", getAdmUser()));
		list.add(new CreditCard(3l,"Holder O1", "3333333333333333", "2020/12", getOprUser()));
		list.add(new CreditCard(4l,"Holder O2", "4444444444444444", "2021/01", getOprUser()));
		return list;
	}
	
	@Test
	public void listbycredicard_number() {
		// Given
		when(userRepository.findById(1l)).thenReturn(Optional.of(getAdmUser()));
		when(creditCardRepository.findByNumberContaining("1111111111111111")).thenReturn(Arrays.asList(new CreditCard(1l, "Holder A1", "1111111111111111", "2020/12", getAdmUser())));

		
		
		// When
		User admin = getAdmUser();
		String creditCardNumber = "111";
		BaseEntity<List<CreditCard>> baseEntity = creditCardServiceImpl.listAllByUserAndNumber(admin.getId(), creditCardNumber);
		
		// Then
		assertTrue(baseEntity.isSuccess());
		assertThat(baseEntity.getEntity()).hasSize(1);
		assertThat(baseEntity.getEntity())
					.extracting(CreditCard::getId)
					.contains(1l);

		
	}
	

	
	private User getAdmUser() {
		return new User(1l, "useradm", "useradm", Role.AD);
	}
	
	private User getOprUser() {
		return new User(2l, "useropr", "useropr", Role.OP);
	}
	
	

}
