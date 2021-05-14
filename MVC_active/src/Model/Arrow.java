package Model;

import java.util.Objects;

public class Arrow {
    private String color;
    private String direction;

    public Arrow(String color, String direction) {
        this.color = color;
        this.direction = direction;
    }
    public Arrow(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return color.toLowerCase() + direction.toUpperCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arrow arrow = (Arrow) o;
        return direction.equals(arrow.direction);
    }
}
