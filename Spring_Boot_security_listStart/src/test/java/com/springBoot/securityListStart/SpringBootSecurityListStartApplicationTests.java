package com.springBoot.securityListStart;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@Import(SecurityConfig.class)
class SpringBootSecurityListStartApplicationTests {

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
	
//TODO	
	@Test
    public void testAccessOneStudentWithAdminRole() throws Exception {
        //"/students/1"
    }
	
	@Test
    public void testAccessOneStudentWithUserRole_NoAccess() throws Exception {
		 //"/students/1"
    }
	
	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	public void testOneStudentWithAdminRole_StudentDoesNotExist() throws Exception {
	    //"/students/99"
	}
	
	@Test
	public void testNoAccessAnonymous() throws Exception {
	    //"/students/**"
	      
	}

}