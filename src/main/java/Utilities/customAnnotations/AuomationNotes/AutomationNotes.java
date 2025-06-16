package Utilities.customAnnotations.AuomationNotes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
public @interface AutomationNotes {

    String automationAuthor() default "";
    String manualAuthor() default "";
    String[] automationNote() default {};

}
