package com.crud.tasks.trello.client;
import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.SimpleEmailService;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.crud.tasks.validator.TrelloValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mail.SimpleMailMessage;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TrelloFacadeTest {
    @InjectMocks
    private TrelloFacade trelloFacade;
    @Mock
    private SimpleEmailService simpleEmailService;
    @Mock
    private TrelloService trelloService;
    @Mock
    private TrelloValidator trelloValidator;
    @Mock
    private TrelloMapper trelloMapper;
    @Test
    void shouldFetchTrelloBoards() {
        // Given
        List<TrelloListDto> trelloLists =
                List.of(new TrelloListDto("1", "test_list", false));

        List<TrelloBoardDto> trelloBoards =
                List.of(new TrelloBoardDto("1", "test", trelloLists));

        List<TrelloList> mappedTrelloLists =
                List.of(new TrelloList("1", "test_list", false));

        List<TrelloBoard> mappedTrelloBoards =
                List.of(new TrelloBoard("1", "test", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(trelloBoards);
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(mappedTrelloBoards);

        // When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();

        // Then
        Assertions.assertNotNull(trelloBoardDtos);
        Assertions.assertEquals(1, trelloBoardDtos.size());

        trelloBoardDtos.forEach(trelloBoardDto -> {

            Assertions.assertEquals("1", trelloBoardDto.getId());
            Assertions.assertEquals("test", trelloBoardDto.getName());

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                Assertions.assertEquals("1", trelloListDto.getId());
                Assertions.assertEquals("test_list", trelloListDto.getName());
                Assertions.assertFalse(trelloListDto.isClosed());
            });
        });

    }
    @Test
    void mapperTest(){
        List<TrelloListDto> trelloLists = List.of(new TrelloListDto("1", "test_list", false));
        List<TrelloBoardDto> trelloBoards = List.of(new TrelloBoardDto("1","test",trelloLists));
        List<TrelloList> mappedTrelloLists = List.of(new TrelloList("1","test_list",false));
        List<TrelloBoard> mappedTrelloBoards = List.of(new TrelloBoard("1","test",mappedTrelloLists));

        List<TrelloBoard> mappedWithoutDto = trelloMapper.mapToBoards(trelloBoards);
        List<TrelloBoardDto> mappedToDto = trelloMapper.mapToBoardsDto(mappedTrelloBoards);
        List<TrelloListDto> mappedToList = trelloMapper.mapToListDto(mappedTrelloLists);
        List<TrelloList> mappedToListDto = trelloMapper.mapToList(trelloLists);
        TrelloCard trelloCard = new TrelloCard("karta", "opis", "pos", "1");
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        trelloService.createTrelloCard(trelloCardDto);
        List<TrelloBoardDto> trelloBoardDtoList = trelloService.fetchTrelloBoards();
        trelloFacade.createCard(trelloCardDto);
        trelloFacade.fetchTrelloBoards();

        Mail mail = new Mail("test@test.com", "Test", "Test Message",null);
        Mail mail2 = new Mail("test@test.com", "Test", "Test Message","");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        mailMessage.setCc(mail.getToCc());

        simpleEmailService.send(mail);
        simpleEmailService.send(mail2);
    }
}
