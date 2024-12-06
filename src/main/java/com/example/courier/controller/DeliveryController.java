package com.example.courier.controller;

import com.example.courier.model.Delivery;
import com.example.courier.model.User;
import com.example.courier.repository.DeliveryRepository;
import com.example.courier.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/deliveries")
public class DeliveryController {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getAllDeliveries(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userService.findUserByUsername(currentUsername);

        if (authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            List<Delivery> deliveries = deliveryRepository.findAll();
            model.addAttribute("deliveries", deliveries);
            return "admin_deliveries";
        } else {
            List<Delivery> deliveries = deliveryRepository.findByUser(user);
            model.addAttribute("deliveries", deliveries);
            return "deliveries";
        }
    }

    @GetMapping("/new")
    public String showNewDeliveryForm(Model model) {
        model.addAttribute("delivery", new Delivery());
        return "new_delivery";
    }

    @PostMapping("/new")
    public String createDelivery(@ModelAttribute Delivery delivery) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userService.findUserByUsername(currentUsername);

        Long userDeliveryNumber = deliveryRepository.countByUser(user) + 1;
        delivery.setUser(user);
        delivery.setUserDeliveryNumber(userDeliveryNumber);
        deliveryRepository.save(delivery);
        return "redirect:/deliveries";
    }

    @GetMapping("/delete/{id}")
    public String deleteDelivery(@PathVariable Long id) {
        deliveryRepository.deleteById(id);
        return "redirect:/deliveries";
    }

    @GetMapping("/search")
    public String searchDelivery(@RequestParam Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userService.findUserByUsername(currentUsername);

        try {
            Optional<Delivery> deliveryOptional = deliveryRepository.findByIdAndUser(id, user);
            if (deliveryOptional.isPresent()) {
                Delivery delivery = deliveryOptional.get();
                System.out.println("Delivery found: " + delivery);
                model.addAttribute("delivery", delivery);
            } else {
                System.out.println("Delivery not found for ID: " + id);
                model.addAttribute("delivery", new Delivery()); // Передаем пустой объект, чтобы избежать ошибки
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "An error occurred while searching for the delivery.");
        }
        return "delivery_map";
    }
}
