package uz.brb.loan_management_system.service;

import uz.brb.loan_management_system.dto.request.CardRequest;
import uz.brb.loan_management_system.dto.Response;

public interface CardService {
    Response createCard(CardRequest request);

    Response getCard(Long id);

    Response getAllCard();

    Response updateCard(CardRequest request, Long id);

    Response deleteCard(Long id);
}