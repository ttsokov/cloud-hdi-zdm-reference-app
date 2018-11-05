package cloud_hdi_zdm_ref_app.resources;

import java.util.List;
import java.util.Random;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import cloud_hdi_zdm_ref_app.model.CdsPerson;

@Stateless
public class CdsPersonDao {

	@PersistenceContext(name = "cloud-hdi-zdm-ref-app")
	private EntityManager entityManager;

	public CdsPerson createTestData() {
		Random random = new Random();

		CdsPerson cdsPerson = new CdsPerson();
		cdsPerson.setId(random.nextInt(10000));
		cdsPerson.setFirstName(String.format("FirstName #%d", cdsPerson.getId()));
		cdsPerson.setLastName(String.format("LastName #%d", cdsPerson.getId()));

		entityManager.persist(cdsPerson);
		return cdsPerson;
	}

	public void deleteAllData() {
		entityManager.createNativeQuery("DELETE FROM \"CDS_PERSON_TABLE\"").executeUpdate();
		return;
	}

	public List<CdsPerson> findAllPeople() {
		TypedQuery<CdsPerson> query = entityManager.createQuery("SELECT p FROM CdsPerson p", CdsPerson.class);
		return query.getResultList();
	}
}
