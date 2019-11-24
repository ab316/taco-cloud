package com.learning.tacos.controller;

import com.learning.tacos.data.IngredientRepository;
import com.learning.tacos.data.TacoRepository;
import com.learning.tacos.model.Ingredient;
import com.learning.tacos.model.Ingredient.Type;
import com.learning.tacos.model.Order;
import com.learning.tacos.model.Taco;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    @Autowired
    private TacoRepository tacoRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping
    public String showDesignForm(Model model) {
        model.addAttribute("design", new Taco());
        return "design";
    }

    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order) {
        if (errors.hasErrors()) {
            return "design";
        }
        log.info("Processing design: {}", design);

        Taco saved = tacoRepository.save(design);
        order.addDesign(saved);

        return "redirect:/orders/current";
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
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
