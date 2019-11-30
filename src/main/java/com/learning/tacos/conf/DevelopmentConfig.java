package com.learning.tacos.conf;

import com.learning.tacos.domain.data.IngredientRepository;
import com.learning.tacos.domain.data.OrderRepository;
import com.learning.tacos.domain.data.TacoRepository;
import com.learning.tacos.domain.model.Ingredient;
import com.learning.tacos.domain.model.Order;
import com.learning.tacos.domain.model.Taco;
import com.learning.tacos.security.data.User;
import com.learning.tacos.security.data.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Profile({ "!prod", "!qa" })
@Configuration
public class DevelopmentConfig {
    private final Random random = new Random();

    @Bean
    public CommandLineRunner dataLoader(
            IngredientRepository ingredientRepository,
            TacoRepository tacoRepository,
            OrderRepository orderRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            ingredientRepository.save(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
            ingredientRepository.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
            ingredientRepository.save(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
            ingredientRepository.save(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
            ingredientRepository.save(new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
            ingredientRepository.save(new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
            ingredientRepository.save(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
            ingredientRepository.save(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
            ingredientRepository.save(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
            ingredientRepository.save(new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));

            User user = new User(
                    "admin",
                    passwordEncoder.encode("admin"),
                    "admin",
                    "15 Yemen Road",
                    "Yemen",
                    "Yemen State",
                    "417 24",
                    "090078601"
            );
            userRepository.save(user);

            for (int i=0; i<30; i++) {
                Order order = makeOrder("taco " + i, user, tacoRepository, ingredientRepository);
                orderRepository.save(order);
            }
        };
    }

    private Taco makeTaco(IngredientRepository ingredientRepository) {
        Taco taco = new Taco();
        taco.setName("taco 1");
        taco.setIngredients(new ArrayList<>());

        List<Ingredient> allIngredientList = new ArrayList<>();
        ingredientRepository.findAll().forEach(allIngredientList::add);

        List<Ingredient> ingredients = new ArrayList<>();
        taco.setIngredients(ingredients);
        int num = random.nextInt(allIngredientList.size()) + 1;
        for (int i = 0; i < num; i++) {
            ingredients.add(allIngredientList.get(random.nextInt(allIngredientList.size())));
        }

        return taco;
    }

    private Order makeOrder(String name, User user, TacoRepository tacoRepository, IngredientRepository ingredientRepository) {
        List<String> cardNumbers = Arrays.asList(
                "4542831781194563", "4897991054340524", "4674813468994580", "4878667725907502"
        ) ;

        Order order = new Order();
        order.setTacos(new ArrayList<>());
        order.setUser(user);
        order.setName(name);
        order.setStreet(user.getStreet());
        order.setCity(user.getCity());
        order.setState(user.getState());
        order.setZip(user.getZip());
        order.setCcNumber(cardNumbers.get(random.nextInt(cardNumbers.size())));
        order.setCcExpiration("11/11");
        order.setCcCVV(String.valueOf(random.nextInt(899) + 100));
        order.setPlacedAt(Instant.now());

        int numTacos = random.nextInt(5) + 1;
        for (int i = 0; i < numTacos; i++) {
            Taco taco = tacoRepository.save(makeTaco(ingredientRepository));
            order.getTacos().add(taco);
        }

        return order;
    }
}
