package com.cafe24.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cafe24.mysite.service.GuestbookService;
import com.cafe24.mysite.vo.GuestbookVO;

@Controller
@RequestMapping( "/guestbook" )
public class GuestbookController {
    @Autowired
    private GuestbookService gbService;

    @RequestMapping( "/list" )
    public String list( Model model ) {
	List<GuestbookVO> list = gbService.getList();
	model.addAttribute( "list", list );
	return "guestbook/list";
    }

    @RequestMapping( value = "/write", method = RequestMethod.POST )
    public String write( @ModelAttribute GuestbookVO vo ) {
	gbService.write( vo );
	return "redirect:/guestbook/list";
    }

    @RequestMapping( value = "/delete/{no}", method = RequestMethod.GET )
    public String delete( @PathVariable Long no, Model model ) {
	model.addAttribute( "no", no );
	return "guestbook/delete";
    }

    @RequestMapping( value = "/delete", method = RequestMethod.POST )
    public String delete( @ModelAttribute GuestbookVO vo, Model model ) {
	gbService.delete( vo );
	return "redirect:/guestbook/list";
    }

}
