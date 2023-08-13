package com.lms.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.app.dto.CustomerResponse;
import com.lms.app.dto.DrawResponse;
import com.lms.app.dto.DrawWinnerResponse;
import com.lms.app.dto.GenerateTicketsResponse;
import com.lms.app.dto.GetActiveDrawsResponse;
import com.lms.app.dto.LicenseResponse;
import com.lms.app.dto.PurchaseTicketRequest;
import com.lms.app.dto.PurchaseTicketResponse;
import com.lms.app.dto.ResponseDTO;
import com.lms.app.dto.TicketOwnerResponse;
import com.lms.app.dto.WinningTicket;
import com.lms.app.entity.Customer;
import com.lms.app.entity.Draw;
import com.lms.app.entity.License;
import com.lms.app.entity.TicketOwner;
import com.lms.app.service.LmsServiceImpl;

import reactor.core.publisher.Flux;

@SpringBootTest
@AutoConfigureMockMvc
public class LmsControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LmsServiceImpl lmsService;

	@Test
	public void testCreateLicense() throws Exception {

		License license = new License();
		license.setLicenseKey("L1");
		license.setMaxTickets(100);
		license.setValidityPeriod(new Timestamp(Calendar.getInstance().getTimeInMillis()));

		LicenseResponse response = new LicenseResponse();
		response.setResponseCode(0);
		response.setResponseMessage("License created successfully");
		response.setLicenseID(1L);

		when(lmsService.createLicense(any(License.class))).thenReturn(response);

		mockMvc.perform(MockMvcRequestBuilders.post("/lms/license/create").content(asJsonString(license))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value(0))
				.andExpect(MockMvcResultMatchers.jsonPath("$.licenseID").value(1L));
	}

	@Test
	public void testCreateCustomer() throws Exception {

		License license = new License();
		license.setLicenseKey("L1");
		license.setMaxTickets(100);
		license.setValidityPeriod(new Timestamp(Calendar.getInstance().getTimeInMillis()));

		Customer customer = new Customer();
		customer.setCustomerIdentity("C1");
		customer.setCustomerName("cus001");
		customer.setPaymentMethod("BankAccNo");
		customer.setLicense(license);

		CustomerResponse response = new CustomerResponse();
		response.setResponseCode(0);
		response.setResponseMessage("Customer created successfully");
		response.setCustomerID(1L);

		when(lmsService.createCustomer(any(Customer.class))).thenReturn(response);

		mockMvc.perform(MockMvcRequestBuilders.post("/lms/customer/create").content(asJsonString(customer))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode").value(0)).andExpect(jsonPath("$.customerID").value(1L));
	}

	@Test
	public void testCreateDraw() throws Exception {

		Draw draw = new Draw();
		draw.setDrawNumber("D123");
		draw.setMaxTickets(100l);
		draw.setStartDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		draw.setEndDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));

		DrawResponse response = new DrawResponse();
		response.setResponseCode(0);
		response.setResponseMessage("Draw created successfully");
		response.setDrawID(1L);

		when(lmsService.createDraw(any(Draw.class))).thenReturn(response);

		mockMvc.perform(MockMvcRequestBuilders.post("/lms/draw/create").content(asJsonString(draw))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode").value(0)).andExpect(jsonPath("$.drawID").value(1L));
	}

	@Test
	public void testGenerateTicketsForDraw() throws Exception {
		
		Draw draw = new Draw();
		draw.setDrawNumber("D123");

		GenerateTicketsResponse response = new GenerateTicketsResponse();
		response.setResponseCode(0);
		response.setResponseMessage("Tickets generated successfully");
		response.setTicketsGenerated(100); // Set the number of generated tickets

		when(lmsService.generateTicketsforDrawNumber(draw.getDrawNumber())).thenReturn(response);

		mockMvc.perform(MockMvcRequestBuilders.post("/lms/draw/generateTickets").content(asJsonString(draw))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode").value(0)).andExpect(jsonPath("$.ticketsGenerated").value(100)); // Adjust
																														// the
	}

	@Test
	public void testCreateTicketOwner() throws Exception {

		License license = new License();
		license.setLicenseKey("L1");
		license.setMaxTickets(100);
		license.setValidityPeriod(new Timestamp(Calendar.getInstance().getTimeInMillis()));

		Customer customer = new Customer();
		customer.setCustomerIdentity("C1");
		customer.setCustomerName("cus001");
		customer.setPaymentMethod("BankAccNo");
		customer.setLicense(license);
		
		TicketOwner ticketOwner = new TicketOwner();
		ticketOwner.setTicketOwnerIdentity("T123");
		ticketOwner.setCustomer(customer);
		
		TicketOwnerResponse response = new TicketOwnerResponse();
		response.setResponseCode(0);
		response.setResponseMessage("Ticket owner created successfully");
		response.setTicketOwnerID(1L);

		when(lmsService.createTicketOwner(any(TicketOwner.class))).thenReturn(response);

		mockMvc.perform(MockMvcRequestBuilders.post("/lms/ticketowner/create").content(asJsonString(ticketOwner))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode").value(0)).andExpect(jsonPath("$.ticketOwnerID").value(1L));
	}

	@Test
	public void testAssignTicketToTicketOwner() throws Exception {
		PurchaseTicketRequest purchaseTicketRequest = new PurchaseTicketRequest();
		purchaseTicketRequest.setDrawNumber("D123");
		purchaseTicketRequest.setTicketOwnerIdentity("T0123");

		PurchaseTicketResponse response = new PurchaseTicketResponse();
		response.setResponseCode(0);
		response.setResponseMessage("Ticket assigned successfully");
		response.setTicketNumber("T12345");

		when(lmsService.purchaseTicket(purchaseTicketRequest.getDrawNumber(),
				purchaseTicketRequest.getTicketOwnerIdentity())).thenReturn(response);

		mockMvc.perform(MockMvcRequestBuilders.post("/lms/purchaseticket").content(asJsonString(purchaseTicketRequest))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode").value(0)).andExpect(jsonPath("$.ticketNumber").value("T12345"));
	}

	@Test
	public void testSelectWinnerForDraw() throws Exception {
		
		Draw draw = new Draw();
		draw.setDrawNumber("D123");

		DrawWinnerResponse response = new DrawWinnerResponse();
		response.setResponseCode(0);
		response.setResponseMessage("Winner selected successfully");
		response.setTicketNumber("T12345");
		response.setCustomerIdentity("C123");
		response.setTicketOwnerIdentity("TO123");

		when(lmsService.selectWinnerForDraw(any(Draw.class))).thenReturn(response);

		mockMvc.perform(MockMvcRequestBuilders.post("/lms/draw/winner").content(asJsonString(draw))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode").value(0)).andExpect(jsonPath("$.ticketNumber").value("T12345"))
				.andExpect(jsonPath("$.customerIdentity").value("C123"))
				.andExpect(jsonPath("$.ticketOwnerIdentity").value("TO123"));
	}

	@Test
	public void testGetActiveDrawList() throws Exception {
		GetActiveDrawsResponse response = new GetActiveDrawsResponse();
		response.setResponseCode(0);
		response.setResponseMessage("Active draw list retrieved successfully");

		List<Draw> activeDraws = new ArrayList<>();
		// Populate activeDraws with actual Draw objects
		Draw draw1 = new Draw();
		draw1.setDrawNumber("D123");
		draw1.setMaxTickets(100l);

		Draw draw2 = new Draw();
		draw2.setDrawNumber("D456");
		draw2.setMaxTickets(200l);

		activeDraws.add(draw1);
		activeDraws.add(draw2);

		response.setDraws(activeDraws);

		when(lmsService.getActiveDrawList()).thenReturn(response);

		mockMvc.perform(MockMvcRequestBuilders.get("/lms/draw/getActiveDraws").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.responseCode").value(0))
				.andExpect(jsonPath("$.draws[0].drawNumber").value("D123"))
				.andExpect(jsonPath("$.draws[0].maxTickets").value(100))
				.andExpect(jsonPath("$.draws[1].drawNumber").value("D456"))
				.andExpect(jsonPath("$.draws[1].maxTickets").value(200));
	}

	@Test
	public void testCheckWinner() throws Exception {
		WinningTicket winningTicket = new WinningTicket();
		winningTicket.setTicketNumber("T12345");
		winningTicket.setDrawNumber("D456");
		winningTicket.setTicketOwnerIdentity("TO123");

		Flux<WinningTicket> winningTicketFlux = Flux.just(winningTicket);

		when(lmsService.checkForWinningTicketForCustomer("C123")).thenReturn(winningTicket);

		mockMvc.perform(get("/lms/checkwinner/C123").contentType(MediaType.TEXT_EVENT_STREAM))
				.andExpect(status().isOk());

		// Since SSE is a continuous stream, you can't directly match content
		// Instead, you can use print to see the events in the console
		winningTicketFlux.doOnNext(System.out::println).subscribe();
	}

	@Test
	public void testCheckVersion() throws Exception {
		String appVersion = "v1"; // The expected version to match

		ResponseDTO response = new ResponseDTO();
		response.setResponseCode(0);
		response.setResponseMessage("App version is latest");

		mockMvc.perform(
				MockMvcRequestBuilders.get("/lms/checkversion/" + appVersion).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.responseCode").value(0))
				.andExpect(jsonPath("$.responseMessage").value("App version is latest"));
	}

	// Helper method to convert objects to JSON strings
	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
