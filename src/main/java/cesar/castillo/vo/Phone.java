package cesar.castillo.vo;

public class Phone {

	private String number;
	private String citycode;
	private String contrycode;
	
	public Phone() {
	}
	public Phone(String number, String citycode, String contrycode) {
		super();
		this.number = number;
		this.citycode = citycode;
		this.contrycode = contrycode;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCitycode() {
		return citycode;
	}
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	public String getContrycode() {
		return contrycode;
	}
	public void setContrycode(String contrycode) {
		this.contrycode = contrycode;
	}
	@Override
	public String toString() {
		return "Phone [number=" + number + ", citycode=" + citycode + ", contrycode=" + contrycode + "]";
	}
}
