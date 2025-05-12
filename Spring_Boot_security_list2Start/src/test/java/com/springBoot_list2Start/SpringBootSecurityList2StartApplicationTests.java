package com.springBoot_list2Start;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import domain.Student;
import service.StudentService;

@WebMvcTest
@Import(SecurityConfig.class)
class SpringBootSecurityListApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private StudentService mockService;

	@ParameterizedTest
	@CsvSource({ "/login, login", "/403, 403" })
	void testGetViews(String url, String expectedViewName) throws Exception {
		mockMvc.perform(get(url)).andExpect(status().isOk())
				.andExpect(view().name(expectedViewName));
	}

	@WithMockUser(username = "user", roles = { "USER", "ADMIN" })
	@Test
	void testAccessStudentListWithCorrectRole() throws Exception {
		mockMvc.perform(get("/students/list")).andExpect(status().isOk())
				.andExpect(view().name("grade/listStudents"))
				.andExpect(model().attributeExists("studentList"));
	}

	private Student createStudent() {
		return new Student(1, "firstName1", "lastName1", "name1@com.be");
	}

	@WithMockUser(username = "admin", roles = { "ADMIN" })
	@Test
	void testAccessOneStudentWithAdminRole() throws Exception {
		when(mockService.findById(1)).thenReturn(createStudent());
		mockMvc.perform(get("/students/1")).andExpect(status().isOk())
				.andExpect(view().name("grade/detailStudent"))
				.andExpect(model().attributeExists("student"))
				.andExpect(model().attribute("username", "admin"));
	}

	@WithMockUser
	@Test
	void testAccessOneStudentWithUserRole_NoAccess() throws Exception {
		when(mockService.findById(1)).thenReturn(createStudent());
		mockMvc.perform(get("/students/1")).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void testOneStudentWithAdminRole_StudentDoesNotExist() throws Exception {
		when(mockService.findById(99)).thenReturn(null);
		mockMvc.perform(get("/students/99")).andExpect(status().isFound())
				// .andExpect(view().name("redirect:/students/list"));
				.andExpect(redirectedUrl("/students/list"));
	}

	@WithAnonymousUser
	@Test
	void testNoAccessAnonymous() throws Exception {
		mockMvc.perform(get("/students/**")).andExpect(redirectedUrlPattern("**/login"));
	}
}
