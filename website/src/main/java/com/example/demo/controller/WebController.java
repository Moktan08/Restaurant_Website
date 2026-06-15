package com.example.demo.controller;

import com.example.demo.model.ContactMessage;
import com.example.demo.model.MenuItem;
import com.example.demo.model.Reserve;
import com.example.demo.service.ContactMessageService;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.MenuItemService;
import com.example.demo.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class WebController {

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ReserveService reserveService;

    @Autowired
    private ContactMessageService contactMessageService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }

    @GetMapping("/contact")
    public String contactPage() {
        return "contact";
    }

    @GetMapping("/menu")
    public String showMenu(Model model) {
        List<MenuItem> breakfastItems = menuItemService.findByCategory("breakfast");
        List<MenuItem> lunchItems = menuItemService.findByCategory("lunch");
        List<MenuItem> dinnerItems = menuItemService.findByCategory("dinner");
        model.addAttribute("breakfastItems", breakfastItems);
        model.addAttribute("lunchItems", lunchItems);
        model.addAttribute("dinnerItems", dinnerItems);
        return "menu";
    }

    @GetMapping("/reservation")
    public String reservationPage(Model model) {
        // This provides the blank form object that th:object="${reservation}" expects
        model.addAttribute("reservation", new Reserve()); 
        return "reservation"; 
    }

    @PostMapping("/reserve")
    public String handleReservation(@ModelAttribute Reserve reserve,
                                    @RequestParam("number") String phone,
                                    @RequestParam("numOfPeople") Integer numOfPeople,
                                    @RequestParam("date") String dateStr,
                                    @RequestParam("time") String timeStr) {
        try {
            reserve.setPhone(phone);
            reserve.setNumOfPeople(numOfPeople);

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            reserve.setDate(LocalDate.parse(dateStr, dateFormatter));

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            reserve.setTime(LocalTime.parse(timeStr, timeFormatter));

            reserveService.save(reserve);
        } catch (Exception e) {
            System.out.println("Error saving reservation: " + e.getMessage());
        }
        return "redirect:/reservation?success";
    }

    @GetMapping("/staff")
    public String staffPage(Model model) {
        model.addAttribute("chefs", employeeService.findByEmployeeType("chef"));
        model.addAttribute("others", employeeService.findByEmployeeType("other"));
        return "staff";
    }

    @PostMapping("/subscribe")
    public String handleSubscribe(@RequestParam("email") String email) {
        System.out.println("New subscriber email: " + email);
        return "redirect:/";
    }

    @PostMapping("/contact")
    public String handleContactForm(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("subject") String subject,
            @RequestParam("message") String message) {

        ContactMessage contactMessage = new ContactMessage();
        contactMessage.setName(name);
        contactMessage.setEmail(email);
        contactMessage.setSubject(subject);
        contactMessage.setMessage(message);
        contactMessageService.save(contactMessage);

        return "redirect:/contact?success";
    }
}