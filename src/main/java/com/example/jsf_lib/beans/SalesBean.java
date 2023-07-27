package com.example.jsf_lib.beans;

import com.example.jsf_lib.dao.DAO;
import com.example.jsf_lib.models.Book;
import com.example.jsf_lib.models.Sales;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ManagedBean
@ViewScoped
public class SalesBean {
    public BarChartModel getSalesModel() {
        BarChartModel barModel = new BarChartModel();
        ChartData data = new ChartData();

        List<BarChartDataSet> barDataSetList = new ArrayList<>();
        List<List<Sales>> salesList = new ArrayList<>();
        List<List<Number>> quantityList = new ArrayList<>();

        List<String> bgColorList = new ArrayList<>();
        bgColorList.add("rgba(255, 99, 132, 0.2)");
        bgColorList.add("rgba(54, 162, 235, 0.2)");

        List<String> borderColorList = new ArrayList<>();
        borderColorList.add("rgb(255, 99, 132)");
        borderColorList.add("rgb(54, 162, 235)");

        List<List<String>> booksList = new ArrayList<>();
        for (int i = 0; i <= 1; i++) {
            BarChartDataSet barDataSet = new BarChartDataSet();
            barDataSet.setLabel("Sales" + (i + 2022));

            barDataSetList.add(barDataSet);

            List<Sales> sales = getSales(i + 2022);
            salesList.add(sales);

            List<Number> quantities = new ArrayList<>();
            for (Sales sale : salesList.get(i)) {
                quantities.add(sale.getQuantity());
            }
            barDataSetList.get(i).setData(quantities);
            quantityList.add(quantities);

            barDataSetList.get(i).setBackgroundColor(bgColorList.get(i));

            barDataSetList.get(i).setBorderColor(borderColorList.get(i));
            barDataSetList.get(i).setBorderWidth(1);

            data.addChartDataSet(barDataSetList.get(i));

            List<String> books = new ArrayList<>();
            for (Sales sale : salesList.get(i)) {
                books.add(sale.getBook().getTitle());
            }
            booksList.add(books);

            data.setLabels(booksList.get(i));
        }

        barModel.setData(data);

        return barModel;
    }

    public List<Sales> getSales(long seed) {
        List<Book> books = new DAO<Book>(Book.class).getAll();
        List<Sales> sales = new ArrayList<Sales>();

        Random random = new Random(seed);

        for(Book book : books) {
            Integer quantity = random.nextInt(500);

            sales.add(new Sales(book, quantity));
        }

        return sales;
    }
}
