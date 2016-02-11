package com.tolotra.algo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	@RequestMapping(value="/index" , method=RequestMethod.GET)
	public ModelAndView welcome() throws Exception
	{
		ModelAndView m = new ModelAndView("index");
		return m;
	}
	
	@RequestMapping(value="/clear" , method=RequestMethod.GET)
	public String logout(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/";
	}
}
