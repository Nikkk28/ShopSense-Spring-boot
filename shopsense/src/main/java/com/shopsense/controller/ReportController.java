package com.shopsense.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopsense.service.ReportService;
import com.shopsense.dto.AdminStat;
import com.shopsense.dto.SalesReportDto;
import com.shopsense.dto.SellerStat;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ReportController {

	@Autowired
	ReportService reportService;

	@GetMapping(value = "/admin/stat")
	public AdminStat getAdminStat() {
		return reportService.getAdminStat();
	}

	@GetMapping(value = "/seller/stat")
	public SellerStat getSellerStat(@RequestParam(name = "sellerId") int sellerId) {
		return reportService.getSellerStat(sellerId);
	}

	@GetMapping(value = "/seller/report/sales")
	public List<SalesReportDto> getSellerSalesReport(@RequestParam int sellerId, @RequestParam String startDate,
			@RequestParam String endDate) {
		return reportService.getSellerSalesReport(sellerId, startDate, endDate);
	}

	@GetMapping(value = "/admin/report/sales")
	public List<SalesReportDto> getAdminSalesReport(@RequestParam String startDate, @RequestParam String endDate) {
		return reportService.getAdminSalesReport(startDate, endDate);
	}
}
