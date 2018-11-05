package cloud_hdi_zdm_ref_app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "\"CDS_PERSON_TABLE\"")
public class CdsPerson implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "\"ID\"", nullable = false)
	private int id;

	@Column(name = "\"FIRST_NAME\"", length = 20)
	private String firstName;

	@Column(name = "\"LAST_NAME\"", length = 20)
	private String lastName;

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
