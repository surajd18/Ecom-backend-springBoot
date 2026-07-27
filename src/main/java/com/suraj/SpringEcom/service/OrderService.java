package com.suraj.SpringEcom.service;

import com.suraj.SpringEcom.exceptions.ProductNotFoundException;
import com.suraj.SpringEcom.model.Order;
import com.suraj.SpringEcom.model.OrderItem;
import com.suraj.SpringEcom.model.Product;
import com.suraj.SpringEcom.model.dto.OrderItemRequest;
import com.suraj.SpringEcom.model.dto.OrderItemResponse;
import com.suraj.SpringEcom.model.dto.OrderRequest;
import com.suraj.SpringEcom.model.dto.OrderResponse;
import com.suraj.SpringEcom.repo.OrderRepo;
import com.suraj.SpringEcom.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private ProductRepo productRepo;
    private OrderRepo orderRepo;

    public OrderResponse placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderId(UUID.randomUUID());
        order.setCustomerName(orderRequest.customerName());
        order.setEmail(orderRequest.email());
        order.setStatus("PLACED");
        order.setOrderDate(LocalDate.now());

        List<OrderItem> orderItems = new ArrayList<>();
        for(OrderItemRequest itemReq : orderRequest.items()){
            Product product = productRepo.findById(itemReq.productId()).orElseThrow(()->new ProductNotFoundException("Product Not Found"));

            product.setStockQuantity(product.getStockQuantity()- itemReq.quantity());
            productRepo.save(product);

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(itemReq.quantity())
                    .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(itemReq.quantity())))
                    .order(order)
                    .build();

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        Order savedOrder = orderRepo.save(order);

        List<OrderItemResponse> itemResponses = new ArrayList<>();
        for(OrderItem item : order.getOrderItems()){
            OrderItemResponse orderItemResponse = new OrderItemResponse(
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getTotalPrice()

            );
            itemResponses.add(orderItemResponse);
        }

        OrderResponse orderResponse = new OrderResponse(
                savedOrder.getOrderId(),
                savedOrder.getCustomerName(),
                savedOrder.getEmail(),
                savedOrder.getStatus(),
                savedOrder.getOrderDate(),
                itemResponses
        );

        return orderResponse;
    }

    public List<OrderResponse> getAllOrdersResponses() {
        List<Order> orders = orderRepo.findAll();
        List<OrderResponse>  orderResponses= new ArrayList<>();

        for(Order order: orders){

            List<OrderItemResponse> orderItemResponses = new ArrayList<>();
            for(OrderItem item: order.getOrderItems()){
                OrderItemResponse orderItemResponse = new OrderItemResponse(
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getTotalPrice()
                );

                orderItemResponses.add(orderItemResponse);
            }

            OrderResponse orderResponse = new OrderResponse(
                order.getOrderId(),
                    order.getEmail(),
                    order.getStatus(),
                    order.getCustomerName(),
                    order.getOrderDate(),
                    orderItemResponses
            );

            orderResponses.add(orderResponse);
        }
        return orderResponses;
    }
}
