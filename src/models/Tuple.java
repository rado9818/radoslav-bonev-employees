package models;

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
}
