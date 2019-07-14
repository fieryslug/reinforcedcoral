package com.fieryslug.reinforcedcoral.util;

public class Pair<T, U> {

    public T t;
    public U u;

    public Pair(T t, U u) {
        this.t = t;
        this.u = u;
    }

    @Override
    public boolean equals(Object o) {

        if(o==null || !(o instanceof Pair)) return false;

        Pair other = (Pair)o;
        return this.t.equals(other.t) && this.u.equals(other.u);

    }

    @Override
    public int hashCode() {
        return this.t.hashCode() + this.u.hashCode();
    }
}
