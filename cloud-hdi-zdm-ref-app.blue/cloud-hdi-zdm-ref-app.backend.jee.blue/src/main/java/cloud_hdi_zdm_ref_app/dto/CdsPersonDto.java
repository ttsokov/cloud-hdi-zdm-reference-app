package cloud_hdi_zdm_ref_app.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import cloud_hdi_zdm_ref_app.model.CdsPerson;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "cdsPerson")
public class CdsPersonDto {

	@XmlElement(name = "id")
	private int id;

	@XmlElement(name = "firstName")
	private String firstName;

	@XmlElement(name = "lastName")
	private String lastName;

	protected CdsPersonDto() {
		// Required by JAXB;
	}

	public CdsPersonDto(CdsPerson cdsPerson) {
		this.id = cdsPerson.getId();
		this.firstName = cdsPerson.getFirstName();
		this.lastName = cdsPerson.getLastName();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
