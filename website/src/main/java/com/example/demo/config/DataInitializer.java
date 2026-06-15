package com.example.demo.config;

import com.example.demo.model.Employee;
import com.example.demo.model.MenuItem;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) {
        // Only seed if database is empty
        if (menuItemRepository.count() > 0) {
            return;
        }

        // ======== BREAKFAST ITEMS ========
        menuItemRepository.save(createMenuItem("Poridge & Banana", "Poridge in a creamy cheese, milk & banana.", 10.0, "breakfast", "menu/b1.jpg"));
        menuItemRepository.save(createMenuItem("Bread & Avacardo", "Avacardo with vegetables & bread.", 10.0, "breakfast", "menu/b2.jpg"));
        menuItemRepository.save(createMenuItem("Avacardo Sandwich", "Toasted bread with paste made of Avacardo & cheese.", 10.0, "breakfast", "menu/b3.jpg"));
        menuItemRepository.save(createMenuItem("Poridge & Strawberry", "Poridge in a creamy cheese, milk & Strawberry.", 10.0, "breakfast", "menu/b4.jpg"));
        menuItemRepository.save(createMenuItem("Cookies", "Cookies with cheese & honey.", 10.0, "breakfast", "menu/b5.jpg"));
        menuItemRepository.save(createMenuItem("Pancake", "Pancake with honey & mixed fruits.", 10.0, "breakfast", "menu/b6.jpg"));
        menuItemRepository.save(createMenuItem("Bread & Egg", "Toasted bread with Avacardo paste & omelette.", 10.0, "breakfast", "menu/b7.jpg"));
        menuItemRepository.save(createMenuItem("Mushroom & Potato", "Finely cooked Mushroom along with Potato.", 10.0, "breakfast", "menu/b8.jpg"));

        // ======== LUNCH ITEMS ========
        menuItemRepository.save(createMenuItem("Noodles", "Finely cooked Noodles along with Potato.", 20.0, "lunch", "menu/l1.jpg"));
        menuItemRepository.save(createMenuItem("Meat & Potato", "Finely cooked Meat along with Potato.", 20.0, "lunch", "menu/l2.jpg"));
        menuItemRepository.save(createMenuItem("Chicken", "Fried Chicken.", 20.0, "lunch", "menu/l3.jpg"));
        menuItemRepository.save(createMenuItem("Spaghetti & Potato", "Spaghetti along with Potato.", 20.0, "lunch", "menu/l4.jpg"));
        menuItemRepository.save(createMenuItem("Chicken Balls", "Fried Chicken balls.", 20.0, "lunch", "menu/l5.jpg"));
        menuItemRepository.save(createMenuItem("Beef & Potato", "Finely cooked Beef along with Potato.", 20.0, "lunch", "menu/l6.jpg"));
        menuItemRepository.save(createMenuItem("Whole Chicken", "Finely cooked Chicken.", 20.0, "lunch", "menu/l7.jpg"));
        menuItemRepository.save(createMenuItem("Mushroom & Potato", "Finely cooked Mushroom along with Potato.", 20.0, "lunch", "menu/l8.jpg"));

        // ======== DINNER ITEMS ========
        menuItemRepository.save(createMenuItem("Fried Fish", "Finely cooked fish along with vegetables.", 30.0, "dinner", "menu/d1.jpg"));
        menuItemRepository.save(createMenuItem("Mushroom & Potato", "Finely cooked Mushroom along with Potato.", 30.0, "dinner", "menu/d2.jpg"));
        menuItemRepository.save(createMenuItem("Soup", "Finely cooked Soup along with Potato.", 30.0, "dinner", "menu/d3.jpg"));
        menuItemRepository.save(createMenuItem("Potato", "Finely cooked Potato.", 30.0, "dinner", "menu/d4.jpg"));
        menuItemRepository.save(createMenuItem("Chicken Steak", "Finely cooked Chicken.", 30.0, "dinner", "menu/d5.jpg"));
        menuItemRepository.save(createMenuItem("Chicken Piece", "Finely cooked Chicken.", 30.0, "dinner", "menu/d6.jpg"));
        menuItemRepository.save(createMenuItem("Fish & Potato", "Fish and Mushroom along with Potato.", 30.0, "dinner", "menu/d7.jpg"));
        menuItemRepository.save(createMenuItem("Chicken & Potato", "Chicken along with Potato.", 30.0, "dinner", "menu/d8.jpg"));

        // ======== CHEFS ========
        employeeRepository.save(createEmployee("Ziz", "Head Chef", "chef", "employees/chef-1.jpg", "#", "#", "#"));
        employeeRepository.save(createEmployee("Richard", "Chef", "chef", "employees/chef-2.jpg", "#", "#", "#"));
        employeeRepository.save(createEmployee("Jacob", "Chef", "chef", "employees/chef-3.jpg", "#", "#", "#"));
        employeeRepository.save(createEmployee("Ally", "Chef", "chef", "employees/chef-4.jpg", "#", "#", "#"));

        // ======== OTHER STAFF ========
        employeeRepository.save(createEmployee("Maria", "Waiter", "other", "employees/w1.jpg", "#", "#", "#"));
        employeeRepository.save(createEmployee("John", "Waiter", "other", "employees/w2.jpg", "#", "#", "#"));
        employeeRepository.save(createEmployee("Sara", "Hostess", "other", "employees/w3.jpg", "#", "#", "#"));
        employeeRepository.save(createEmployee("David", "Manager", "other", "employees/w4.jpg", "#", "#", "#"));

        System.out.println("Database seeded with initial menu items and staff.");
    }

    private MenuItem createMenuItem(String name, String description, double price, String category, String imagePath) {
        MenuItem item = new MenuItem();
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);
        item.setCategory(category);
        item.setImagePath(imagePath);
        return item;
    }

    private Employee createEmployee(String name, String role, String employeeType, String imagePath,
                                     String fbUrl, String instaUrl, String twitterUrl) {
        Employee emp = new Employee();
        emp.setName(name);
        emp.setRole(role);
        emp.setEmployeeType(employeeType);
        emp.setImagePath(imagePath);
        emp.setFacebookUrl(fbUrl);
        emp.setInstagramUrl(instaUrl);
        emp.setTwitterUrl(twitterUrl);
        return emp;
    }
}