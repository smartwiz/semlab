package com.semlab.server.enrichment.util;

public interface Proximity<E> {

    /**
     * Returns the distance between the specified pair of objects.
     *
     * @param e1 First object.
     * @param e2 Second object.
     * @return Proximity between the two objects.
     */
    public double proximity(E e1, E e2);

}
