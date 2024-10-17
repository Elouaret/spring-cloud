package ch.ge.apside.archi.cart.service.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public record Cart(Long id, List<Item> items, LocalDate date) {
    @Override
    public List<Item> items() {
        return Collections.unmodifiableList(items);
    }
}
