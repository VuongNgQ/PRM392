package com.haile.exe101.depairapplication.repositories;

import com.haile.exe101.depairapplication.models.entity.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
}
