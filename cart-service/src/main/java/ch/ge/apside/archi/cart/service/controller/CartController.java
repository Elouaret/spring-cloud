package ch.ge.apside.archi.cart.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CartController {

    @Autowired
    private ItemClient itemClient;

    @RequestMapping("/get-page")
    public String page(Model model) {
        model.addAttribute("page", itemClient.name());
        return "page-view";
    }
}
