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
   
   /**
    * Create a string that identifies which class created it for debugging and testing
    * 
    * @return the output of toString with the class name prepended
    */
   public String toDebugString() {
	   return getClass().getSimpleName() +":"+toString();
   }
}
