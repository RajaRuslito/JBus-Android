package com.RajaYonandroRuslitoJBusAF.jbus_android.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Algorithm {

    private Algorithm() {

    }

    public static <T> boolean exists(Iterator<T> it, Predicate<T> pr) {
        while(it.hasNext()) {
            T current = it.next();
            if(pr.predicate(current)) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean exists(Iterator<T> it, T t) {
        final Predicate<T> pr = t::equals;
        return exists(it, pr);
    }

    public static <T> boolean exists(Iterable<T> il, Predicate<T> pr) {
        final Iterator<T> it = il.iterator();
        return exists(it, pr);
    }

    public static <T> boolean exists(Iterable<T> il, T t) {
        final Iterator<T> it = il.iterator();
        return exists(it, t);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static <T> boolean exists(T[] tArr, T t) {
        final Iterator<T> it = Arrays.stream(tArr).iterator();
        return exists(it, t);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static <T> boolean exists(T[] tArr, Predicate<T> pr) {
        final Iterator<T> it = Arrays.stream(tArr).iterator();
        return exists(it, pr);
    }

    public static <T> int count(Iterator<T> it, T t) {
        final Predicate<T> pr = t::equals;
        return count(it, pr);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static <T> int count(T[] tArr, T t) {
        final Iterator<T> it = Arrays.stream(tArr).iterator();
        return count(it, t);
    }

    public static <T> int count(Iterator<T> it, Predicate<T> pr) {
        int c = 0;
        while(it.hasNext()) {
            T current = it.next();
            if(pr.predicate(current)) {
                c++;
            }
        }
        return c;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static <T> int count(T[] tArr, Predicate<T> pr) {
        final Iterator<T> it = Arrays.stream(tArr).iterator();
        return count(it, pr);
    }

    public static <T> int count(Iterable<T> il, T t) {
        final Iterator<T> it = il.iterator();
        return count(it, t);
    }

    public static <T> int count(Iterable<T> il, Predicate<T> pr) {
        final Iterator<T> it = il.iterator();
        return count(it, pr);
    }

    public static <T> List<T> collect(Iterable<T> il, Predicate<T> pr) {
        final Iterator<T> it = il.iterator();
        return collect(it, pr);
    }

    public static <T> List<T> collect(Iterable<T> il, T t) {
        final Iterator<T> it = il.iterator();
        return collect(it, t);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static <T> List<T> collect(T[] tArr, T t) {
        final Iterator<T> it = Arrays.stream(tArr).iterator();
        return collect(it, t);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static <T> List<T> collect(T[] tArr, Predicate<T> pr) {
        final Iterator<T> it = Arrays.stream(tArr).iterator();
        return collect(it, pr);
    }

    public static <T> List<T> collect(Iterator<T> it, T t) {
        Predicate<T> pr = t::equals;
        return collect(it, pr);
    }

    public static <T> List<T> collect(Iterator<T> it, Predicate<T> pr) {
        List<T> list = new ArrayList<>();
        while(it.hasNext()) {
            T current = it.next();
            if(pr.predicate(current)) {
                list.add(current);
            }
        }
        return list;
    }

    public static <T> T find(Iterable<T> il, Predicate<T> pr) {
        final Iterator<T> it = il.iterator();
        return find(it, pr);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static <T> T find(T[] tArr, T t) {
        final Iterator<T> it = Arrays.stream(tArr).iterator();
        return find(it, t);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static <T> T find(T[] tArr, Predicate<T> pr) {
        final Iterator<T> it = Arrays.stream(tArr).iterator();
        return find(it, pr);
    }

    public static <T> T find(Iterator<T> it, T t) {
        final Predicate<T> pr = t::equals;
        return find(it, pr);
    }

    public static <T> T find(Iterable<T> il, T t) {
        final Iterator<T> it = il.iterator();
        return find(it, t);
    }

    public static <T> T find(Iterator<T> it, Predicate<T> pr) {
        while(it.hasNext()) {
            T current = it.next();
            if(pr.predicate(current)) {
                return current;
            }
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static <T> List<T> paginate(T[] tArr, int page, int pageSize, Predicate<T> pr) {
        Iterator<T> it = Arrays.stream(tArr).iterator();
        return paginate(it, page, pageSize, pr);
    }

    public static <T> List<T> paginate(Iterable<T> il, int page, int pageSize, Predicate<T> pr) {
        Iterator<T> it = il.iterator();
        return paginate(it, page, pageSize, pr);
    }

    public static <T> List<T> paginate(Iterator<T> it, int page, int pageSize, Predicate<T> pr) {
        List<T> list = new ArrayList<>();
        int index = 0;
        int interregnum = page * pageSize;
        while(it.hasNext()) {
            T current = it.next();
            if(pr.predicate(current) && (interregnum <= index && index < interregnum + pageSize)) {
                list.add(current);
            }
            index++;
        }

        return list;
    }
}
