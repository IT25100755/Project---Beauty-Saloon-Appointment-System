package com.Booking.Booking.System.controller;

import com.Booking.Booking.System.model.Payment;
import com.Booking.Booking.System.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST Controller for mock Payment API.
 * OOP Concept: Abstraction — exposes clean REST endpoints, delegates logic to PaymentService.
 *
 * DEMO NOTE: Card details are NEVER stored. Only a flag ("CARD"/"CASH") is stored.
 */
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // ─── CREATE Payment (Step 1: initiate payment) ───────────────────────────
    // POST /api/payments/create/{appointmentId}
    // Body: { "paymentMethod": "CARD" }   or   { "paymentMethod": "CASH" }
    @PostMapping("/create/{appointmentId}")
    public ResponseEntity<?> createPayment(
            @PathVariable Long appointmentId,
            @RequestBody Map<String, String> body) {
        try {
            String paymentMethod = body.getOrDefault("paymentMethod", "CASH");
            Payment payment = paymentService.createPayment(appointmentId, paymentMethod);
            return new ResponseEntity<>(payment, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ─── CONFIRM Payment (Step 2: mock card validation → SUCCESS) ────────────
    // POST /api/payments/confirm/{paymentId}
    // Body: { "cardNumber": "4242424242424242" }  (CARD only — not saved in DB)
    //       { }  (CASH — no card info needed)
    @PostMapping("/confirm/{paymentId}")
    public ResponseEntity<?> confirmPayment(
            @PathVariable Long paymentId,
            @RequestBody(required = false) Map<String, String> body) {
        try {
            String cardNumber = (body != null) ? body.getOrDefault("cardNumber", "") : "";
            Payment payment = paymentService.confirmPayment(paymentId, cardNumber);
            return ResponseEntity.ok(payment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ─── READ Payment by ID ──────────────────────────────────────────────────
    // GET /api/payments/{paymentId}
    @GetMapping("/{paymentId}")
    public ResponseEntity<?> getPaymentById(@PathVariable Long paymentId) {
        Optional<Payment> payment = paymentService.getPaymentById(paymentId);
        return payment.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    // ─── READ Payments by Appointment ID ────────────────────────────────────
    // GET /api/payments/appointment/{appointmentId}
    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<List<Payment>> getPaymentsByAppointment(
            @PathVariable Long appointmentId) {
        return ResponseEntity.ok(paymentService.getPaymentsByAppointmentId(appointmentId));
    }

    // ─── READ Latest Payment for Appointment ────────────────────────────────
    // GET /api/payments/appointment/{appointmentId}/latest
    @GetMapping("/appointment/{appointmentId}/latest")
    public ResponseEntity<?> getLatestPayment(@PathVariable Long appointmentId) {
        Optional<Payment> payment = paymentService.getLatestPaymentForAppointment(appointmentId);
        return payment.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
}
