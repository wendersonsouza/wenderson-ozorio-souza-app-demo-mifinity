package ie.wenderson.ozorio.app.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.wenderson.ozorio.app.demo.entity.CreditCard;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long>{
	
	public List<CreditCard> findByNumberContaining(String number);
	
	public List<CreditCard> findByUserIdAndNumberContaining(Long userId, String number);
	
	public Optional<CreditCard> findByIdAndUserId(Long id, Long userId);
	
	public CreditCard findByNumber(String number);
	
	public boolean existsByNumber(String number);

}
