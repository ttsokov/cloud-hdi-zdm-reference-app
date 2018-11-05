package cloud_hdi_zdm_ref_app.resources;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import cloud_hdi_zdm_ref_app.dto.CdsPersonDto;
import cloud_hdi_zdm_ref_app.model.CdsPerson;

@Path("/cdsPerson")
public class CdsPersonTableResource {

	@EJB
	private CdsPersonDao cdsPersonDao;

	@GET
	@Path("/hello")
	@Produces("text/plain")
	public String getHello() {
		return "Hello World!";
	}

	@GET
	@Path("/")
	public Response getCdsPeople() {
		CdsPerson cdsPerson = cdsPersonDao.createTestData();

		return Response.status(Response.Status.OK).entity(new CdsPersonDto(cdsPerson)).build();
	}

	@GET
	@Path("/version")
	public Response getVersion() {
		return Response.status(Response.Status.OK).entity(new String("GREEN")).build();
	}
}
