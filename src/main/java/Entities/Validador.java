package Entities;

import java.util.function.Predicate;

public class Validador {
    public static <T> void validar (T valor, Predicate<T> condicion, RuntimeException ex){
        if (condicion.test(valor)){
            throw ex;
        }
    }
}
