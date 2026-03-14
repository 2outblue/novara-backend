package com.novaraspace.web;

import com.novaraspace.model.dto.auth.EmailDTO;
import com.novaraspace.model.dto.booking.AccountBookingDTO;
import com.novaraspace.model.dto.booking.UserBookingsRequestDTO;
import com.novaraspace.model.dto.payment.PaymentDTO;
import com.novaraspace.model.dto.user.*;
import com.novaraspace.model.other.PageResponse;
import com.novaraspace.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/initial")
    public ResponseEntity<AccountDTO> getAccountDTO() {
//        AccountDTO data = userService.getAccountDTO(authentication.getName());
        AccountDTO data = userService.getCurrentAccountDTO();

        return ResponseEntity.ok(data);
    }

//    @GetMapping("/email")
//    public ResponseEntity<EmailDTO> getEmailByAuthId(Authentication authentication) {
//        EmailDTO dto = userService.getEmailByAuthId(authentication.getName());
//        return ResponseEntity.ok(dto);
//    }

    @PatchMapping("/update")
    public ResponseEntity<List<UpdateFieldDTO>> updateAccountData(@RequestBody List<UpdateFieldDTO> updates, Authentication authentication) {
        List<UpdateFieldDTO> completedUpdates = userService.updateFields(updates, authentication.getName());
        return ResponseEntity.ok(completedUpdates);
    }

    @PatchMapping("/update-doc")
    public ResponseEntity<UserDocumentDTO[]> updateUserDoc(@Valid @RequestBody UserDocumentUpdateRequest req, Authentication authentication) {
        UserDocumentDTO[] docs = userService.updateUserDocument(req, authentication.getName());
        return ResponseEntity.ok(docs);
    }

    @PostMapping("/card-add")
    public ResponseEntity<UserCardDTO[]> addUserCard(@Valid @RequestBody AddCardDTO dto, Authentication authentication) {
        UserCardDTO[] cards = userService.addUserCard(dto, authentication.getName());
        return ResponseEntity.ok(cards);
    }

    @PostMapping("/card-remove")
    public ResponseEntity<UserCardDTO[]> removeUserCard(@RequestParam String userCardRef, Authentication authentication) {
        UserCardDTO[] cards = userService.removeUserCard(userCardRef, authentication.getName());
        return ResponseEntity.ok(cards);
    }

    @PostMapping("/user-bookings")
    public ResponseEntity<PageResponse<AccountBookingDTO>> getUserBookings(@Valid @RequestBody UserBookingsRequestDTO dto) {
        PageResponse<AccountBookingDTO> page = userService.getCurrentUserBookingsPage(dto);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/user-payments")
    public ResponseEntity<PaymentDTO[]> getUserPayments() {
        PaymentDTO[] payments = userService.getCurrentUserLast10Payments();
        return ResponseEntity.ok(payments);
    }
}
