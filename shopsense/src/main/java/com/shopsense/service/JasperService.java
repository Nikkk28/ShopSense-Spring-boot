//package com.shopsense.service;
//
//import java.util.HashMap;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.shopsense.repository.OrderRepository;
//import com.shopsense.repository.ProductRepository;
//import com.shopsense.repository.CustomerRepository;
//import com.shopsense.repository.SellerRepository;
//import com.shopsense.repository.RevenueProfitRepository;
//
//@Service
//public class JasperService {
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @Autowired
//    private SellerRepository sellerRepository;
//
//    @Autowired
//    private RevenueProfitRepository revenueProfitRepository;
//
//    public List<HashMap<String, String>> getAdminSalesReportGroupBySeller(String startDate, String endDate) {
//        return orderRepository.getAdminSalesReportGroupBySeller(startDate, endDate);
//    }
//
//    public List<HashMap<String, String>> getProductDetailsReport() {
//        return productRepository.getProductDetailsReport();
//    }
//
//    public List<HashMap<String, String>> getFavoriteItemReport() {
//        return productRepository.getFavoriteItemReport();
//    }
//
//    public List<HashMap<String, String>> getCustomerReport() {
//        return customerRepository.getCustomerReport();
//    }
//
//    public List<HashMap<String, String>> getAdminProfitReport() {
//        return revenueProfitRepository.getAdminProfitReport();
//    }
//
//    public List<HashMap<String, String>> getSellerReport() {
//        return sellerRepository.getSellerReport();
//    }
//
//    public HashMap<String, Object> getCustomerOrderById(int id) {
//        return orderRepository.getCustomerOrderById(id);
//    }
//
//    public List<HashMap<String, String>> getStockAlertReport(int sellerId) {
//        return productRepository.getStockAlertReport(sellerId);
//    }
//
//    public List<HashMap<String, String>> getTopSellingReport(int sellerId) {
//        return productRepository.getTopSellingReport(sellerId);
//    }
//
//    public HashMap<String, Object> getInvoiceByOrderId(int orderId) {
//        return orderRepository.getInvoiceByOrderId(orderId);
//    }
//}
