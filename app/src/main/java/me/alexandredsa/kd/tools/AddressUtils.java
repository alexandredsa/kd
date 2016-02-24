package me.alexandredsa.kd.tools;

import android.location.Address;

/**
 * Created by Alexandre on 22/02/2016.
 */
public class AddressUtils {

    public static String extractAddressLines(Address address) {
        String resultAddress = null;
        for (int i = 0, tam = address.getMaxAddressLineIndex(); i < tam; i++) {
            resultAddress += address.getAddressLine(i);
            resultAddress += i < tam - 1 ? ", " : "";
        }

        return resultAddress;
    }
}
