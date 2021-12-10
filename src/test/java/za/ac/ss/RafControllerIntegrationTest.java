package za.ac.ss;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import za.ac.ss.entities.RoadAccidentFund;

@Profile("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "security.basic.enabled=false"
})
@RunWith(SpringRunner.class)
public class RafControllerIntegrationTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	//@Sql(scripts={"classpath:test.sql"})
	public void getRafByIdTest() {
		ResponseEntity<RoadAccidentFund> response = testRestTemplate.getForEntity("/raf/id/1", RoadAccidentFund.class);

		assertEquals(1, response.getBody().getId());
		//assertEquals("Seleta", response.getBody().getCreatedBy());
		//assertEquals("2020-08-21 22:51:26.828", response.getBody().getCreatedDate());
		//assertEquals("Seleta", response.getBody().getLastModifiedBy());
		//assertEquals("2020-08-22 22:51:26.828", response.getBody().getLastModifiedDate());
		assertEquals("2020-08-25 22:51:26.828", response.getBody().getClaimLodgeDate());
		assertEquals("2020-08-30 22:51:26.828", response.getBody().getCourtDate());
		assertEquals("2020-06-21 22:51:26.828", response.getBody().getDateOfAccident());
		assertEquals("2020-08-21 22:51:26.828", response.getBody().getDateSubmited());
		assertEquals("Helen Franz Hospital", response.getBody().getHospital());
		assertEquals("0155050152", response.getBody().getHospitalContactNumber());
		assertEquals("TCP/100", response.getBody().getReferenceNumber());
		assertEquals(0, response.getBody().getVersion());
		assertEquals("Driver", response.getBody().getMerit());
		assertEquals(1, response.getBody().getCategory());
		assertEquals("IN_PROCESS", response.getBody().getLitigationProcess());
	}
	
	public void saveRafTest() {
		RoadAccidentFund roadAccident = new RoadAccidentFund();
		roadAccident.setClaimLodgeDate(LocalDateTime.now());
		roadAccident.setCourtDate(new Date());
		roadAccident.setHospital("Hospital 2");
		roadAccident.setHospitalContactNumber("01264836834");
		roadAccident.setReferenceNumber("TCP/3898");
		roadAccident.setVersion(0L);
		roadAccident.setMerit("Driver");
		
		//ResponseEntity<RoadAccidentFund>
	}
	
	
}
