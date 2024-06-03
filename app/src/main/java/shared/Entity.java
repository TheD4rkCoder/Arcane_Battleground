package shared;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Entity implements Serializable {

    private static final long serialVersionUID = 1567L;
    private float x;
    private float y;
    private String id;

    public Entity(float x, float y, String id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        assert obj != null;
        return ((Entity)obj).id.equals(this.id);
    }
}
