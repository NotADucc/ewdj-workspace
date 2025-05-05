package com.springBoot_list2Start;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@Import(SecurityConfig.class)
class SpringBootSecurityListApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void loginGet() throws Exception {
	    	mockMvc.perform(get("/login"))
	        .andExpect(status().isOk())
	    	.andExpect(view().name("login"));
	}
	 
	@Test
	public void accessDeniedPageGet() throws Exception {
	    	mockMvc.perform(get("/403"))
	        .andExpect(status().isOk())
	    	.andExpect(view().name("403"));
	}
	 
	@WithMockUser(username = "user", roles = {"USER", "ADMIN"})
    @Test
    public void testAccessStudentListWithCorrectRole() throws Exception {
        mockMvc.perform(get("/students/list"))
        .andExpect(status().isOk()).
         andExpect(view().name("grade/listStudents")).
         andExpect(model().attributeExists("studentList"));
    }
	
	@WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    public void testAccessOneStudentWithAdminRole() throws Exception {
        mockMvc.perform(get("/students/1"))
        .andExpect(status().isOk())
    	.andExpect(view().name("grade/detailStudent"))
    	.andExpect(model().attributeExists("student"))
    	.andExpect(model().attribute("username", "admin"));
    }
	
	@WithMockUser(username = "admin", roles = {"USER"})
    @Test
    public void testAccessOneStudentWithUserRole_NoAccess() throws Exception {
        mockMvc.perform(get("/students/1"))
        .andExpect(status().isForbidden());
    }
	
	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	public void testOneStudentWithAdminRole_StudentDoesNotExist() throws Exception {
	    	mockMvc.perform(get("/students/99"))
	    	.andExpect(status().isFound())
	    	//.andExpect(view().name("redirect:/students/list"));
	    	.andExpect(redirectedUrl("/students/list"));
	}
	
	@WithAnonymousUser
	@Test
	public void testNoAccessAnonymous() throws Exception {
	    mockMvc.perform(get("/students/**"))
	          .andExpect(redirectedUrlPattern("**/login"));
	}
}
