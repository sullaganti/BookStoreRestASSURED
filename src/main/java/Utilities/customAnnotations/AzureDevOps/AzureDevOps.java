package Utilities.customAnnotations.AzureDevOps;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)

@Target(ElementType.METHOD)
public @interface AzureDevOps {
    int bugID() default 0;
    int workItemID() default 0;
}
