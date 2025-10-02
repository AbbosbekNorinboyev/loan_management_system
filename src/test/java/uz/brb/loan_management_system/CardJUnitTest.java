package uz.brb.loan_management_system;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import uz.brb.loan_management_system.dto.Response;
import uz.brb.loan_management_system.dto.request.CardRequest;
import uz.brb.loan_management_system.dto.response.CardResponse;
import uz.brb.loan_management_system.entity.Card;
import uz.brb.loan_management_system.enums.CardCurrency;
import uz.brb.loan_management_system.enums.CardStatus;
import uz.brb.loan_management_system.exception.ResourceNotFoundException;
import uz.brb.loan_management_system.mapper.CardMapper;
import uz.brb.loan_management_system.repository.CardRepository;
import uz.brb.loan_management_system.service.impl.CardServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardJUnitTest {
    @InjectMocks
    CardServiceImpl cardService;
    @Mock
    CardRepository cardRepository;
    @Mock
    CardMapper cardMapper;
    Card card1;
    Card card2;
    CardRequest request1;
    CardRequest request2;
    CardResponse cardResponse1;
    CardResponse cardResponse2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        request1 = new CardRequest();
        request1.setCardNumber("8600123412341234");
        request1.setCardHolder("Norinboyev Abbos");
        request1.setExpiryDate("09/27");
        request1.setBalance(BigDecimal.valueOf(100000));
        request1.setCardStatus(CardStatus.ACTIVE);
        request1.setCardCurrency(CardCurrency.UZS);

        request2 = new CardRequest();
        request2.setCardNumber("8600123412341235");
        request2.setCardHolder("Husanboy Jo'rayev");
        request2.setExpiryDate("09/28");
        request2.setBalance(BigDecimal.valueOf(200000));
        request2.setCardStatus(CardStatus.ACTIVE);
        request2.setCardCurrency(CardCurrency.UZS);

        card1 = new Card();
        card1.setId(1L);
        card1.setCardNumber("8600123412341234");
        card1.setCardHolder("Norinboyev Abbos");
        card1.setExpiryDate("09/27");
        card1.setBalance(BigDecimal.valueOf(100000));
        card1.setCardStatus(CardStatus.ACTIVE);
        card1.setCardCurrency(CardCurrency.UZS);

        card2 = new Card();
        card2.setId(2L);
        card2.setCardNumber("8600123412341235");
        card2.setCardHolder("Husanboy Jo'rayev");
        card2.setExpiryDate("09/28");
        card2.setBalance(BigDecimal.valueOf(200000));
        card2.setCardStatus(CardStatus.ACTIVE);
        card2.setCardCurrency(CardCurrency.UZS);

        cardResponse1 = new CardResponse();
        cardResponse1.setId(1L);
        cardResponse1.setCardNumber("8600123412341234");
        cardResponse1.setCardHolder("Norinboyev Abbos");
        cardResponse1.setExpiryDate("09/27");
        cardResponse1.setBalance(BigDecimal.valueOf(100000));
        cardResponse1.setCardStatus(CardStatus.ACTIVE);
        cardResponse1.setCardCurrency(CardCurrency.UZS);

        cardResponse2 = new CardResponse();
        cardResponse2.setId(2L);
        cardResponse2.setCardNumber("8600123412341235");
        cardResponse2.setCardHolder("Husanboy Jo'rayev");
        cardResponse2.setExpiryDate("09/28");
        cardResponse2.setBalance(BigDecimal.valueOf(200000));
        cardResponse2.setCardStatus(CardStatus.ACTIVE);
        cardResponse2.setCardCurrency(CardCurrency.UZS);

        // SHU YERDA Service ni new qilamiz
        cardService = new CardServiceImpl(cardMapper, cardRepository);
    }

    @Test
    void createTest() {
        // given
        Mockito.when(cardMapper.toEntity(request1)).thenReturn(card1);
        Mockito.when(cardRepository.save(card1)).thenReturn(card1);

        // when
        Response response = cardService.createCard(request1);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getStatus().is2xxSuccessful()).isTrue();
        assertThat(response.getMessage()).isEqualTo("Card successfully created");

        // verify repository va mapper chaqirilganini tekshirish
        verify(cardMapper, times(1)).toEntity(request1);
        verify(cardRepository, times(1)).save(card1);
    }

    @Test
    void getTest() {
        // given
        Mockito.when(cardRepository.findById(1L)).thenReturn(Optional.of(card1));
        Mockito.when(cardMapper.toResponse(card1)).thenReturn(cardResponse1);

        // when
        Response response = cardService.getCard(1L);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getStatus().is2xxSuccessful()).isTrue();
        assertThat(response.getMessage()).isEqualTo("Card successfully found");
        assertThat(response.getData()).isEqualTo(cardResponse1);

        // verify repository va mapper chaqirilganini tekshirish
        verify(cardRepository, times(1)).findById(1L);
        verify(cardMapper, times(1)).toResponse(card1);
    }

    @Test
    void getNotFoundTest() {
        // given
        Mockito.when(cardRepository.findById(12L)).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> cardService.getCard(12L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Card not found: 12");

        // verify repository va mapper chaqirilganini tekshirish
        verify(cardRepository, times(1)).findById(12L);
        verifyNoInteractions(cardMapper);
    }

    @Test
    void getAllTest() {
        // given
        Mockito.when(cardRepository.findAll()).thenReturn(List.of(card1, card2));
        Mockito.when(cardMapper.responseList(List.of(card1, card2)))
                .thenReturn(List.of(cardResponse1, cardResponse2));

        // when
        Response response = cardService.getAllCard();

        // then
        assertThat(response).isNotNull();
        assertThat(response.getCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getStatus().is2xxSuccessful()).isTrue();
        assertThat(response.getMessage()).isEqualTo("Card list successfully found");
        assertThat(response.getData()).isInstanceOf(List.class);

        List<?> data = (List<?>) response.getData();
        assertThat(data).hasSize(2);

        // verify repository va mapper chaqirilganini tekshirish
        verify(cardRepository, times(1)).findAll();
        verify(cardMapper, times(1)).responseList(List.of(card1, card2));
    }

    @Test
    void updateTest() {
        // given
        Long id = 1L;

        when(cardRepository.findById(id)).thenReturn(Optional.of(card1));
        doNothing().when(cardMapper).update(card1, request1);

        // when
        Response response = cardService.updateCard(request1, id);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getStatus().is2xxSuccessful()).isTrue();
        assertThat(response.getMessage()).isEqualTo("Card successfully updated");

        // verify
        verify(cardRepository, times(1)).findById(id);
        verify(cardRepository, times(1)).save(card1);
        verify(cardMapper, times(1)).update(card1, request1);
    }

    @Test
    void deleteTest() {
        // given
        Long id = 1L;

        when(cardRepository.findById(id)).thenReturn(Optional.of(card1));

        // when
        Response response = cardService.deleteCard(id);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getStatus().is2xxSuccessful()).isTrue();
        assertThat(response.getMessage()).isEqualTo("Card successfully deleted");

        // verify
        verify(cardRepository, times(1)).findById(1L);
        verify(cardRepository, times(1)).delete(card1);
    }
}
