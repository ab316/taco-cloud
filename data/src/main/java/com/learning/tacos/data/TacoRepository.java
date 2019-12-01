package com.learning.tacos.data;

import com.learning.tacos.domain.Taco;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TacoRepository extends PagingAndSortingRepository<Taco, Long> {
}
