package com.Booking.Booking.System.controller;

import com.Booking.Booking.System.model.Receipt;
import com.Booking.Booking.System.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST Controller for Receipt API.
 * OOP Concept: Abstraction — exposes clean endpoints, delegates to ReceiptService.
 */
@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    // ─── GET Receipt by Payment ID ───────────────────────────────────────────
    // GET /api/receipts/payment/{paymentId}
    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<?> getReceiptByPaymentId(@PathVariable Long paymentId) {
        Optional<Receipt> receipt = receiptService.getReceiptByPaymentId(paymentId);
        return receipt.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    // ─── GET Receipt by ID ───────────────────────────────────────────────────
    // GET /api/receipts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getReceiptById(@PathVariable Long id) {
        Optional<Receipt> receipt = receiptService.getReceiptById(id);
        return receipt.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    // ─── GET Receipt by Receipt Number ───────────────────────────────────────
    // GET /api/receipts/number/{receiptNumber}
    @GetMapping("/number/{receiptNumber}")
    public ResponseEntity<?> getReceiptByNumber(@PathVariable String receiptNumber) {
        Optional<Receipt> receipt = receiptService.getReceiptByNumber(receiptNumber);
        return receipt.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
}
