package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final String UPLOAD_DIR = "src/main/resources/static/images/";

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ReserveService reserveService;

    @Autowired
    private ContactMessageService contactMessageService;

    // ===================== LOGIN =====================
    @GetMapping("/login")
    public String loginPage() {
        return "admin/login";
    }

    // ===================== DASHBOARD =====================
    @GetMapping
    public String adminDashboard(Model model) {
        model.addAttribute("menuCount", menuItemService.findAll().size());
        model.addAttribute("staffCount", employeeService.findAll().size());
        model.addAttribute("reservationCount", reserveService.findAll().size());
        model.addAttribute("messageCount", contactMessageService.findAll().size());
        return "admin/dashboard";
    }

    // ===================== MENU ITEMS =====================
    @GetMapping("/menu")
    public String listMenuItems(Model model) {
        model.addAttribute("menuItems", menuItemService.findAll());
        return "admin/menu";
    }

    @GetMapping("/menu/add")
    public String showAddMenuItemForm(Model model) {
        model.addAttribute("menuItem", new MenuItem());
        return "admin/menu-form";
    }

    @PostMapping("/menu/add")
    public String addMenuItem(@ModelAttribute MenuItem menuItem,
                              @RequestParam("imageFile") MultipartFile imageFile,
                              RedirectAttributes redirectAttributes) {
        try {
            if (!imageFile.isEmpty()) {
                menuItem.setImagePath(saveImage(imageFile, "menu"));
            }
            menuItemService.save(menuItem);
            redirectAttributes.addFlashAttribute("success", "Menu item added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding menu item: " + e.getMessage());
        }
        return "redirect:/admin/menu";
    }

    @GetMapping("/menu/edit/{id}")
    public String showEditMenuItemForm(@PathVariable Long id, Model model) {
        Optional<MenuItem> menuItem = menuItemService.findById(id);
        if (menuItem.isPresent()) {
            model.addAttribute("menuItem", menuItem.get());
            return "admin/menu-form";
        }
        return "redirect:/admin/menu";
    }

    @PostMapping("/menu/edit/{id}")
    public String updateMenuItem(@PathVariable Long id,
                                 @ModelAttribute MenuItem menuItem,
                                 @RequestParam("imageFile") MultipartFile imageFile,
                                 RedirectAttributes redirectAttributes) {
        try {
            Optional<MenuItem> existing = menuItemService.findById(id);
            if (existing.isPresent()) {
                MenuItem item = existing.get();
                item.setName(menuItem.getName());
                item.setDescription(menuItem.getDescription());
                item.setPrice(menuItem.getPrice());
                item.setCategory(menuItem.getCategory());
                if (!imageFile.isEmpty()) {
                    item.setImagePath(saveImage(imageFile, "menu"));
                }
                menuItemService.save(item);
                redirectAttributes.addFlashAttribute("success", "Menu item updated successfully!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating menu item: " + e.getMessage());
        }
        return "redirect:/admin/menu";
    }

    @GetMapping("/menu/delete/{id}")
    public String deleteMenuItem(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        menuItemService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Menu item deleted successfully!");
        return "redirect:/admin/menu";
    }

    // ===================== EMPLOYEES / STAFF =====================
    @GetMapping("/staff")
    public String listStaff(Model model) {
        model.addAttribute("chefs", employeeService.findByEmployeeType("chef"));
        model.addAttribute("others", employeeService.findByEmployeeType("other"));
        model.addAttribute("allStaff", employeeService.findAll());
        return "admin/staff";
    }

    @GetMapping("/staff/add")
    public String showAddStaffForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "admin/staff-form";
    }

    @PostMapping("/staff/add")
    public String addStaff(@ModelAttribute Employee employee,
                           @RequestParam("imageFile") MultipartFile imageFile,
                           RedirectAttributes redirectAttributes) {
        try {
            if (!imageFile.isEmpty()) {
                employee.setImagePath(saveImage(imageFile, "employees"));
            }
            employeeService.save(employee);
            redirectAttributes.addFlashAttribute("success", "Staff member added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding staff: " + e.getMessage());
        }
        return "redirect:/admin/staff";
    }

    @GetMapping("/staff/edit/{id}")
    public String showEditStaffForm(@PathVariable Long id, Model model) {
        Optional<Employee> employee = employeeService.findById(id);
        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
            return "admin/staff-form";
        }
        return "redirect:/admin/staff";
    }

    @PostMapping("/staff/edit/{id}")
    public String updateStaff(@PathVariable Long id,
                              @ModelAttribute Employee employee,
                              @RequestParam("imageFile") MultipartFile imageFile,
                              RedirectAttributes redirectAttributes) {
        try {
            Optional<Employee> existing = employeeService.findById(id);
            if (existing.isPresent()) {
                Employee emp = existing.get();
                emp.setName(employee.getName());
                emp.setRole(employee.getRole());
                emp.setEmployeeType(employee.getEmployeeType());
                emp.setFacebookUrl(employee.getFacebookUrl());
                emp.setInstagramUrl(employee.getInstagramUrl());
                emp.setTwitterUrl(employee.getTwitterUrl());
                if (!imageFile.isEmpty()) {
                    emp.setImagePath(saveImage(imageFile, "employees"));
                }
                employeeService.save(emp);
                redirectAttributes.addFlashAttribute("success", "Staff member updated successfully!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating staff: " + e.getMessage());
        }
        return "redirect:/admin/staff";
    }

    @GetMapping("/staff/delete/{id}")
    public String deleteStaff(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        employeeService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Staff member deleted successfully!");
        return "redirect:/admin/staff";
    }

    // ===================== RESERVATIONS =====================
    @GetMapping("/reservations")
    public String listReservations(Model model) {
        model.addAttribute("reservations", reserveService.findAll());
        return "admin/reservations";
    }

    @GetMapping("/reservations/delete/{id}")
    public String deleteReservation(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        reserveService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Reservation deleted successfully!");
        return "redirect:/admin/reservations";
    }

    // ===================== CONTACT MESSAGES =====================
    @GetMapping("/messages")
    public String listMessages(Model model) {
        model.addAttribute("messages", contactMessageService.findAll());
        return "admin/messages";
    }

    @GetMapping("/messages/delete/{id}")
    public String deleteMessage(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        contactMessageService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Message deleted successfully!");
        return "redirect:/admin/messages";
    }

    // ===================== HELPER METHODS =====================
    private String saveImage(MultipartFile imageFile, String subfolder) throws IOException {
        String originalFileName = imageFile.getOriginalFilename();
        String extension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        String fileName = UUID.randomUUID().toString() + extension;

        Path uploadPath = Paths.get(UPLOAD_DIR + subfolder);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, imageFile.getBytes());

        return subfolder + "/" + fileName;
    }
}