package com.example.demo.vault;

import com.example.demo.vault.UserRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.support.VaultResponseSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class VaultController {

    private final VaultKeyValueOperations vaultOps;
    private final String basePath;

    public VaultController(VaultKeyValueOperations vaultOps, @Qualifier("appName") String appName) {
        this.vaultOps = vaultOps;
        this.basePath = appName + "/users/";
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserRecord> read(@PathVariable String username) {
        VaultResponseSupport<UserRecord> vaultResponse = vaultOps.get(basePath + username, UserRecord.class);
        if (vaultResponse == null) {
            return ResponseEntity.notFound().build();
        } else {
            Optional<UserRecord> foundUser = Optional.ofNullable(vaultResponse.getData());
            return ResponseEntity.of(foundUser);
        }
    }

    @PostMapping("/")
    public ResponseEntity<UserRecord> create(@RequestBody UserRecord userRecord) {
        vaultOps.put(basePath + userRecord.username, userRecord);
        VaultResponseSupport<UserRecord> vaultResponse = vaultOps
                .get(basePath + userRecord.username, UserRecord.class);
        if (vaultResponse == null) {
            return ResponseEntity.notFound().build();
        } else {
            Optional<UserRecord> foundUser = Optional.ofNullable(vaultResponse.getData());
            if (foundUser.isPresent()) {
                URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{username}")
                        .buildAndExpand(foundUser.get().getUsername())
                        .toUri();
                return ResponseEntity.created(uri).body(foundUser.get());
            } else {
                return ResponseEntity.notFound().build();
            }

        }
    }

    @PutMapping("/{username}")
    public ResponseEntity<UserRecord> update(@RequestBody UserRecord userRecord, @PathVariable String username) {
        vaultOps.put(basePath + username, userRecord);
        VaultResponseSupport<UserRecord> vaultResponse = vaultOps
                .get(basePath + userRecord.username, UserRecord.class);
        if (vaultResponse == null) {
            return ResponseEntity.notFound().build();
        } else {
            Optional<UserRecord> updatedUser = Optional.ofNullable(vaultResponse.getData());
            return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable String username) {
        vaultOps.delete(basePath + username);
        return ResponseEntity.noContent().build();
    }

}
