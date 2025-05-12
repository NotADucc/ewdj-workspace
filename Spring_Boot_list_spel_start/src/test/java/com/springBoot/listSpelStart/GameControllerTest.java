package com.springBoot.listSpelStart;
import static domain.InitGame.DEFAULT_NUMBER;
import static domain.InitGame.MAX_NUMBER;
import static domain.InitGame.MIN_NUMBER;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import domain.Game;
import exception.IndexNotFoundException;

@WebMvcTest(GameController.class)
class GameControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testGetRequest() throws Exception {
		mockMvc.perform(get("/game"))
		.andExpect(view().name("startGame"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("game"));
	}

	@ParameterizedTest
	@ValueSource(ints = { MIN_NUMBER, MAX_NUMBER, DEFAULT_NUMBER })
    public void testPostRequestValidGame(Integer number) throws Exception {	
		Game validGame = new Game(number);
        mockMvc.perform(post("/game").flashAttr("game", validGame))
                .andExpect(status().isOk())
                 .andExpect(view().name("game"))
                .andExpect(model().attributeExists("game"));
    }
	
	@ParameterizedTest
	@ValueSource(ints = { MIN_NUMBER-1, MAX_NUMBER+1})
    public void testPostRequestInvalidGame(Integer number) throws Exception {	
		Game validGame = new Game(number);
        mockMvc.perform(post("/game").flashAttr("game", validGame))
                .andExpect(status().isOk())
                 .andExpect(view().name("startGame"))
                .andExpect(model().attributeExists("game"));
    }
	
	@Test
    public void validPlayNotEndGame() throws Exception {
        Game mockGame = mock(Game.class);
        when(mockGame.isEndGame()).thenReturn(false);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("game", mockGame);

        mockMvc.perform(get("/game/1").session(session).flashAttr("game", mockGame))
                .andExpect(status().isOk())
                .andExpect(view().name("game"))
                .andExpect(request().sessionAttribute("game", mockGame));

        verify(mockGame).play(1);
    }

	@ParameterizedTest
	@CsvSource({"true, win", "false, lose"})
    public void validPlayEndGame(boolean win, String expectedResult) throws Exception 
	{
		Game mockGame = mock(Game.class);
        when(mockGame.isEndGame()).thenReturn(true);
        when(mockGame.isWin()).thenReturn(win);
        
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("game", mockGame);

        mockMvc.perform(get("/game/1").session(session).flashAttr("game", mockGame))
                .andExpect(status().isOk())
                .andExpect(view().name("game"))
                .andExpect(model().attributeExists("gameStatus"))
                .andExpect(model().attribute("gameStatus", expectedResult))
                .andExpect(request().sessionAttributeDoesNotExist("game"));

        verify(mockGame).play(1);
        verify(mockGame).isEndGame();
        verify(mockGame).isWin();
    }
	
	
    @Test
    public void invalidPlay() throws Exception {
        Game mockGame = mock(Game.class);
        doThrow(new IndexNotFoundException()).when(mockGame).play(-1);

        mockMvc.perform(get("/game/-1").flashAttr("game", mockGame))
        .andExpect(view().name("error/error"));
    }
    
}
