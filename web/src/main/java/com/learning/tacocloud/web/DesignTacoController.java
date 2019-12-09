package com.learning.tacocloud.web;

import com.learning.tacocloud.data.IngredientRepository;
import com.learning.tacocloud.data.TacoRepository;
import com.learning.tacocloud.domain.Ingredient;
import com.learning.tacocloud.domain.Order;
import com.learning.tacocloud.domain.Taco;
import com.learning.tacocloud.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.learning.tacocloud.domain.Ingredient.Type;

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
@Slf4j
public class DesignTacoController {

    @Autowired
    private TacoRepository tacoRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping
    public String showDesignForm(Model model) {
        return "design";
    }

    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order) {
        if (errors.hasErrors()) {
            return "design";
        }
        Taco saved = tacoRepository.save(design);
        log.info("Processed design: {}", saved);
        order.addDesign(saved);

        return "redirect:/orders/current";
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @ModelAttribute(name = "order")
    public Order order(@AuthenticationPrincipal User user) {
        Order order = new Order();
        order.setUser(user);
        order.setName(user.getFullName());
        order.setStreet(user.getStreet());
        order.setState(user.getState());
        order.setCity(user.getCity());
        order.setZip(user.getZip());
        return order;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredients::add);

        for (var type : Type.values()) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    private List<Ingredient> filterByType(List <Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(x -> type.equals(x.getType())).collect(Collectors.toList());
    }
}
