package com.MailSendr.MailSendr.controller;

import com.MailSendr.MailSendr.model.MailModel;
import com.MailSendr.MailSendr.model.MailFormModel;
import com.MailSendr.MailSendr.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/api/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/send")
    public String sendMail(@ModelAttribute MailFormModel mailForm, RedirectAttributes redirectAttributes) {
        MailModel mailModel = new MailModel();
        mailModel.setSubject(mailForm.getSubject());
        mailModel.setMessage(mailForm.getMessage());
        mailService.sendMail(mailForm.getTo(), mailModel);
        redirectAttributes.addFlashAttribute("successMessage", "Sent!");
        return "redirect:/api/mail/mailui";
    }

    @GetMapping("/mailui")
    public String mailUI(Model model){
        model.addAttribute("mailForm", new MailFormModel());
        return "index";
    }
}