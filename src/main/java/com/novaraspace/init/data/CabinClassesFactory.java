package com.novaraspace.init.data;

import com.novaraspace.model.entity.CabinClass;
import com.novaraspace.model.enums.CabinClassEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CabinClassesFactory {

    private static Map<Integer, CabinClass> firstClasses = new HashMap<Integer, CabinClass>();
    private static Map<Integer, CabinClass> middleClasses = new HashMap<Integer, CabinClass>();
    private static Map<Integer, CabinClass> lowerClasses = new HashMap<Integer, CabinClass>();

    private static void initAll() {
        firstClasses = createFirstClasses();
        middleClasses = createMiddleClasses();
        lowerClasses = createLowerClasses();
    }


    public static Map<Integer, CabinClass> getFirstClasses() {
        if (firstClasses.isEmpty()) {
            initAll();
        }
        return firstClasses;
    }

    public static Map<Integer, CabinClass> getMiddleClasses() {
        if (middleClasses.isEmpty()) {
            initAll();
        }
        return middleClasses;
    }

    public static Map<Integer, CabinClass> getLowerClasses() {
        if (lowerClasses.isEmpty()) {
            initAll();
        }
        return lowerClasses;
    }

    private static Map<Integer, CabinClass> createFirstClasses() {
        List<CabinClass> classList = new ArrayList<CabinClass>();
        for (int i = 4; i < 26; i+=2) {
            CabinClass cabinClass = new CabinClass()
                    .setType(CabinClassEnum.FIRST)
                    .setTotalSeats(i)
                    .setWindowAvailable(true);
            if (i == 16 || i == 20 || i == 24) {
                cabinClass.setWindowAvailable(false);
            }
            classList.add(cabinClass);
        }
        return classList.stream().collect(Collectors.toMap((c) -> c.getTotalSeats(), (c) -> c));
    }

    private static Map<Integer, CabinClass> createMiddleClasses() {
        List<CabinClass> classList = new ArrayList<CabinClass>();
        for (int i = 6; i < 40; i+=2) {
            CabinClass cabinClass = new CabinClass()
                    .setType(CabinClassEnum.MIDDLE)
                    .setTotalSeats(i)
                    .setWindowAvailable(true);
            if ( (i >= 12 && i <= 22) || i > 26) {
                cabinClass.setWindowAvailable(false);
            }
            classList.add(cabinClass);
        }
        return classList.stream().collect(Collectors.toMap((c) -> c.getTotalSeats(), (c) -> c));
    }

    private static Map<Integer, CabinClass> createLowerClasses() {
        List<CabinClass> classList = new ArrayList<CabinClass>();
        for (int i = 4; i < 44; i+=2) {
            CabinClass cabinClass = new CabinClass()
                    .setType(CabinClassEnum.LOWER)
                    .setTotalSeats(i)
                    .setWindowAvailable(true);
            if ((i >= 10 && i <= 14) || i > 16) {
                cabinClass.setWindowAvailable(false);
            }
            classList.add(cabinClass);
        }
        return classList.stream().collect(Collectors.toMap((c) -> c.getTotalSeats(), (c) -> c));
    }
}
