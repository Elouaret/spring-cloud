package ch.ge.apside.archi.cart.service.controller;

import ch.ge.apside.archi.cart.service.controller.client.GatewayFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CartPageController {

//    @Autowired
    private GatewayFeignClient gatewayFeignClient;

    @RequestMapping("/get-page")
    public String page(Model model) {
        model.addAttribute("items", gatewayFeignClient.items());
        return "page-view";
    }
}
