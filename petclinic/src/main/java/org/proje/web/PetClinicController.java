package org.proje.web;

import org.proje.service.PetClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PetClinicController {

    @Autowired
    private PetClinicService petClinicService;

    @RequestMapping(value={"/","/index.html"})
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }

    @RequestMapping(value="/login.html")
    public ModelAndView loginPage() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    @RequestMapping("/pcs")
    @ResponseBody
    public String welcome() {
        return "Welcome to PetClinic World!";
    }

    @RequestMapping("/owners")
    public ModelAndView getOwners() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("owners", petClinicService.findOwners());
        mav.setViewName("owners");
        return mav;
    }
}
