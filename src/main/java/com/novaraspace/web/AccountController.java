package com.novaraspace.web;

import com.novaraspace.model.dto.booking.AccountBookingDTO;
import com.novaraspace.model.dto.booking.UserBookingsRequestDTO;
import com.novaraspace.model.dto.user.*;
import com.novaraspace.model.other.PageResponse;
import com.novaraspace.security.NotDemoUser;
import com.novaraspace.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        AccountDTO data = userService.getCurrentAccountDTO();
        return ResponseEntity.ok(data);
    }

    @NotDemoUser
    @PatchMapping("/update-details")
    public ResponseEntity<UpdatableUserSettingsDTO> updateAccountData(@Valid @RequestBody UpdatableUserSettingsDTO updates) {
        UpdatableUserSettingsDTO completedUpdates = userService.updateAccountSettings(updates);
        return ResponseEntity.ok(completedUpdates);
    }

    @NotDemoUser
    @PostMapping("/change-password")
    public ResponseEntity<Void> changeUserPassword(@Valid @RequestBody PasswordChangeRequestDTO req) {
        userService.changeUserPassword(req);
        return ResponseEntity.ok().build();
    }

    @NotDemoUser
    @PatchMapping("/update-doc")
    public ResponseEntity<UserDocumentDTO[]> updateUserDoc(@Valid @RequestBody UserDocumentUpdateRequest req) {
        UserDocumentDTO[] docs = userService.updateUserDocument(req);
        return ResponseEntity.ok(docs);
    }

    @NotDemoUser
    @PostMapping("/card-add")
    public ResponseEntity<UserCardDTO[]> addUserCard(@Valid @RequestBody AddCardDTO dto) {
        UserCardDTO[] cards = userService.addUserCard(dto);
        return ResponseEntity.ok(cards);
    }

    @NotDemoUser
    @PostMapping("/card-remove")
    public ResponseEntity<UserCardDTO[]> removeUserCard(@RequestParam String userCardRef) {
        UserCardDTO[] cards = userService.removeUserCard(userCardRef);
        return ResponseEntity.ok(cards);
    }


    @PostMapping("/user-bookings")
    public ResponseEntity<PageResponse<AccountBookingDTO>> getUserBookings(@Valid @RequestBody UserBookingsRequestDTO dto) {
        PageResponse<AccountBookingDTO> page = userService.getCurrentUserBookingsPage(dto);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/user-payments")
    public ResponseEntity<UserPaymentsResponseDTO> getUserPayments() {
        UserPaymentsResponseDTO payments = userService.getCurrentUserPaymentsData();
        return ResponseEntity.ok(payments);
    }
}
