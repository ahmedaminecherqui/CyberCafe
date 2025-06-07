package com.example.demo.controller.api;

import com.example.demo.DTO.*;
import com.example.demo.entity.Administrator;
import com.example.demo.services.AdministratorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/administrators")
@Validated
public class AdministratorRestController {

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private EntityMapper entityMapper;

    @GetMapping
    public ResponseEntity<Page<AdministratorDTO>> getAllAdministrators(Pageable pageable) {
        Page<Administrator> admins = administratorService.getAllAdministrators(pageable);
        Page<AdministratorDTO> adminDTOPage = admins.map(entityMapper::toAdministratorDTO);
        return ResponseEntity.ok(adminDTOPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdministratorDTO> getAdministratorById(@PathVariable Long id) {
        return administratorService.getAdministratorById(id)
                .map(admin -> ResponseEntity.ok(entityMapper.toAdministratorDTO(admin)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<AdministratorDTO> createAdministrator(@Valid @RequestBody AdministratorDTO adminDTO) {
        Administrator admin = entityMapper.toAdministratorEntity(adminDTO);
        Administrator savedAdmin = administratorService.saveAdministrator(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(entityMapper.toAdministratorDTO(savedAdmin));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdministratorDTO> updateAdministrator(@PathVariable Long id, @Valid @RequestBody AdministratorDTO adminDTO) {
        return administratorService.getAdministratorById(id).map(existingAdmin -> {
            adminDTO.setId(id);
            Administrator admin = entityMapper.toAdministratorEntity(adminDTO);
            Administrator updatedAdmin = administratorService.saveAdministrator(admin);
            return ResponseEntity.ok(entityMapper.toAdministratorDTO(updatedAdmin));
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdministrator(@PathVariable Long id) {
        Optional<Administrator> admin = administratorService.getAdministratorById(id);
        if (admin.isPresent()) {
            administratorService.deleteAdministrator(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
