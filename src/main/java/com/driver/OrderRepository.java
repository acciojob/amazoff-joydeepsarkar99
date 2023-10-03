package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {
     HashMap<String,Order> orderHashMap = new HashMap<>();
     HashMap<String,DeliveryPartner> partnerHashMap = new HashMap<>();
     HashMap<String, List<String>> orderPartnerPairMap = new HashMap<>();

     public void addOrderToDB(Order order){
         String key = order.getId();
         orderHashMap.put(key,order);
     }

     public void addPartnerToDB(String partnerId){
         DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
         partnerHashMap.put(partnerId,deliveryPartner);
     }

     public void addOrderPartnerPairToDB(String orderId, String partnerId){
         if(orderPartnerPairMap.containsKey(partnerId)){
             List<String> orderList = orderPartnerPairMap.get(partnerId);
             orderList.add(orderId);
             orderPartnerPairMap.put(partnerId,orderList);
         }
         else{
             List<String> orderList = new ArrayList<>();
             orderList.add(orderId);
             orderPartnerPairMap.put(partnerId,orderList);
         }
     }

     public Order getOrderByIdFromDB(String orderId){
         if(orderHashMap.containsKey(orderId)) return orderHashMap.get(orderId);
         return null;
     }

     public DeliveryPartner getPartnerByIdFromDB(String partnerId){
         DeliveryPartner deliveryPartner = partnerHashMap.get(partnerId);
         return deliveryPartner;
     }

     public List<String> getOrderCountByPartnerIdFromDB(String partnerId){
         List<String> orderList = new ArrayList<>();
         if(orderPartnerPairMap.containsKey(partnerId)){
            orderList = orderPartnerPairMap.get(partnerId);
             return orderList;
         }
         return orderList;
     }

     public List<Order> getAllOrdersFromDB(){
         List<Order> orderList = new ArrayList<>();
         for(Order obj : orderHashMap.values()){
             orderList.add(obj);
         }
         return orderList;
     }

     public int getCountOfUnassignedOrdersFromDB(){
         int assignedCount = 0;
         for(String key : orderPartnerPairMap.keySet()){
             List<String> orderList = orderPartnerPairMap.get(key);
             assignedCount += orderList.size();
         }
         return orderHashMap.size() - assignedCount;
     }


     public void deletePartnerIdFromDB(String partnerId){
         partnerHashMap.remove(partnerId);
         orderPartnerPairMap.remove(partnerId);
     }

     public void deleteOrderByIdFromDB(String orderId){
         orderHashMap.remove(orderId);
         for(String key : orderPartnerPairMap.keySet()){
             List<String> orderList = orderPartnerPairMap.get(key);
             if(orderList.indexOf(orderId) != -1){
                 int idx = orderList.indexOf(orderId);
                 orderList.remove(idx);
                 orderPartnerPairMap.put(key,orderList);
             }
         }
     }




}
