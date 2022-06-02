package com.example.productapp.dao;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.productapp.database.DataBaseHelper;
import com.example.productapp.dto.DataDTO;
import com.example.productapp.dto.ProductDTO;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    DataBaseHelper dataBaseHelper;

    public ProductDAO(Application application) {
        dataBaseHelper = DataBaseHelper.getInstance(application);

    }

    public void deleteAll(){
        String sql = "DELETE FROM " + DataBaseHelper.TABLE_PRODUCT;
        dataBaseHelper.execSql(sql);
    }

    public Boolean insertProduct(ProductDTO productDTO) {
        return dataBaseHelper.insert(DataBaseHelper.TABLE_PRODUCT, values(productDTO));
    }


    public Boolean updateProduct(ProductDTO productDTO) {
        return dataBaseHelper.update(DataBaseHelper.TABLE_PRODUCT,
                DataBaseHelper.COLUMN_ID + "=?",
                values(productDTO), new String[]{String.valueOf(productDTO.getId())});
    }

    public List<ProductDTO> getProducts(){
        List<ProductDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM " + DataBaseHelper.TABLE_PRODUCT;
        Cursor cursor = dataBaseHelper.query(sql, null);
        while(cursor.moveToNext()){
            list.add(new ProductDTO(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getFloat(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getString(7)));
        }
        return list;
    }

    public List<ProductDTO> getProductsLimit(int offset, int limit){
        List<ProductDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM " + DataBaseHelper.TABLE_PRODUCT + "LIMIT " + limit + " OFFSET " + offset;
        Cursor cursor = dataBaseHelper.query(sql, null);
        while(cursor.moveToNext()){
            list.add(new ProductDTO(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getFloat(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getString(7)));
        }
        return list;
    }

    public ContentValues values(ProductDTO productDTO) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.COLUMN_ID, productDTO.getId());
        values.put(DataBaseHelper.COLUMN_NAME, productDTO.getName());
        values.put(DataBaseHelper.COLUMN_PRICE, productDTO.getPrice());
        values.put(DataBaseHelper.COLUMN_SHORT_DESCRIPTION, productDTO.getShort_description());
        values.put(DataBaseHelper.COLUMN_RATING_AVERAGE, productDTO.getRating_average());
        values.put(DataBaseHelper.COLUMN_QUANTITY_SOLD, productDTO.getQuanttitySold().getValue());
        values.put(DataBaseHelper.COLUMN_QUANTITY_SOLD_TEXT, productDTO.getQuanttitySold().getText());
        values.put(DataBaseHelper.COLUMN_THUMBNAIL, productDTO.getThumbnail_url());
        return values;
    }

    public boolean updateProducts(List<ProductDTO> list){
        for (ProductDTO productDTO : list){
            if(!insertProduct(productDTO)) updateProduct(productDTO);
        }
        return true;
    }

    public List<ProductDTO> searchProductBySameNameOrPrice(String search) {
        List<ProductDTO> list = new ArrayList<>();
        String like = "%"+search+"%";
        String query = "SELECT * FROM " + DataBaseHelper.TABLE_PRODUCT +
                " WHERE " + DataBaseHelper.COLUMN_NAME + " LIKE ? OR " +
                " CAST("+DataBaseHelper.COLUMN_PRICE+" AS TEXT) LIKE ? OR CAST("+DataBaseHelper.COLUMN_NAME+" AS TEXT) LIKE ?";
        Cursor cursor =  dataBaseHelper.query(query, new String[]{like, like, like});
        while(cursor.moveToNext()){
            list.add(new ProductDTO(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getFloat(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getString(7)));
        }
        return list;

    }


}
