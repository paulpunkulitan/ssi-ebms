package ph.com.smesoft.ebms.service;

import java.util.List;

import ph.com.smesoft.ebms.domain.Sales;

public interface SalesService {

	public abstract long countAllSales();


	public abstract void deleteSales(Sales sales);


	public abstract Sales findSales(Long id);


	public abstract List<Sales> findAllSales();


	public abstract List<Sales> findSalesEntries(int firstResult, int maxResults);


	public abstract void saveSales(Sales sales);


	public abstract Sales updateSales(Sales sales);

 
	public abstract List<Sales> findAllSalesBySearch(String searchString);


}
