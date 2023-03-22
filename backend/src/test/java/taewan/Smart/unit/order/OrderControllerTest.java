package taewan.Smart.unit.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import taewan.Smart.domain.order.controller.OrderController;
import taewan.Smart.domain.order.dto.OrderItemSaveDto;
import taewan.Smart.domain.order.dto.OrderSaveDto;
import taewan.Smart.domain.order.service.OrderItemService;
import taewan.Smart.domain.order.service.OrderService;
import taewan.Smart.fixture.ExceptionTestFixture;
import taewan.Smart.global.error.GlobalExceptionHandler;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private OrderService orderService;
    @Mock
    private OrderItemService orderItemService;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new OrderController(orderService, orderItemService))
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    private String toJson(OrderSaveDto dto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dto);
    }

    @Test
    @DisplayName("주문 저장 테스트")
    void upload() throws Exception {
        //given
        OrderSaveDto dto = new OrderSaveDto(new ArrayList<>(){{
            add(new OrderItemSaveDto(1L, "s", 1));
            add(new OrderItemSaveDto(2L, "s", 1));
        }});
        MockHttpServletRequestBuilder request = post("/orders")
                .requestAttr("tokenMemberId", 1L)
                .content(toJson(dto))
                .contentType(MediaType.APPLICATION_JSON);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isOk());
        verify(orderService, only()).save(any(), any());
        verify(orderService, times(1)).save(any(), any());
    }

    @Test
    @DisplayName("등록되지 않은 회원 기본키를 이용해 주문을 저장하는 경우 isNotFound를 반환")
    void upload_invalid_memberId() throws Exception {
        //given
        OrderSaveDto dto = new OrderSaveDto(new ArrayList<>(){{
            add(new OrderItemSaveDto(1L, "s", 1));
            add(new OrderItemSaveDto(2L, "s", 1));
        }});
        MockHttpServletRequestBuilder request = post("/orders")
                .requestAttr("tokenMemberId", 0L)
                .content(toJson(dto))
                .contentType(MediaType.APPLICATION_JSON);

        doThrow(NoSuchElementException.class).when(orderService).save(any(), any());

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isNotFound())
                .andExpect(ExceptionTestFixture.isNoSuchElementException());
        verify(orderService, only()).save(any(), any());
        verify(orderService, times(1)).save(any(), any());
    }

    @Test
    @DisplayName("주문 아이템의 제품 기본키가 등록되지 않았을 때 주문을 저장하는 경우 isNotFound를 반환")
    void upload_invalid_productId() throws Exception {
        //given
        OrderSaveDto dto = new OrderSaveDto(new ArrayList<>(){{
            add(new OrderItemSaveDto(1L, "s", 1));
            add(new OrderItemSaveDto(0L, "s", 1));
        }});
        MockHttpServletRequestBuilder request = post("/orders")
                .requestAttr("tokenMemberId", 1L)
                .content(toJson(dto))
                .contentType(MediaType.APPLICATION_JSON);

        doThrow(NoSuchElementException.class).when(orderService).save(any(), any());

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isNotFound())
                .andExpect(ExceptionTestFixture.isNoSuchElementException());
        verify(orderService, only()).save(any(), any());
        verify(orderService, times(1)).save(any(), any());
    }

    @Test
    @DisplayName("회원 기본키를 이용한 회원 주문 리스트 전체 조회 테스트")
    void search() throws Exception {
        //given
        MockHttpServletRequestBuilder request = get("/orders")
                .requestAttr("tokenMemberId", 1L);

        when(orderService.findAll(any())).thenReturn(new ArrayList<>());

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(orderService, only()).findAll(any());
        verify(orderService, times(1)).findAll(any());
    }

    @Test
    @DisplayName("등록되지 않른 회원 기본키를 이용해 회원 주문 리스트 전체 조회 시 isNotFound를 반환")
    void search_invalid_memberId() throws Exception {
        //given
        MockHttpServletRequestBuilder request = get("/orders")
                .requestAttr("tokenMemberId", 0L);

        when(orderService.findAll(any())).thenThrow(NoSuchElementException.class);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isNotFound())
                .andExpect(ExceptionTestFixture.isNoSuchElementException());
        verify(orderService, only()).findAll(any());
        verify(orderService, times(1)).findAll(any());
    }

    @Test
    @DisplayName("주문 아이템 기본키를 이용한 배달 상태 취소 테스트")
    void cancel() throws Exception {
        //given
        MockHttpServletRequestBuilder request = post("/orders/cancel")
                .param("orderItemId", "1")
                .param("reason", "...");

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isOk());
        verify(orderItemService, only()).cancel(any());
        verify(orderItemService, times(1)).cancel(any());
    }

    @Test
    @DisplayName("등록되지 않은 주문 아이템 기본키를 이용해 배달 상태 취소를 하는 경우 isNotFound를 반환")
    void cancel_invalid_orderItemId() throws Exception {
        //given
        MockHttpServletRequestBuilder request = post("/orders/cancel")
                .param("orderItemId", "0")
                .param("reason", "...");

        doThrow(NoSuchElementException.class).when(orderItemService).cancel(any());

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isNotFound())
                .andExpect(ExceptionTestFixture.isNoSuchElementException());
        verify(orderItemService, only()).cancel(any());
        verify(orderItemService, times(1)).cancel(any());
    }

    @Test
    @DisplayName("주문 아이템 기본키를 이용한 배달 상태 환불 테스트")
    void refund() throws Exception {
        //given
        MockHttpServletRequestBuilder request = post("/orders/refund")
                .param("orderItemId", "1")
                .param("reason", "...");

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isOk());
        verify(orderItemService, only()).refund(any());
        verify(orderItemService, times(1)).refund(any());
    }

    @Test
    @DisplayName("등록되지 않은 주문 아이템 기본키를 이용해 배달 상태 환불을 하는 경우 isNotFound를 반환")
    void refund_invalid_orderItemId() throws Exception {
        //given
        MockHttpServletRequestBuilder request = post("/orders/refund")
                .param("orderItemId", "0")
                .param("reason", "...");

        doThrow(NoSuchElementException.class).when(orderItemService).refund(any());

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isNotFound())
                .andExpect(ExceptionTestFixture.isNoSuchElementException());
        verify(orderItemService, only()).refund(any());
        verify(orderItemService, times(1)).refund(any());
    }
}
