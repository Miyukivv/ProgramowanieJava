public class ParentingAgeException extends Exception{
    public ParentingAgeException(String name) {
        super(String.format("The parent is younger than 15 years of age or deceased at the time of the child's birth:  %s",name));
    }
}
