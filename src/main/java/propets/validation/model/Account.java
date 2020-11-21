package propets.validation.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = {"email"})
@Document(collection = "accounts")
public class Account implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1755964199555731373L;

	@Value("${block.period}")
	int blockPeriod;
	
	@Id
	String email;
	String password;
	String name;
	String avatar;
	String phone;
	Set<String> roles = new HashSet<>();
	Map<String, Set<String>> favourites = new HashMap<>();
	Map<String, Set<String>> activities = new HashMap<>();
	boolean flBlocked;
	long timeUnblock;
	
}
