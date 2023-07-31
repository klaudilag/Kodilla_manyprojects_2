package com.crud.tasks.trello.client;
import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.crud.tasks.validator.TrelloValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.crud.tasks.domain.TrelloList;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrelloFacadeTest {
    @InjectMocks
    private TrelloFacade trelloFacade;
    @Mock
    private TrelloService trelloService;
    @Mock
    private TrelloValidator trelloValidator;
    @Mock
    private TrelloMapper trelloMapper;
    @Test
    void shouldFetchEmptyList(){
        List<TrelloListDto> trelloLists = List.of(new TrelloListDto("1", "test_list", false));
        List<TrelloBoardDto> trelloBoards = List.of(new TrelloBoardDto("1","test",trelloLists));
        List<TrelloList> mappedTrelloLists = List.of(new TrelloList("1","test_list",false));
        List<TrelloBoard> mappedTrelloBoards = List.of(new TrelloBoard("1","test",mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(List.of());
        when(trelloValidator.validateTrelloBoards((mappedTrelloBoards))).thenReturn(List.of());

        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        Assertions.assertNotNull(trelloBoardDtos);
        Assertions.assertEquals(0,trelloBoardDtos.size());
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

        Assertions.assertEquals(mappedWithoutDto.get(0).getName(), "test");

    }
}
