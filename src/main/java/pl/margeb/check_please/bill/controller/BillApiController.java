package pl.margeb.check_please.bill.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.margeb.check_please.bill.domain.model.Bill;
import pl.margeb.check_please.bill.service.BillService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/groups/{group-id}/bills")
@AllArgsConstructor
public class BillApiController {

    private final BillService billService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Bill createBill(@PathVariable("group-id")UUID groupId, @RequestBody Bill bill){
        return billService.createBill(groupId, bill);
    }

    @GetMapping
    List<Bill> getBills(@PathVariable("group-id") UUID groupId){
        return billService.getBills(groupId);
    }

    @GetMapping("{bill-id}")
    Bill getBill(@PathVariable("group-id") UUID groupId, @PathVariable("bill-id")UUID billId){
        return billService.getBill(billId);
    }

    @PutMapping("{bill-id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Bill updateBill(@PathVariable("group-id")UUID groupId, @PathVariable("bill-id")UUID billId, @RequestBody Bill bill){
        return billService.updateBill(billId,bill);
    }

    @DeleteMapping("{bill-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteBill(@PathVariable("bill-id")UUID billId, @PathVariable("group-id")UUID groupId){
        billService.deleteBill(groupId, billId);
    }
}
