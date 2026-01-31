package com.novaraspace.model.mapper;

import com.novaraspace.model.dto.payment.NewPaymentDTO;
import com.novaraspace.model.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class PaymentMapper {

    public abstract Payment newPaymentDTOToEntity(NewPaymentDTO dto);
}
