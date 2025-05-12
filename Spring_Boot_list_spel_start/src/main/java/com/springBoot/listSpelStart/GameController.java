package com.springBoot.listSpelStart;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import domain.Game;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/game")
@SessionAttributes("game")
public class GameController {
	@GetMapping
	public String initGame(Model model) {
		model.addAttribute("game", new Game());
		return "startGame";
	}

	@PostMapping
	public String onSubmit(@Valid Game game, BindingResult result) {
		if (result.hasErrors()) {
			return "startGame";
		}
		game.startGame();
		return "game";
	}

	@GetMapping("/{index}")
	public String getMethodName(
			@PathVariable Integer index,
			Game game,
			Model model,
			SessionStatus status
	) {
		game.play(index);

		if (game.isEndGame()) {
			model.addAttribute("gameStatus", game.isWin() ? "win" : "lose");
			status.setComplete();
		}
		return "game";
	}

}