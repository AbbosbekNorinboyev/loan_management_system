package uz.brb.loan_management_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.brb.loan_management_system.dto.Response;
import uz.brb.loan_management_system.dto.request.CardRequest;
import uz.brb.loan_management_system.service.CardService;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping("/create")
    public Response createCard(@RequestBody CardRequest request) {
        return cardService.createCard(request);
    }

    @GetMapping("/get")
    public Response getCard(@RequestParam Long id) {
        return cardService.getCard(id);
    }

    @GetMapping("/getAll")
    public Response getAllCard() {
        return cardService.getAllCard();
    }

    @PutMapping("/update")
    public Response updateCard(@RequestBody CardRequest request,
                               @RequestParam Long id) {
        return cardService.updateCard(request, id);
    }

    @DeleteMapping("/delete")
    public Response deleteCard(@RequestParam Long id) {
        return cardService.deleteCard(id);
    }
}
