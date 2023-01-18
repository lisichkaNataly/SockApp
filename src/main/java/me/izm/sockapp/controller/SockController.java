package me.izm.sockapp.controller;

import me.izm.sockapp.dto.SockRequest;
import me.izm.sockapp.exception.InSufficientSockQuantity;
import me.izm.sockapp.exception.InvalidSockRequestException;
import me.izm.sockapp.model.Colour;
import me.izm.sockapp.model.Size;
import me.izm.sockapp.model.Sock;
import me.izm.sockapp.service.SockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sock")
public class SockController {

    private final SockService sockService;

    public SockController(SockService sockService) {
        this.sockService = sockService;
    }


    @ExceptionHandler(InvalidSockRequestException.class)
    public ResponseEntity<String> handleInvalidException(InvalidSockRequestException invalidSockRequestException) {
        return ResponseEntity.badRequest().body(invalidSockRequestException.getMessage());
    }

    @ExceptionHandler(InSufficientSockQuantity.class)
    public ResponseEntity<String> handleInSufficientException(InSufficientSockQuantity inSufficientSockQuantity) {
        return ResponseEntity.badRequest().body(inSufficientSockQuantity.getMessage());
    }

    @PostMapping
    public void addSocks(@RequestBody SockRequest sockRequest) {
        sockService.addSock(sockRequest);
    }

    @PutMapping
    public void issueSock(SockRequest sockRequest) {
        sockService.issueSock(sockRequest);
    }

    @GetMapping
    public int getSocksCount(@RequestParam(required = false, name = "colour")Colour colour,
                             @RequestParam(required = false, name = "size")Size size,
                             @RequestParam(required = false, name = "cottonMin") Integer cottonMin,
                             @RequestParam(required = false, name = "cottonMax") Integer cottonMax) {
        return sockService.getSocksQuantity(colour, size, cottonMin, cottonMax);
    }


    @DeleteMapping
    public void removeDefectiveSocks(@RequestBody SockRequest sockRequest) {
        sockService.removeDefectiveSocks(sockRequest);
    }
}
