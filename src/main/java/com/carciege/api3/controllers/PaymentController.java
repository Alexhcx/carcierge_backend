package com.carciege.api3.controllers;

import com.carciege.api3.DTOs.PaymentRecordDto;
import com.carciege.api3.Repositories.PaymentRepository;
import com.carciege.api3.models.PaymentModel;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class PaymentController {

    @Autowired
    PaymentRepository paymentRepository;

    @GetMapping("/payments")
    public ResponseEntity<List<PaymentModel>> getAllPayments(){
        List<PaymentModel> paymentsList = paymentRepository.findAll();
        if(!paymentsList.isEmpty()) {
            for(PaymentModel payment : paymentsList) {
                UUID id = payment.getId();
                payment.add(linkTo(methodOn(PaymentController.class).getOnePayment(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(paymentsList);
    }

    @GetMapping("/payments/{id}")
    public ResponseEntity<Object> getOnePayment(@PathVariable(value="id") UUID id){
        Optional<PaymentModel> paymentO = paymentRepository.findById(id);
        if(paymentO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment not found.");
        }
        paymentO.get().add(linkTo(methodOn(PaymentController.class).getAllPayments()).withRel("Payments List"));
        return ResponseEntity.status(HttpStatus.OK).body(paymentO.get());
    }

    @PostMapping("/payments")
    public ResponseEntity<PaymentModel> savePayment(@RequestBody @Valid PaymentRecordDto paymentRecordDto) {
        var paymentModel = new PaymentModel();
        BeanUtils.copyProperties(paymentRecordDto, paymentModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentRepository.save(paymentModel));
    }

    @DeleteMapping("/payments/{id}")
    public ResponseEntity<Object> deletePayment(@PathVariable(value="id") UUID id) {
        Optional<PaymentModel> paymentO = paymentRepository.findById(id);
        if(paymentO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment not found.");
        }
        paymentRepository.delete(paymentO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Payment deleted successfully.");
    }

    @PutMapping("/payments/{id}")
    public ResponseEntity<Object> updatePayment(@PathVariable(value="id") UUID id,
                                            @RequestBody @Valid PaymentRecordDto paymentRecordDto) {
        Optional<PaymentModel> paymentO = paymentRepository.findById(id);
        if(paymentO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment not found.");
        }
        var paymentModel = paymentO.get();
        BeanUtils.copyProperties(paymentRecordDto, paymentModel);
        return ResponseEntity.status(HttpStatus.OK).body(paymentRepository.save(paymentModel));
    }

}
