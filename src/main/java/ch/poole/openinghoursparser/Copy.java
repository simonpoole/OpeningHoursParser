package ch.poole.openinghoursparser;

public interface Copy<T> {
    /**
     * Create a copy of the objecz
     * 
     * @return a copy of the object
     */
    public T copy();
}
