package com.example.courier.controller;

import com.example.courier.model.Delivery;
import com.example.courier.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @GetMapping("/deliveries")
    public String getAllDeliveries(Model model) {
        List<Delivery> deliveries = deliveryRepository.findAll();
        model.addAttribute("deliveries", deliveries);
        return "admin_deliveries";
    }

    @GetMapping("/deliveries/edit/{id}")
    public String showEditDeliveryForm(@PathVariable Long id, Model model) {
        Delivery delivery = deliveryRepository.findById(id).orElse(null);
        model.addAttribute("delivery", delivery);
        return "edit_delivery";
    }

    @PostMapping("/deliveries/edit/{id}")
    public String editDelivery(@PathVariable Long id, @ModelAttribute Delivery delivery) {
        Delivery existingDelivery = deliveryRepository.findById(id).orElse(null);
        if (existingDelivery != null) {
            existingDelivery.setStatus(delivery.getStatus());
            existingDelivery.setLocation(delivery.getLocation());
            existingDelivery.setLatitude(delivery.getLatitude());
            existingDelivery.setLongitude(delivery.getLongitude());
            deliveryRepository.save(existingDelivery);
        }
        return "redirect:/admin/deliveries";
    }
}
