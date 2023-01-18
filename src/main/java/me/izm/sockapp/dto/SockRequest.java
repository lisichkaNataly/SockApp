package me.izm.sockapp.dto;

import me.izm.sockapp.model.Colour;
import me.izm.sockapp.model.Size;
import me.izm.sockapp.model.Sock;
import org.yaml.snakeyaml.scanner.ScannerImpl;

public class SockRequest {
    private Colour colour;
    private Size size;
    private int cottonPercentage;
    private int quantity;

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public int getCottonPercentage() {
        return cottonPercentage;
    }

    public void setCottonPart(int cottonPart) {
        this.cottonPercentage = cottonPercentage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
