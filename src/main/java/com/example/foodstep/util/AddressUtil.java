package com.example.foodstep.util;

public class AddressUtil {

    // **시 **동 or **구 **동 (서울, 부산 등)
    public static String shortenToSiOrGuAndDong(String address) {
        String[] addressList = address.split(" ");
        if (address.contains("구")) {
            for (int i = 0; i < addressList.length - 1; i++) {
                if (addressList[i].contains("구")) {
                    if (i + 1 < addressList.length) {
                        return addressList[i] + " " + addressList[i + 1];
                    }
                }
            }
        } else {
            for (int i = 0; i < addressList.length - 1; i++) {
                if (addressList[i].contains("시")) {
                    if (i + 1 < addressList.length) {
                        return addressList[i] + " " + addressList[i + 1];
                    }
                }
            }
        }
        return addressList[0] + " " + addressList[1];
    }
}
