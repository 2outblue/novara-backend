package com.novaraspace.init;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novaraspace.model.entity.CabinClass;
import com.novaraspace.model.enums.CabinClassEnum;
import com.novaraspace.model.other.CabinClassJSON;
import com.novaraspace.repository.CabinClassRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Component
@Order(1)
public class CabinClassDBInit implements CommandLineRunner {

    private final ObjectMapper mapper;
    private final CabinClassRepository cabinClassRepository;

    public CabinClassDBInit(ObjectMapper mapper, CabinClassRepository cabinClassRepository) {
        this.mapper = mapper;
        this.cabinClassRepository = cabinClassRepository;
    }



    @Override
    public void run(String... args) throws Exception {
        if (cabinClassRepository.count() == 0) {
            initCabinClasses();
        }
    }

    private void initCabinClasses() throws IOException {
        ClassPathResource resource = new ClassPathResource("data/CabinClasses.json");
        List<CabinClassJSON> ccData = Arrays.asList(mapper.readValue(resource.getInputStream(), CabinClassJSON[].class));

        List<CabinClass> cabinClasses = ccData.stream()
                .map(cc -> {
                    return new CabinClass()
                            .setId(cc.getId())
                            .setTotalSeats(cc.getSeats())
                            .setWindowAvailable(cc.isWindowAvailable())
                            .setType(CabinClassEnum.valueOf(cc.getClassType()));
                }).toList();

        cabinClassRepository.saveAll(cabinClasses);
    }

//    private void initCabinClasses() {
//        List<CabinClass> cabinClasses = Stream.of(
//                CabinClassesFactory.getFirstClasses().entrySet(),
//                CabinClassesFactory.getMiddleClasses().entrySet(),
//                CabinClassesFactory.getLowerClasses().entrySet()
//        ).flatMap(set -> set.stream())
//                        .map(entry -> entry.getValue())
//                        .toList();
//
//        classRepository.saveAll(cabinClasses);
//    }
}
