package org.kerflyn.javafp;

public class Tuple2<T0, T1> {
    public T0 _0;
    public T1 _1;

    public Tuple2(T0 _0, T1 _1) {
        this._0 = _0;
        this._1 = _1;
    }

    public Object get(int i) {
        switch (i) {
            case 0: return _0;
            case 1: return _1;
            default: throw new IndexOutOfBoundsException();
        }
    }

    public static <T0, T1> Tuple2<T0, T1> Tuple2(T0 _0, T1 _1) {
        return new Tuple2<T0, T1>(_0, _1);
    }

}
