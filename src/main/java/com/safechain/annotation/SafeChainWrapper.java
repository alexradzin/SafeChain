package com.safechain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.safechain.NullSafeChain;

/**
 * This annotation marks class or method that is used as a entry point or "wrapper" of object 
 * to start the safe execution chain.
 *
 * Being applied to class it makes all its method to become wrapper. 
 * 
 * For example if we want to create method {@code safe()} that allow calls directly or indirectly 
 * {@link NullSafeChain#nullsafe(Object)} this method should be marked with this annotattion. 
 * If we want to implement utility class that contains only methods that call {@link NullSafeChain#nullsafe(Object)}
 * this annotation can be placed on class itself.   
 *    
 * @author alexr
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SafeChainWrapper {
}
