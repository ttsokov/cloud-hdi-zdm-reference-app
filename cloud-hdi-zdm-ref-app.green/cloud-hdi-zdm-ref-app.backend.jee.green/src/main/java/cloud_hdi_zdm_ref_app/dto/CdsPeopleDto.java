package cloud_hdi_zdm_ref_app.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlAccessorType(XmlAccessType.FIELD)
public class CdsPeopleDto {

	@XmlElementWrapper(name = "cdsPeople")
	@XmlElement(name = "cdsPerson")
	private List<CdsPersonDto> cdsPeople;

	public CdsPeopleDto() {
		// Required by JAXB
	}

	public List<CdsPersonDto> getCdsPeople() {
		return cdsPeople;
	}

	public void setCdsPeople(List<CdsPersonDto> cdsPeople) {
		this.cdsPeople = cdsPeople;
	}
}
