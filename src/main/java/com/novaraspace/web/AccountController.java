package com.novaraspace.web;

import com.novaraspace.model.dto.auth.EmailDTO;
import com.novaraspace.model.dto.user.InitialAccountDataDTO;
import com.novaraspace.model.dto.user.UpdateFieldDTO;
import com.novaraspace.service.UserService;
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
    public ResponseEntity<InitialAccountDataDTO> loadInitialData(Authentication authentication) {
        InitialAccountDataDTO data = userService.getInitialAccountData(authentication.getName());
        return ResponseEntity.ok(data);
    }

    @GetMapping("/email")
    public ResponseEntity<EmailDTO> getEmailByAuthId(Authentication authentication) {
        EmailDTO dto = userService.getEmailByAuthId(authentication.getName());
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/update")
    public ResponseEntity<List<UpdateFieldDTO>> updateAccountData(@RequestBody List<UpdateFieldDTO> updates, Authentication authentication) {
        List<UpdateFieldDTO> completedUpdates = userService.updateFields(updates, authentication.getName());
        return ResponseEntity.ok(completedUpdates);
    }
}
