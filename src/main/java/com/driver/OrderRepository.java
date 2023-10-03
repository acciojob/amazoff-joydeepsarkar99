package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {
     HashMap<String,Order> orderHashMap;
     HashMap<String,Order> unassignedOrderMap;
     HashMap<String,DeliveryPartner> partnerHashMap;
     HashMap<String, List<String>> orderPartnerPairMap;

     OrderRepository(){
         orderHashMap = new HashMap<>();
         unassignedOrderMap = new HashMap<>();
         partnerHashMap = new HashMap<>();
         orderPartnerPairMap = new HashMap<>();
     }
     public void addOrderToDB(Order order){
         String key = order.getId();
         orderHashMap.put(key,order);
         unassignedOrderMap.put(key,order);
     }

     public void addPartnerToDB(String partnerId){
         partnerHashMap.put(partnerId,new DeliveryPartner(partnerId));
     }

     public void addOrderPartnerPairToDB(String orderId, String partnerId){
         if(unassignedOrderMap.containsKey(orderId)){
             unassignedOrderMap.remove(orderId);
         }

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
         if(partnerHashMap.containsKey(partnerId)) return partnerHashMap.get(partnerId);
         return null;
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
         if(unassignedOrderMap.size() != 0) return unassignedOrderMap.size();
         return 0;
     }


     public void deletePartnerIdFromDB(String partnerId){
         if(orderPartnerPairMap.containsKey(partnerId)){
             List<String> orderList = orderPartnerPairMap.get(partnerId);
             orderPartnerPairMap.remove(partnerId);
             for(String orderId : orderList){
                 unassignedOrderMap.put(orderId,orderHashMap.get(orderId));
             }
         }
     }

     public void deleteOrderByIdFromDB(String orderId){
         orderHashMap.remove(orderId);
         unassignedOrderMap.remove(orderId);
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
