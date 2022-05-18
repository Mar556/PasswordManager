/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author Deniss
 */
@Entity
public class Sneaker implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sneakerModel;
    private String sneakerFirm;
    private int sneakerSize;
    private double sneakerPrice;
    private int sneakerQuantity;
    
    public Sneaker() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSneakerModel() {
        return sneakerModel;
    }

    public void setSneakerModel(String sneakerModel) {
        this.sneakerModel = sneakerModel;
    }

    public String getSneakerFirm() {
        return sneakerFirm;
    }

    public void setSneakerFirm(String sneakerFirm) {
        this.sneakerFirm = sneakerFirm;
    }

    public double getSneakerSize() {
        return sneakerSize;
    }

    public void setSneakerSize(int sneakerSize) {
        this.sneakerSize = sneakerSize;
    }

    public double getSneakerPrice() {
        return sneakerPrice;
    }

    public void setSneakerPrice(double sneakerPrice) {
        this.sneakerPrice = sneakerPrice;
    }

    public int getSneakerQuantity() {
        return sneakerQuantity;
    }

    public void setSneakerQuantity(int sneakerQuantity) {
        this.sneakerQuantity = sneakerQuantity;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.sneakerModel);
        hash = 97 * hash + Objects.hashCode(this.sneakerFirm);
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.sneakerSize) ^ (Double.doubleToLongBits(this.sneakerSize) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.sneakerPrice) ^ (Double.doubleToLongBits(this.sneakerPrice) >>> 32));
        hash = 97 * hash + this.sneakerQuantity;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sneaker other = (Sneaker) obj;
        if (Double.doubleToLongBits(this.sneakerSize) != Double.doubleToLongBits(other.sneakerSize)) {
            return false;
        }
        if (Double.doubleToLongBits(this.sneakerPrice) != Double.doubleToLongBits(other.sneakerPrice)) {
            return false;
        }
        if (this.sneakerQuantity != other.sneakerQuantity) {
            return false;
        }
        if (!Objects.equals(this.sneakerModel, other.sneakerModel)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.sneakerFirm, other.sneakerFirm)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "Sneaker{" + "id=" + id + ", sneakerModel=" + sneakerModel + ", sneakerFirm=" + sneakerFirm + ", sneakerSize=" + sneakerSize + ", sneakerPrice=" + sneakerPrice + ", sneakerQuantity=" + sneakerQuantity + '}';
    }      
}
