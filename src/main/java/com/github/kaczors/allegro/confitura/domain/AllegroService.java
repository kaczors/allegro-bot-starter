package com.github.kaczors.allegro.confitura.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.allegro.ItemsListType;
import pl.allegro.PriceInfoType;

import java.util.Optional;

@Service
public class AllegroService {

    private final AllegroWsClient allegroWsClient;

    @Autowired
    public AllegroService(AllegroWsClient allegroWsClient) {
        this.allegroWsClient = allegroWsClient;
    }

    public Optional<ItemsListType> findAnyItem(String userId){
        return allegroWsClient.listItems(userId).stream().findAny();
    }

    public void buyItNow(ItemsListType item){
        float price = item.getPriceInfo().getItem().stream()
                .filter(p -> p.getPriceType().equals("buyNow"))
                .findAny()
                .map(PriceInfoType::getPriceValue)
                .orElseThrow(() -> new RuntimeException("Nie ma ceny 'Kup teraz'"));

        allegroWsClient.buyItNow(item.getItemId(), price, 1L);
    }
}
