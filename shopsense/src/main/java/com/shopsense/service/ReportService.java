package com.shopsense.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopsense.dto.AdminStat;
import com.shopsense.dto.SalesReportDto;
import com.shopsense.dto.SellerStat;
import com.shopsense.repository.OrderRepository;
import com.shopsense.repository.ProductRepository;
import com.shopsense.repository.CustomerRepository;
import com.shopsense.repository.SellerRepository;

@Service
public class ReportService {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private SellerRepository sellerRepository;

    public AdminStat getAdminStat() {
        AdminStat stat = new AdminStat();
        stat.setTotalCustomers(customerRepository.countActiveCustomers());
        stat.setTotalSellers(sellerRepository.countActiveSellers());
        stat.setTotalProducts(productRepository.countActiveProducts());
        stat.setTotalOrders(orderRepository.countTotalOrders());
        stat.setTotalRevenue(orderRepository.getTotalRevenue());
        return stat;
    }

    public SellerStat getSellerStat(int sellerId) {
        SellerStat stat = new SellerStat();
        stat.setTotalProducts(productRepository.countProductsBySeller(sellerId));
        stat.setTotalOrders(orderRepository.countOrdersBySeller(sellerId));
        stat.setTotalRevenue(orderRepository.getRevenueBySellerr(sellerId));
        stat.setPendingOrders(orderRepository.countPendingOrdersBySeller(sellerId));
        return stat;
    }

    public List<SalesReportDto> getSellerSalesReport(int sellerId, String startDate, String endDate) {
        return orderRepository.getSellerSalesReport(sellerId, startDate, endDate);
    }

    public List<SalesReportDto> getAdminSalesReport(String startDate, String endDate) {
        return orderRepository.getAdminSalesReport(startDate, endDate);
    }
}
