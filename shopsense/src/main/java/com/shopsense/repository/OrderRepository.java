package com.shopsense.repository;

import java.util.HashMap;
import java.util.List;

import com.shopsense.dto.SalesReportDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopsense.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	List<Order> findByCustomerIdOrderByIdDesc(int customerId);
	
	@Query("SELECT DISTINCT o FROM Order o JOIN OrderDetails od ON o.id = od.orderId " +
		   "WHERE od.sellerId = :sellerId ORDER BY o.id DESC")
	List<Order> findOrdersBySellerId(@Param("sellerId") int sellerId);
	
	@Query("SELECT DISTINCT o FROM Order o JOIN OrderDetails od ON o.id = od.orderId " +
		   "WHERE od.status = 'Shipped' ORDER BY o.id DESC")
	List<Order> findShippedOrders();

	@Query("SELECT COUNT(o) FROM Order o")
	long countTotalOrders();

	@Query("SELECT COALESCE(SUM(o.orderTotal), 0) FROM Order o WHERE o.status = 'Completed'")
	double getTotalRevenue();

	@Query("SELECT COUNT(DISTINCT o.id) FROM Order o JOIN OrderDetails od ON o.id = od.orderId WHERE od.sellerId = :sellerId")
	int countOrdersBySeller(@Param("sellerId") int sellerId);

	@Query("SELECT COALESCE(SUM(od.subTotal), 0) FROM OrderDetails od WHERE od.sellerId = :sellerId AND od.status = 'Delivered'")
	double getRevenueBySellerr(@Param("sellerId") int sellerId);

	@Query("SELECT COUNT(DISTINCT o.id) FROM Order o JOIN OrderDetails od ON o.id = od.orderId WHERE od.sellerId = :sellerId AND od.status IN ('Pending', 'Processing')")
	int countPendingOrdersBySeller(@Param("sellerId") int sellerId);

	@Query("SELECT new com.shopsense.dto.SalesReportDto(DATE(od.deliveryDate), COUNT(od.id), SUM(od.subTotal), 0, SUM(od.subTotal * 0.98)) " +
			"FROM OrderDetails od WHERE od.sellerId = :sellerId AND od.deliveryDate BETWEEN :startDate AND :endDate AND od.status = 'Delivered' " +
			"GROUP BY DATE(od.deliveryDate) ORDER BY DATE(od.deliveryDate)")
	List<SalesReportDto> getSellerSalesReport(@Param("sellerId") int sellerId, @Param("startDate") String startDate, @Param("endDate") String endDate);

	@Query("SELECT new com.shopsense.dto.SalesReportDto(DATE(od.deliveryDate), COUNT(od.id), SUM(od.subTotal), 0, SUM(od.subTotal * 0.02)) " +
			"FROM OrderDetails od WHERE od.deliveryDate BETWEEN :startDate AND :endDate AND od.status = 'Delivered' " +
			"GROUP BY DATE(od.deliveryDate) ORDER BY DATE(od.deliveryDate)")
	List<SalesReportDto> getAdminSalesReport(@Param("startDate") String startDate, @Param("endDate") String endDate);

	@Query("SELECT new java.util.HashMap(map('sellerId', od.sellerId, 'storeName', od.storeName, 'totalSales', SUM(od.subTotal), 'totalOrders', COUNT(od.id))) " +
			"FROM OrderDetails od WHERE od.deliveryDate BETWEEN :startDate AND :endDate AND od.status = 'Delivered' " +
			"GROUP BY od.sellerId, od.storeName ORDER BY SUM(od.subTotal) DESC")
	List<HashMap<String, String>> getAdminSalesReportGroupBySeller(@Param("startDate") String startDate, @Param("endDate") String endDate);

	@Query("SELECT new java.util.HashMap(map('name', c.name, 'email', c.email, 'address', o.shippingStreet + ', ' + o.shippingCity + ', ' + o.shippingState, 'orders', " +
			"(SELECT new java.util.HashMap(map('orderId', od2.orderId, 'productName', od2.productName, 'quantity', od2.quantity, 'subTotal', od2.subTotal)) " +
			"FROM OrderDetails od2 WHERE od2.orderId = :id))) " +
			"FROM Customer c JOIN Order o ON c.id = o.customerId WHERE o.id = :id")
	HashMap<String, Object> getCustomerOrderById(@Param("id") int id);

	@Query("SELECT new java.util.HashMap(map('id', o.id, 'street', o.shippingStreet, 'city', o.shippingCity, 'state', o.shippingState, " +
			"'subTotal', o.subTotal, 'gatewayFee', o.gatewayFee, 'shippingCharge', o.shippingCharge, 'discount', o.discount, 'tax', o.tax, 'orderTotal', o.orderTotal, " +
			"'items', (SELECT new java.util.HashMap(map('productName', od2.productName, 'quantity', od2.quantity, 'unitPrice', od2.productUnitPrice, 'subTotal', od2.subTotal)) " +
			"FROM OrderDetails od2 WHERE od2.orderId = :orderId))) " +
			"FROM Order o WHERE o.id = :orderId")
	HashMap<String, Object> getInvoiceByOrderId(@Param("orderId") int orderId);
	
	@Query("SELECT o FROM Order o JOIN OrderDetails od ON o.id = od.orderId " +
		   "ORDER BY o.id DESC")
	List<Order> findAllOrdersWithDetails();
}
