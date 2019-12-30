package cesar.castillo.vo;

import java.util.ArrayList;
import java.util.List;

public class User {

	private String name;
	private String email;
	private String password;
	private List<Phone> phones = new ArrayList<Phone>();
	
	public User() {
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Phone> getPhones() {
		return phones;
	}
	public void add(Phone phone) {
		this.phones.add( phone );
	}
	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + ", password=" + password + ", phones=" + phones + "]";
	}
}
