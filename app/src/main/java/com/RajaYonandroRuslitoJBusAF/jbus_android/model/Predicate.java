package com.RajaYonandroRuslitoJBusAF.jbus_android.model;

/**
 * A functional interface representing a predicate (boolean-valued function) of one argument.
 *
 * @param /<T> the type of the input to the predicate
 */
public interface Predicate<T> {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param /val the input argument
     * @return {@code true} if the input argument matches the predicate, otherwise {@code false}
     */
    public boolean predicate(T t);
}