package sv.edu.facturacion.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Salvador Peña
 */
public class Test {
    public static void main(String[] args) {
        // TODO code application logic here
        String nombre = "salvador";
        System.out.println( DigestUtils.sha512Hex(nombre));
    }

}
