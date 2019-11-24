package com.learning.tacos.data;

import com.learning.tacos.model.Ingredient;
import com.learning.tacos.model.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

@Repository
public class JdbcTacoRepository implements TacoRepository {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);

        for (var ingredient : taco.getIngredients()) {
            saveIngredientToTaco(ingredient, tacoId);
        }
        return taco;
    }

    private long saveTacoInfo(Taco taco) {
        taco.setCreatedAt(Instant.now());
        var factory = new PreparedStatementCreatorFactory(
                "INSERT INTO Taco (name, createdAt) VALUES (?, ?)", Types.VARCHAR, Types.TIMESTAMP);
        factory.setReturnGeneratedKeys(true);
        var psc = factory.newPreparedStatementCreator(Arrays.asList(taco.getName(), new Timestamp(taco.getCreatedAt().toEpochMilli())));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
        jdbc.update("INSERT INTO Taco_Ingredients (taco, ingredient) VALUES (?, ?)",
                tacoId, ingredient.getId());
    }
}
