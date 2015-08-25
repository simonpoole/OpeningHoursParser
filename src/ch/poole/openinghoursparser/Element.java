package ch.poole.openinghoursparser;
/**
 * Force implementation of equals and hashcode 
 * @author simon
 *
 */
abstract class Element {
   // put other methods from Command interface here

   public abstract boolean equals(Object other);
   public abstract int hashCode();
}
