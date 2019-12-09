package com.learning.tacocloud.tacos;

import com.learning.tacocloud.data.IngredientRepository;
import com.learning.tacocloud.data.OrderRepository;
import com.learning.tacocloud.data.TacoRepository;
import com.learning.tacocloud.data.UserRepository;
import com.learning.tacocloud.domain.Ingredient;
import com.learning.tacocloud.domain.Order;
import com.learning.tacocloud.domain.Taco;
import com.learning.tacocloud.domain.User;
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

import static com.learning.tacocloud.domain.Ingredient.Type;

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
            ingredientRepository.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
            ingredientRepository.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
            ingredientRepository.save(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
            ingredientRepository.save(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
            ingredientRepository.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
            ingredientRepository.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
            ingredientRepository.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
            ingredientRepository.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
            ingredientRepository.save(new Ingredient("SLSA", "Salsa", Type.SAUCE));
            ingredientRepository.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE));

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
                Order order = makeOrder("order " + i, user, tacoRepository, ingredientRepository);
                orderRepository.save(order);
            }
        };
    }

    private Taco makeTaco(String name, IngredientRepository ingredientRepository) {
        Taco taco = new Taco();
        taco.setName(name);
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
            Taco taco = tacoRepository.save(makeTaco("taco " + i, ingredientRepository));
            order.getTacos().add(taco);
        }

        return order;
    }
}
