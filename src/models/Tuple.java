package models;

import java.util.Objects;

public class Tuple<T, K> {
    T data1;
    K data2;

    public Tuple(T data1, K data2) {
        this.data1 = data1;
        this.data2 = data2;
    }

    public T getData1() {
        return data1;
    }

    public void setData1(T data1) {
        this.data1 = data1;
    }

    public K getData2() {
        return data2;
    }

    public void setData2(K data2) {
        this.data2 = data2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return Objects.equals(data1, tuple.data1) &&
                Objects.equals(data2, tuple.data2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data1, data2);
    }
}
