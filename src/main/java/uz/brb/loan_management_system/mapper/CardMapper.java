package uz.brb.loan_management_system.mapper;

import org.springframework.stereotype.Component;
import uz.brb.loan_management_system.dto.request.CardRequest;
import uz.brb.loan_management_system.dto.response.CardResponse;
import uz.brb.loan_management_system.entity.Card;

import java.util.ArrayList;
import java.util.List;

@Component
public class CardMapper {
    public Card toEntity(CardRequest request) {
        return Card.builder()
                .cardNumber(request.getCardNumber())
                .cardHolder(request.getCardHolder())
                .expiryDate(request.getExpiryDate())
                .balance(request.getBalance())
                .build();
    }

    public CardResponse toResponse(Card entity) {
        return CardResponse.builder()
                .id(entity.getId())
                .cardNumber(entity.getCardNumber())
                .cardHolder(entity.getCardHolder())
                .expiryDate(entity.getExpiryDate())
                .balance(entity.getBalance())
                .cardStatus(entity.getCardStatus())
                .cardCurrency(entity.getCardCurrency())
                .build();
    }

    public List<CardResponse> responseList(List<Card> cards) {
        if (cards != null && !cards.isEmpty()) {
            return cards.stream().map(this::toResponse).toList();
        }
        return new ArrayList<>();
    }

    public void update(Card entity, CardRequest request) {
        if (request == null) {
            return;
        }
        if (request.getCardNumber() != null && !request.getCardNumber().trim().isEmpty()) {
            entity.setCardNumber(request.getCardNumber());
        }
        if (request.getCardHolder() != null && !request.getCardHolder().trim().isEmpty()) {
            entity.setCardHolder(request.getCardHolder());
        }
        if (request.getExpiryDate() != null) {
            entity.setExpiryDate(request.getExpiryDate());
        }
        if (request.getBalance() != null) {
            entity.setBalance(request.getBalance());
        }
        if (request.getCardCurrency() != null) {
            entity.setCardCurrency(request.getCardCurrency());
        }
        if (request.getCardStatus() != null) {
            entity.setCardStatus(request.getCardStatus());
        }
    }
}
