package propets.validation.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import propets.validation.model.Account;

public interface AccountingRepository extends MongoRepository<Account, String> {

}
