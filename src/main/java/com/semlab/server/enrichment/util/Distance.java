package com.semlab.server.enrichment.util;

public interface Distance<E> {

    /**
     * Returns the distance between the specified pair of objects.
     *
     * @param e1 First object.
     * @param e2 Second object.
     * @return Distance between the two objects.
     */
    public double distance(E e1, E e2);

}
