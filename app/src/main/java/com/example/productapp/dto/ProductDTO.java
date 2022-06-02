package com.example.productapp.dto;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class ProductDTO {
    private int id;
    private String name;
    private String short_description;
    private float rating_average;
    @SerializedName("quantity_sold")
    private QuanttitySold quanttitySold ;
    private int price ;
    private String thumbnail_url;

    public ProductDTO(int id, String name, String short_description, float rating_average, int quanttitySold,String soldText, int price, String thumbnail_url) {
        this.id = id;
        this.name = name;
        this.short_description = short_description;
        this.rating_average = rating_average;
        this.quanttitySold = new QuanttitySold(soldText, quanttitySold);
        this.price = price;
        this.thumbnail_url = thumbnail_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public QuanttitySold getQuanttitySold() {
        return quanttitySold;
    }

    public void setQuanttitySold(QuanttitySold quanttitySold) {
        this.quanttitySold = quanttitySold;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public float getRating_average() {
        return rating_average;
    }

    public void setRating_average(float rating_average) {
        this.rating_average = rating_average;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDTO)) return false;
        ProductDTO that = (ProductDTO) o;
        return Float.compare(that.getRating_average(), getRating_average()) == 0 &&
                getPrice() == that.getPrice() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getShort_description(), that.getShort_description()) &&
                Objects.equals(getQuanttitySold(), that.getQuanttitySold()) &&
                Objects.equals(getThumbnail_url(), that.getThumbnail_url()) &&
                Integer.compare(that.getId(), getId())==0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),getName(), getShort_description(), getRating_average(), getPrice(), getQuanttitySold(), getThumbnail_url());
    }

    public static DiffUtil.ItemCallback<ProductDTO> itemCallback = new DiffUtil.ItemCallback<ProductDTO>() {
        @Override
        public boolean areItemsTheSame(@NonNull ProductDTO oldItem, @NonNull ProductDTO newItem) {
            return oldItem.getId()== newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProductDTO oldItem, @NonNull ProductDTO newItem) {
            return oldItem.equals(newItem);
        }
    };
}
