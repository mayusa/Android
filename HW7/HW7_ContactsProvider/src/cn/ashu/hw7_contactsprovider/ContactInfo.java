package cn.ashu.hw7_contactsprovider;

public class ContactInfo {
	private int id;
	private String name;
	private String phone;	
	private String email;	
	private String postal;
	public ContactInfo() {
	}
	
	public ContactInfo(String name, String phone, String email, String postal) {
		super();
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.postal = postal;
	}
	public ContactInfo(int id, String name, String phone, String email, String postal) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.postal = postal;
	}	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPostal() {
		return postal;
	}
	public void setPostal(String postal) {
		this.postal = postal;
	}	
}
